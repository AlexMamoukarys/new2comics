package com.example.cms;

import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.repository.SavedVolumeRepository;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.VolumeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SavedVolumeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SavedVolumeRepository savedVolumeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VolumeRepository volumeRepository;

	@Test
	void retrieveAllSavedVolumes() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/savedvolumes"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void userCanSaveVolume() throws Exception {
		// Get existing test data
		final Volume testVolume = volumeRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_save_vol", "password123");
		final User savedUser = userRepository.save(user);

		// Record count before
		int countBefore = savedVolumeRepository.findAll().size();

		// PUT to save volume
		MockHttpServletResponse response = mockMvc.perform(
				put("/users/" + savedUser.getId() + "/savedvolumes/" + testVolume.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify save was added
		int countAfter = savedVolumeRepository.findAll().size();
		assertEquals(countBefore + 1, countAfter);
	}

}
