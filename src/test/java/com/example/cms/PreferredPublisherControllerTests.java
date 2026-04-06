package com.example.cms;

import com.example.cms.model.entity.Publisher;
import com.example.cms.model.entity.PreferredPublisher;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.PublisherRepository;
import com.example.cms.model.repository.PreferredPublisherRepository;
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
class PreferredPublisherControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PreferredPublisherRepository preferredPublisherRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@Test
	void retrieveAllPreferredPublishers() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/preferredpublishers"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void updatePreferredPublisher() throws Exception {
		// Get existing test data
		final Publisher testPublisher = publisherRepository.findAll().get(0);
		final Publisher testPublisher2 = publisherRepository.findAll().size() > 1 ? publisherRepository.findAll().get(1) : testPublisher;

		// Create a test user
		User user = new User("testuser_pref_pub_update", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred publisher
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);
		PreferredPublisher savedPublisher = preferredPublisherRepository.save(preferredPublisher);

		// Update preferred publisher
		PreferredPublisher updatedPublisher = new PreferredPublisher(savedUser, testPublisher2);

		// PUT to update
		MockHttpServletResponse response = mockMvc.perform(
				put("/preferredpublishers/" + savedPublisher.getId())
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(updatedPublisher)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

	@Test
	void deletePreferredPublisherById() throws Exception {
		// Get existing test data
		final Publisher testPublisher = publisherRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_delete_pref_pub", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred publisher
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);
		PreferredPublisher savedPublisher = preferredPublisherRepository.save(preferredPublisher);

		// Verify it was added
		assertTrue(preferredPublisherRepository.findById(savedPublisher.getId()).isPresent());

		// Remove using id
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredpublishers/" + savedPublisher.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was deleted
		assertTrue(preferredPublisherRepository.findById(savedPublisher.getId()).isEmpty());
	}

	@Test
	void foreignDeletePreferredPublisher() throws Exception {
		// Get existing test data
		final Publisher testPublisher = publisherRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_foreign_delete_pub", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred publisher
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);
		PreferredPublisher savedPublisher = preferredPublisherRepository.save(preferredPublisher);

		// Verify it was added
		assertTrue(preferredPublisherRepository.findById(savedPublisher.getId()).isPresent());

		// Remove using foreign key delete
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredpublishers/" + savedUser.getId() + "/" + testPublisher.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
