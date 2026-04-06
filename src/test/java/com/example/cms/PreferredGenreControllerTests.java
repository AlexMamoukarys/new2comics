package com.example.cms;

import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.PreferredGenre;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.PreferredGenreRepository;
import com.example.cms.model.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
class PreferredGenreControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PreferredGenreRepository preferredGenreRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Test
	void retrieveAllPreferredGenres() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/preferredgenres"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void updatePreferredGenre() throws Exception {
		// Get existing test data
		final Genre testGenre = genreRepository.findAll().get(0);
		final Genre testGenre2 = genreRepository.findAll().size() > 1 ? genreRepository.findAll().get(1) : testGenre;

		// Create a test user
		User user = new User("testuser_pref_genre_update", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred genre
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);
		PreferredGenre savedGenre = preferredGenreRepository.save(preferredGenre);

		// Update preferred genre
		PreferredGenre updatedGenre = new PreferredGenre(savedUser, testGenre2);

		// PUT to update
		MockHttpServletResponse response = mockMvc.perform(
				put("/preferredgenres/" + savedGenre.getId())
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(updatedGenre)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void foreignDeletePreferredGenre() throws Exception {
		// Get existing test data
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_foreign_delete_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred genre
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);
		PreferredGenre savedGenre = preferredGenreRepository.save(preferredGenre);

		// Verify it was added
		assertTrue(preferredGenreRepository.findById(savedGenre.getId()).isPresent());

		// Remove using foreign key delete
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredgenres/" + savedUser.getId() + "/" + testGenre.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void deletePreferredGenreById() throws Exception {
		// Get existing test data
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_delete_pref_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred genre
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);
		PreferredGenre savedGenre = preferredGenreRepository.save(preferredGenre);

		// Verify it was added
		assertTrue(preferredGenreRepository.findById(savedGenre.getId()).isPresent());

		// Remove using id
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredgenres/" + savedGenre.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was deleted
		assertTrue(preferredGenreRepository.findById(savedGenre.getId()).isEmpty());
	}

	@Test
	void createPreferredGenreWithDuplicate() throws Exception {
		// Get existing test data
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_dup_pref_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred genre object first time
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);

		// POST first time - should succeed
		MockHttpServletResponse response1 = mockMvc.perform(
				post("/preferredgenres")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(preferredGenre)))
				.andReturn().getResponse();

		assertEquals(200, response1.getStatus());

		// Try to POST duplicate - should fail with conflict
		MockHttpServletResponse response2 = mockMvc.perform(
				post("/preferredgenres")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(preferredGenre)))
				.andReturn().getResponse();

		assertEquals(409, response2.getStatus());
	}

}
