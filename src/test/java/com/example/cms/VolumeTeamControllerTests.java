package com.example.cms;

import com.example.cms.controller.dto.VolumeTeamDto;
import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.TeamRepository;
import com.example.cms.model.repository.VolumeTeamRepository;
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
class VolumeTeamControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VolumeTeamRepository volumeTeamRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Test
	void retrieveAllVolumeTeams() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/volume_team"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void createVolumeTeamRelationship() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);
		final Team testTeam = teamRepository.findAll().get(0);

		// Create DTO for the relationship
		VolumeTeamDto volumeTeamDto = new VolumeTeamDto();
		volumeTeamDto.setVolumeId(testVolume.getId());
		volumeTeamDto.setTeamId(testTeam.getId());

		// POST to create relationship
		MockHttpServletResponse response = mockMvc.perform(
				post("/volume_team")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(volumeTeamDto)))
				.andReturn().getResponse();

		// Accept either 200 (created) or 409 (already exists - which is still valid behavior)
		assertTrue(response.getStatus() == 200 || response.getStatus() == 409);
	}

	@Test
	void deleteVolumeTeamRelationship() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);
		final Team testTeam = teamRepository.findAll().get(0);

		// Create a volume-team relationship
		VolumeTeamDto volumeTeamDto = new VolumeTeamDto();
		volumeTeamDto.setVolumeId(testVolume.getId());
		volumeTeamDto.setTeamId(testTeam.getId());

		// First create the relationship
		mockMvc.perform(
				post("/volume_team")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(volumeTeamDto)))
				.andReturn().getResponse();

		// Now delete it
		MockHttpServletResponse response = mockMvc.perform(
				delete("/volume_team/" + testVolume.getId() + "/" + testTeam.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
