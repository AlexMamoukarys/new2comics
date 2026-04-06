package com.example.cms;

import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.PreferredTeam;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.TeamRepository;
import com.example.cms.model.repository.PreferredTeamRepository;
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
class PreferredTeamControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PreferredTeamRepository preferredTeamRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Test
	void retrieveAllPreferredTeams() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/preferredteams"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void updatePreferredTeam() throws Exception {
		// Get existing test data
		final Team testTeam = teamRepository.findAll().get(0);
		final Team testTeam2 = teamRepository.findAll().size() > 1 ? teamRepository.findAll().get(1) : testTeam;

		// Create a test user
		User user = new User("testuser_pref_team_update", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred team
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);
		PreferredTeam savedTeam = preferredTeamRepository.save(preferredTeam);

		// Update preferred team
		PreferredTeam updatedTeam = new PreferredTeam(savedUser, testTeam2);

		// PUT to update
		MockHttpServletResponse response = mockMvc.perform(
				put("/preferredteams/" + savedTeam.getId())
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(updatedTeam)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void deletePreferredTeamById() throws Exception {
		// Get existing test data
		final Team testTeam = teamRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_delete_pref_team", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred team
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);
		PreferredTeam savedTeam = preferredTeamRepository.save(preferredTeam);

		// Verify it was added
		assertTrue(preferredTeamRepository.findById(savedTeam.getId()).isPresent());

		// Remove using id
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredteams/" + savedTeam.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was deleted
		assertTrue(preferredTeamRepository.findById(savedTeam.getId()).isEmpty());
	}

	@Test
	void foreignDeletePreferredTeam() throws Exception {
		// Get existing test data
		final Team testTeam = teamRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_foreign_delete_team", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred team
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);
		PreferredTeam savedTeam = preferredTeamRepository.save(preferredTeam);

		// Verify it was added
		assertTrue(preferredTeamRepository.findById(savedTeam.getId()).isPresent());

		// Remove using foreign key delete
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredteams/" + savedUser.getId() + "/" + testTeam.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
