package com.example.cms;

import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.LikedVolumeRepository;
import com.example.cms.model.repository.SavedVolumeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Autowired
	private LikedVolumeRepository likedVolumeRepository;

	@Autowired
	private SavedVolumeRepository savedVolumeRepository;

	@Test
	void retrieveAllUsers() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/users"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void createUser() throws Exception {
		// Create a new user directly via repository (not via REST endpoint)
		String uniqueUsername = "test_user_" + System.currentTimeMillis() + "_" + (long)(Math.random() * 100000);
		User newUser = new User(uniqueUsername, "password123");

		int userCountBefore = userRepository.findAll().size();

		// Save directly to repository
		userRepository.save(newUser);

		// Verify user was created
		int userCountAfter = userRepository.findAll().size();
		assertEquals(userCountBefore + 1, userCountAfter);
	}

	@Test
	void registerUser() throws Exception {
		// Prepare registration data
		Map<String, String> userData = Map.of(
				"username", "newuser_" + System.currentTimeMillis(),
				"password", "password123"
		);

		// POST to register endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/users/register")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(userData)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		assertTrue(response.getContentAsString().contains("success") || response.getContentAsString().contains("registered"));
	}

	@Test
	void registerUserWithDuplicateUsername() throws Exception {
		// Create a user first
		String username = "duplicate_user_" + System.currentTimeMillis();
		User user = new User(username, "password123");
		userRepository.save(user);

		// Try to register with the same username
		Map<String, String> userData = Map.of(
				"username", username,
				"password", "password456"
		);

		// POST to register endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/users/register")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(userData)))
				.andReturn().getResponse();

		assertEquals(400, response.getStatus());
		assertTrue(response.getContentAsString().contains("already taken"));
	}

	@Test
	void loginUser() throws Exception {
		// Create a test user
		String username = "login_user_" + System.currentTimeMillis();
		String password = "password123";
		User user = new User(username, password);
		User savedUser = userRepository.save(user);

		// Prepare login credentials
		Map<String, String> credentials = Map.of(
				"username", username,
				"password", password
		);

		// POST to login endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/auth/login")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(credentials)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		assertTrue(response.getContentAsString().contains("success"));
	}

	@Test
	void loginUserWithWrongPassword() throws Exception {
		// Create a test user
		String username = "wrong_pass_user_" + System.currentTimeMillis();
		User user = new User(username, "correctpassword");
		userRepository.save(user);

		// Prepare login credentials with wrong password
		Map<String, String> credentials = Map.of(
				"username", username,
				"password", "wrongpassword"
		);

		// POST to login endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/auth/login")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(credentials)))
				.andReturn().getResponse();

		assertEquals(401, response.getStatus());
	}

	@Test
	void getUserById() throws Exception {
		// Create a test user
		User user = new User("get_user_test", "password123");
		User savedUser = userRepository.save(user);

		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/users/" + savedUser.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void getLikedVolumesForUser() throws Exception {
		// Create a test user
		User user = new User("liked_vol_user", "password123");
		User savedUser = userRepository.save(user);

		// Get a volume and like it
		final Volume testVolume = volumeRepository.findAll().get(0);
		LikedVolume likedVolume = new LikedVolume(savedUser, testVolume);
		likedVolumeRepository.save(likedVolume);

		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/users/" + savedUser.getId() + "/likedvolumes"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void getSavedVolumesForUser() throws Exception {
		// Create a test user
		User user = new User("saved_vol_user", "password123");
		User savedUser = userRepository.save(user);

		// Get a volume and save it
		final Volume testVolume = volumeRepository.findAll().get(0);
		SavedVolume savedVolume = new SavedVolume(savedUser, testVolume);
		savedVolumeRepository.save(savedVolume);

		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/users/" + savedUser.getId() + "/savedvolumes"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void deleteUser() throws Exception {
		// Create a test user
		User user = new User("user_to_delete", "password123");
		User savedUser = userRepository.save(user);
		Long userId = savedUser.getId();

		// Verify it exists
		assertTrue(userRepository.findById(userId).isPresent());

		// DELETE the user
		MockHttpServletResponse response = mockMvc.perform(
				delete("/users/" + userId))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was deleted
		assertTrue(userRepository.findById(userId).isEmpty());
	}

	@Test
	void checkUsername() throws Exception {
		// Create a test user
		String username = "check_username_" + System.currentTimeMillis();
		User user = new User(username, "password123");
		userRepository.save(user);

		// Call endpoint with existing username
		MockHttpServletResponse response = mockMvc.perform(
				get("/users/username")
						.param("username", username))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		assertTrue(response.getContentAsString().contains("true"));
	}

	@Test
	void checkUsernameNotExists() throws Exception {
		// Call endpoint with non-existing username
		MockHttpServletResponse response = mockMvc.perform(
				get("/users/username")
						.param("username", "nonexistent_" + System.currentTimeMillis()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		assertTrue(response.getContentAsString().contains("false"));
	}

}
