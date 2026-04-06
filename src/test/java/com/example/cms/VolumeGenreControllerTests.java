package com.example.cms;

import com.example.cms.controller.dto.VolumeGenreDto;
import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.VolumeGenreRepository;
import com.example.cms.model.repository.VolumeRepository;
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
class VolumeGenreControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VolumeGenreRepository volumeGenreRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Test
	void retrieveAllVolumeGenres() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/volume_genre"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void createVolumeGenreRelationship() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create DTO for the relationship
		VolumeGenreDto volumeGenreDto = new VolumeGenreDto();
		volumeGenreDto.setVolumeId(testVolume.getId());
		volumeGenreDto.setGenreId(testGenre.getId());

		// POST to create relationship
		MockHttpServletResponse response = mockMvc.perform(
				post("/volume_genre")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(volumeGenreDto)))
				.andReturn().getResponse();

		// Accept either 200 (created) or 409 (already exists - which is still valid behavior)
		assertTrue(response.getStatus() == 200 || response.getStatus() == 409);
	}

	@Test
	void deleteVolumeGenreRelationship() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create a volume-genre relationship
		VolumeGenreDto volumeGenreDto = new VolumeGenreDto();
		volumeGenreDto.setVolumeId(testVolume.getId());
		volumeGenreDto.setGenreId(testGenre.getId());

		// First create the relationship
		mockMvc.perform(
				post("/volume_genre")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(volumeGenreDto)))
				.andReturn().getResponse();

		// Now delete it
		MockHttpServletResponse response = mockMvc.perform(
				delete("/volume_genre/" + testVolume.getId() + "/" + testGenre.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
