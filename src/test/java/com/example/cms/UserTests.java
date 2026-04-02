package com.example.cms;

import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.PreferredGenre;
import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.PreferredGenreRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Autowired
	private PreferredGenreRepository preferredGenreRepository;

	@Autowired
	private LikedVolumeRepository likedVolumeRepository;

	@Autowired
	private SavedVolumeRepository savedVolumeRepository;

	@Test
	void userAddsPreferredGenre() throws Exception {
		// Get existing test data or create with large ID
		final Genre testGenre;
		if (genreRepository.findAll().isEmpty()) {
			Genre genre = new Genre();
			genre.setId(999999L);
			genre.setName("Test Genre");
			testGenre = genreRepository.save(genre);
		} else {
			testGenre = genreRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Create and save a preferred genre
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);
		PreferredGenre savedGenre = preferredGenreRepository.save(preferredGenre);

		// Verify the preferred genre was saved
		assertEquals(savedUser.getId(), savedGenre.getUser().getId());
		assertEquals(testGenre.getId(), savedGenre.getGenre().getId());

		// Verify via GET endpoint
		MockHttpServletResponse response = mockMvc.perform(get("/users/" + savedUser.getId() + "/preferredgenres"))
				.andReturn().getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	void userRemovesPreferredGenre() throws Exception {
		// Get existing test data or create with large ID
		final Genre testGenre;
		if (genreRepository.findAll().isEmpty()) {
			Genre genre = new Genre();
			genre.setId(999998L);
			genre.setName("Test Genre");
			testGenre = genreRepository.save(genre);
		} else {
			testGenre = genreRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_remove_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred genre
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);
		PreferredGenre savedGenre = preferredGenreRepository.save(preferredGenre);
		long preferredGenreId = savedGenre.getId();

		// Verify it was added
		assertTrue(preferredGenreRepository.findById(preferredGenreId).isPresent());

		// Remove the preferred genre
		preferredGenreRepository.deleteById(preferredGenreId);

		// Verify it was removed
		assertTrue(preferredGenreRepository.findById(preferredGenreId).isEmpty());
	}

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

		// Like the volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/likedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify the liked volume exists in repository
		assertTrue(likedVolumeRepository.findAll().stream()
				.anyMatch(lv -> lv.getUser().getId() == savedUser.getId() && lv.getVolume().getId() == testVolume.getId()));
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

		// Save the volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/savedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify the saved volume exists in repository
		assertTrue(savedVolumeRepository.findAll().stream()
				.anyMatch(sv -> sv.getUser().getId() == savedUser.getId() && sv.getVolume().getId() == testVolume.getId()));
	}

	@Test
	void userRemovesLikedVolume() throws Exception {
		// Get existing test data or create with large ID
		final Volume testVolume;
		if (volumeRepository.findAll().isEmpty()) {
			Volume volume = new Volume();
			volume.setId(999995L);
			volume.setName("Test Volume");
			testVolume = volumeRepository.save(volume);
		} else {
			testVolume = volumeRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_unlike", "password123");
		final User savedUser = userRepository.save(user);

		// Like the volume first
		LikedVolume likedVolume = new LikedVolume(savedUser, testVolume);
		likedVolumeRepository.save(likedVolume);

		// Verify it was liked
		assertTrue(likedVolumeRepository.findAll().stream()
				.anyMatch(lv -> lv.getUser().getId() == savedUser.getId() && lv.getVolume().getId() == testVolume.getId()));

		// Remove the liked volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/likedvolumes/remove/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify the liked volume was removed
		assertTrue(likedVolumeRepository.findAll().stream()
				.noneMatch(lv -> lv.getUser().getId() == savedUser.getId() && lv.getVolume().getId() == testVolume.getId()));
	}

	@Test
	void userRemovesSavedVolume() throws Exception {
		// Get existing test data or create with large ID
		final Volume testVolume;
		if (volumeRepository.findAll().isEmpty()) {
			Volume volume = new Volume();
			volume.setId(999994L);
			volume.setName("Test Volume");
			testVolume = volumeRepository.save(volume);
		} else {
			testVolume = volumeRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_unsave", "password123");
		final User savedUser = userRepository.save(user);

		// Save the volume first
		SavedVolume savedVolume = new SavedVolume(savedUser, testVolume);
		savedVolumeRepository.save(savedVolume);

		// Verify it was saved
		assertTrue(savedVolumeRepository.findAll().stream()
				.anyMatch(sv -> sv.getUser().getId() == savedUser.getId() && sv.getVolume().getId() == testVolume.getId()));

		// Remove the saved volume via HTTP endpoint
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/savedvolumes/remove/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify the saved volume was removed
		assertTrue(savedVolumeRepository.findAll().stream()
				.noneMatch(sv -> sv.getUser().getId() == savedUser.getId() && sv.getVolume().getId() == testVolume.getId()));
	}

}
