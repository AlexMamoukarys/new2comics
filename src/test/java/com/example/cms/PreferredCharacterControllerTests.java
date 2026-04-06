package com.example.cms;

import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.PreferredCharacterRepository;
import com.example.cms.model.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PreferredCharacterControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PreferredCharacterRepository preferredCharacterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CharacterRepository characterRepository;

	@Test
	void retrieveAllPreferredCharacters() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/preferredcharacters"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void updatePreferredCharacter() throws Exception {
		// Get existing test data
		final Character testCharacter = characterRepository.findAll().get(0);
		final Character testCharacter2 = characterRepository.findAll().size() > 1 ? characterRepository.findAll().get(1) : testCharacter;

		// Create a test user
		User user = new User("testuser_pref_char_update", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred character
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);
		PreferredCharacter savedCharacter = preferredCharacterRepository.save(preferredCharacter);

		// Update preferred character
		PreferredCharacter updatedCharacter = new PreferredCharacter(savedUser, testCharacter2);

		// PUT to update
		MockHttpServletResponse response = mockMvc.perform(
				put("/preferredcharacters/" + savedCharacter.getId())
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(updatedCharacter)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void deletePreferredCharacterById() throws Exception {
		// Get existing test data
		final Character testCharacter = characterRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_delete_pref_char", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred character
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);
		PreferredCharacter savedCharacter = preferredCharacterRepository.save(preferredCharacter);

		// Verify it was added
		assertTrue(preferredCharacterRepository.findById(savedCharacter.getId()).isPresent());

		// Remove using id
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredcharacters/" + savedCharacter.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was deleted
		assertTrue(preferredCharacterRepository.findById(savedCharacter.getId()).isEmpty());
	}

	@Test
	void foreignDeletePreferredCharacter() throws Exception {
		// Get existing test data
		final Character testCharacter = characterRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_foreign_delete_char", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred character
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);
		PreferredCharacter savedCharacter = preferredCharacterRepository.save(preferredCharacter);

		// Verify it was added
		assertTrue(preferredCharacterRepository.findById(savedCharacter.getId()).isPresent());

		// Remove using foreign key delete
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredcharacters/" + savedUser.getId() + "/" + testCharacter.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
