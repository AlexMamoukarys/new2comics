package com.example.cms;

import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.LikedVolumeRepository;
import com.example.cms.model.repository.SavedVolumeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserVolumeTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Autowired
	private LikedVolumeRepository likedVolumeRepository;

	@Autowired
	private SavedVolumeRepository savedVolumeRepository;

	@Test
	void userLikesVolume() throws Exception {
		// Get existing test data or create with large ID
		final Volume testVolume;
		if (volumeRepository.findAll().isEmpty()) {
			Volume volume = new Volume();
			volume.setId(999997L);
			volume.setName("Test Volume");
			testVolume = volumeRepository.save(volume);
		} else {
			testVolume = volumeRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_like", "password123");
		final User savedUser = userRepository.save(user);

		// Record count before liking
		int beforeLike = likedVolumeRepository.findAll().size();

		// Like the volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/likedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify the liked volume exists in repository
		int afterLike = likedVolumeRepository.findAll().size();
		assertEquals(beforeLike + 1, afterLike);
	}

	@Test
	void userSavesVolume() throws Exception {
		// Get existing test data or create with large ID
		final Volume testVolume;
		if (volumeRepository.findAll().isEmpty()) {
			Volume volume = new Volume();
			volume.setId(999996L);
			volume.setName("Test Volume");
			testVolume = volumeRepository.save(volume);
		} else {
			testVolume = volumeRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_save", "password123");
		final User savedUser = userRepository.save(user);

		// Record count before saving
		int beforeSave = savedVolumeRepository.findAll().size();

		// Save the volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/savedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify the saved volume exists in repository
		int afterSave = savedVolumeRepository.findAll().size();
		assertEquals(beforeSave + 1, afterSave);
	}

	@Test
	void userRemovesLikedVolume() throws Exception {
		// Use existing volume from database
		final Volume testVolume = volumeRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_unlike", "password123");
		final User savedUser = userRepository.save(user);

		// Record count before adding liked volume
		int beforeAdd = likedVolumeRepository.findAll().size();

		// Like the volume via HTTP endpoint
		mockMvc.perform(
				put("/users/" + savedUser.getId() + "/likedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		// Count liked volumes after adding
		int afterAdd = likedVolumeRepository.findAll().size();
		assertEquals(beforeAdd + 1, afterAdd);

		// Remove the liked volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				delete("/volumes/" + testVolume.getId() + "/like/" + savedUser.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Count liked volumes after removal - should be back to before add count
		int afterRemove = likedVolumeRepository.findAll().size();
		assertEquals(beforeAdd, afterRemove);
	}

	@Test
	void userRemovesSavedVolume() throws Exception {
		// Use existing volume from database
		final Volume testVolume = volumeRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_unsave", "password123");
		final User savedUser = userRepository.save(user);

		// Record count before adding saved volume
		int beforeAdd = savedVolumeRepository.findAll().size();

		// Save the volume via HTTP endpoint
		mockMvc.perform(
				put("/users/" + savedUser.getId() + "/savedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		// Count saved volumes after adding
		int afterAdd = savedVolumeRepository.findAll().size();
		assertEquals(beforeAdd + 1, afterAdd);

		// Remove the saved volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				delete("/volumes/" + testVolume.getId() + "/save/" + savedUser.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Count saved volumes after removal - should be back to before add count
		int afterRemove = savedVolumeRepository.findAll().size();
		assertEquals(beforeAdd, afterRemove);
	}

}
