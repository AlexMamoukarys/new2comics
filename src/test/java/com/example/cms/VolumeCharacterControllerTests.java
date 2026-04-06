package com.example.cms;

import com.example.cms.controller.dto.VolumeCharacterDto;
import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.entity.VolumeCharacter;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.VolumeCharacterRepository;
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
class VolumeCharacterControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VolumeCharacterRepository volumeCharacterRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Autowired
	private CharacterRepository characterRepository;

	@Test
	void retrieveAllVolumeCharacters() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/volume_character"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void createVolumeCharacterRelationship() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);
		final Character testCharacter = characterRepository.findAll().get(0);

		// Create DTO for the relationship
		VolumeCharacterDto volumeCharacterDto = new VolumeCharacterDto();
		volumeCharacterDto.setVolumeId(testVolume.getId());
		volumeCharacterDto.setCharacterId(testCharacter.getId());

		// POST to create relationship
		MockHttpServletResponse response = mockMvc.perform(
				post("/volume_character")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(volumeCharacterDto)))
				.andReturn().getResponse();

		// Accept either 200 (created) or 409 (already exists - which is still valid behavior)
		assertTrue(response.getStatus() == 200 || response.getStatus() == 409);
	}

	@Test
	void deleteVolumeCharacterRelationship() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);
		final Character testCharacter = characterRepository.findAll().get(0);

		// Create a volume-character relationship
		VolumeCharacterDto volumeCharacterDto = new VolumeCharacterDto();
		volumeCharacterDto.setVolumeId(testVolume.getId());
		volumeCharacterDto.setCharacterId(testCharacter.getId());

		// First create the relationship
		mockMvc.perform(
				post("/volume_character")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(volumeCharacterDto)))
				.andReturn().getResponse();

		// Now delete it
		MockHttpServletResponse response = mockMvc.perform(
				delete("/volume_character/" + testVolume.getId() + "/" + testCharacter.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
