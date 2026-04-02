package com.example.cms;

import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.PreferredGenre;
import com.example.cms.model.entity.PreferredPublisher;
import com.example.cms.model.entity.PreferredTeam;
import com.example.cms.model.entity.Publisher;
import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.PreferredCharacterRepository;
import com.example.cms.model.repository.PreferredGenreRepository;
import com.example.cms.model.repository.PreferredPublisherRepository;
import com.example.cms.model.repository.PreferredTeamRepository;
import com.example.cms.model.repository.PublisherRepository;
import com.example.cms.model.repository.TeamRepository;
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
class UserPreferredTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private PreferredGenreRepository preferredGenreRepository;

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private PreferredCharacterRepository preferredCharacterRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private PreferredPublisherRepository preferredPublisherRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private PreferredTeamRepository preferredTeamRepository;

	@Test
	void userAddsPreferredGenre() throws Exception {
		// Get existing test data
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred genre object
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);

		// POST to controller endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/preferredgenres")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(preferredGenre)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Parse response to get the ID
		ObjectNode responseJson = objectMapper.readValue(response.getContentAsString(), ObjectNode.class);
		long preferredGenreId = responseJson.get("id").longValue();

		// Verify the preferred genre exists in repository
		assertTrue(preferredGenreRepository.findById(preferredGenreId).isPresent());
		PreferredGenre savedGenre = preferredGenreRepository.findById(preferredGenreId).get();
		assertEquals(savedUser.getId(), savedGenre.getUser().getId());
		assertEquals(testGenre.getId(), savedGenre.getGenre().getId());
	}

	@Test
	void userRemovesPreferredGenre() throws Exception {
		// Get existing test data
		final Genre testGenre = genreRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_remove_genre", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred genre
		PreferredGenre preferredGenre = new PreferredGenre(savedUser, testGenre);
		PreferredGenre savedGenre = preferredGenreRepository.save(preferredGenre);

		// Verify it was added
		assertTrue(preferredGenreRepository.findById(savedGenre.getId()).isPresent());

		// Remove the preferred genre via DELETE endpoint
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredgenres/" + savedUser.getId() + "/" + testGenre.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was removed
		assertTrue(preferredGenreRepository.findById(savedGenre.getId()).isEmpty());
	}

	@Test
	void userAddsPreferredCharacter() throws Exception {
		// Get existing test data
		final Character testCharacter = characterRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_character", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred character object
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);

		// POST to controller endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/preferredcharacters")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(preferredCharacter)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Parse response to get the ID
		ObjectNode responseJson = objectMapper.readValue(response.getContentAsString(), ObjectNode.class);
		long preferredCharacterId = responseJson.get("id").longValue();

		// Verify the preferred character exists in repository
		assertTrue(preferredCharacterRepository.findById(preferredCharacterId).isPresent());
		PreferredCharacter savedCharacter = preferredCharacterRepository.findById(preferredCharacterId).get();
		assertEquals(savedUser.getId(), savedCharacter.getUser().getId());
		assertEquals(testCharacter.getId(), savedCharacter.getCharacter().getId());
	}

	@Test
	void userRemovesPreferredCharacter() throws Exception {
		// Get existing test data
		final Character testCharacter = characterRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_remove_character", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred character
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);
		PreferredCharacter savedCharacter = preferredCharacterRepository.save(preferredCharacter);

		// Verify it was added
		assertTrue(preferredCharacterRepository.findById(savedCharacter.getId()).isPresent());

		// Remove the preferred character via DELETE endpoint
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredcharacters/" + savedUser.getId() + "/" + testCharacter.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was removed
		assertTrue(preferredCharacterRepository.findById(savedCharacter.getId()).isEmpty());
	}

	@Test
	void userAddsPreferredPublisher() throws Exception {
		// Get existing test data
		final Publisher testPublisher = publisherRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_publisher", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred publisher object
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);

		// POST to controller endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/preferredpublishers")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(preferredPublisher)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Parse response to get the ID
		ObjectNode responseJson = objectMapper.readValue(response.getContentAsString(), ObjectNode.class);
		long preferredPublisherId = responseJson.get("id").longValue();

		// Verify the preferred publisher exists in repository
		assertTrue(preferredPublisherRepository.findById(preferredPublisherId).isPresent());
		PreferredPublisher savedPublisher = preferredPublisherRepository.findById(preferredPublisherId).get();
		assertEquals(savedUser.getId(), savedPublisher.getUser().getId());
		assertEquals(testPublisher.getId(), savedPublisher.getPublisher().getId());
	}

	@Test
	void userRemovesPreferredPublisher() throws Exception {
		// Get existing test data
		final Publisher testPublisher = publisherRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_remove_publisher", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred publisher
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);
		PreferredPublisher savedPublisher = preferredPublisherRepository.save(preferredPublisher);

		// Verify it was added
		assertTrue(preferredPublisherRepository.findById(savedPublisher.getId()).isPresent());

		// Remove the preferred publisher via DELETE endpoint
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredpublishers/" + savedUser.getId() + "/" + testPublisher.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was removed
		assertTrue(preferredPublisherRepository.findById(savedPublisher.getId()).isEmpty());
	}

	@Test
	void userAddsPreferredTeam() throws Exception {
		// Get existing test data
		final Team testTeam = teamRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_team", "password123");
		final User savedUser = userRepository.save(user);

		// Create a preferred team object
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);

		// POST to controller endpoint
		MockHttpServletResponse response = mockMvc.perform(
				post("/preferredteams")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(preferredTeam)))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Parse response to get the ID
		ObjectNode responseJson = objectMapper.readValue(response.getContentAsString(), ObjectNode.class);
		long preferredTeamId = responseJson.get("id").longValue();

		// Verify the preferred team exists in repository
		assertTrue(preferredTeamRepository.findById(preferredTeamId).isPresent());
		PreferredTeam savedTeam = preferredTeamRepository.findById(preferredTeamId).get();
		assertEquals(savedUser.getId(), savedTeam.getUser().getId());
		assertEquals(testTeam.getId(), savedTeam.getTeam().getId());
	}

	@Test
	void userRemovesPreferredTeam() throws Exception {
		// Get existing test data
		final Team testTeam = teamRepository.findAll().get(0);

		// Create a test user
		User user = new User("testuser_remove_team", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred team
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);
		PreferredTeam savedTeam = preferredTeamRepository.save(preferredTeam);

		// Verify it was added
		assertTrue(preferredTeamRepository.findById(savedTeam.getId()).isPresent());

		// Remove the preferred team via DELETE endpoint
		MockHttpServletResponse response = mockMvc.perform(
				delete("/preferredteams/" + savedUser.getId() + "/" + testTeam.getId()))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		// Verify it was removed
		assertTrue(preferredTeamRepository.findById(savedTeam.getId()).isEmpty());
	}
}
