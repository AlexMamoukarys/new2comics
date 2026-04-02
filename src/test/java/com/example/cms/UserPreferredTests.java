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
	void userAddsPreferredCharacter() throws Exception {
		// Get existing test data or create with large ID
		final Character testCharacter;
		if (characterRepository.findAll().isEmpty()) {
			Character character = new Character();
			character.setId(999997L);
			character.setName("Test Character");
			testCharacter = characterRepository.save(character);
		} else {
			testCharacter = characterRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_character", "password123");
		final User savedUser = userRepository.save(user);

		// Create and save a preferred character
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);
		PreferredCharacter savedCharacter = preferredCharacterRepository.save(preferredCharacter);

		// Verify the preferred character was saved
		assertEquals(savedUser.getId(), savedCharacter.getUser().getId());
		assertEquals(testCharacter.getId(), savedCharacter.getCharacter().getId());

		// Verify via GET endpoint
		MockHttpServletResponse response = mockMvc.perform(get("/users/" + savedUser.getId() + "/preferredcharacters"))
				.andReturn().getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	void userRemovesPreferredCharacter() throws Exception {
		// Get existing test data or create with large ID
		final Character testCharacter;
		if (characterRepository.findAll().isEmpty()) {
			Character character = new Character();
			character.setId(999996L);
			character.setName("Test Character");
			testCharacter = characterRepository.save(character);
		} else {
			testCharacter = characterRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_remove_character", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred character
		PreferredCharacter preferredCharacter = new PreferredCharacter(savedUser, testCharacter);
		PreferredCharacter savedCharacter = preferredCharacterRepository.save(preferredCharacter);
		long preferredCharacterId = savedCharacter.getId();

		// Verify it was added
		assertTrue(preferredCharacterRepository.findById(preferredCharacterId).isPresent());

		// Remove the preferred character
		preferredCharacterRepository.deleteById(preferredCharacterId);

		// Verify it was removed
		assertTrue(preferredCharacterRepository.findById(preferredCharacterId).isEmpty());
	}

	@Test
	void userAddsPreferredPublisher() throws Exception {
		// Get existing test data or create with large ID
		final Publisher testPublisher;
		if (publisherRepository.findAll().isEmpty()) {
			Publisher publisher = new Publisher();
			publisher.setId(999995L);
			publisher.setName("Test Publisher");
			testPublisher = publisherRepository.save(publisher);
		} else {
			testPublisher = publisherRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_publisher", "password123");
		final User savedUser = userRepository.save(user);

		// Create and save a preferred publisher
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);
		PreferredPublisher savedPublisher = preferredPublisherRepository.save(preferredPublisher);

		// Verify the preferred publisher was saved
		assertEquals(savedUser.getId(), savedPublisher.getUser().getId());
		assertEquals(testPublisher.getId(), savedPublisher.getPublisher().getId());

		// Verify via GET endpoint
		MockHttpServletResponse response = mockMvc.perform(get("/users/" + savedUser.getId() + "/preferredpublishers"))
				.andReturn().getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	void userRemovesPreferredPublisher() throws Exception {
		// Get existing test data or create with large ID
		final Publisher testPublisher;
		if (publisherRepository.findAll().isEmpty()) {
			Publisher publisher = new Publisher();
			publisher.setId(999994L);
			publisher.setName("Test Publisher");
			testPublisher = publisherRepository.save(publisher);
		} else {
			testPublisher = publisherRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_remove_publisher", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred publisher
		PreferredPublisher preferredPublisher = new PreferredPublisher(savedUser, testPublisher);
		PreferredPublisher savedPublisher = preferredPublisherRepository.save(preferredPublisher);
		long preferredPublisherId = savedPublisher.getId();

		// Verify it was added
		assertTrue(preferredPublisherRepository.findById(preferredPublisherId).isPresent());

		// Remove the preferred publisher
		preferredPublisherRepository.deleteById(preferredPublisherId);

		// Verify it was removed
		assertTrue(preferredPublisherRepository.findById(preferredPublisherId).isEmpty());
	}

	@Test
	void userAddsPreferredTeam() throws Exception {
		// Get existing test data or create with large ID
		final Team testTeam;
		if (teamRepository.findAll().isEmpty()) {
			Team team = new Team();
			team.setId(999993L);
			team.setName("Test Team");
			testTeam = teamRepository.save(team);
		} else {
			testTeam = teamRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_team", "password123");
		final User savedUser = userRepository.save(user);

		// Create and save a preferred team
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);
		PreferredTeam savedTeam = preferredTeamRepository.save(preferredTeam);

		// Verify the preferred team was saved
		assertEquals(savedUser.getId(), savedTeam.getUser().getId());
		assertEquals(testTeam.getId(), savedTeam.getTeam().getId());

		// Verify via GET endpoint
		MockHttpServletResponse response = mockMvc.perform(get("/users/" + savedUser.getId() + "/preferredteams"))
				.andReturn().getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	void userRemovesPreferredTeam() throws Exception {
		// Get existing test data or create with large ID
		final Team testTeam;
		if (teamRepository.findAll().isEmpty()) {
			Team team = new Team();
			team.setId(999992L);
			team.setName("Test Team");
			testTeam = teamRepository.save(team);
		} else {
			testTeam = teamRepository.findAll().get(0);
		}

		// Create a test user
		User user = new User("testuser_remove_team", "password123");
		final User savedUser = userRepository.save(user);

		// Add a preferred team
		PreferredTeam preferredTeam = new PreferredTeam(savedUser, testTeam);
		PreferredTeam savedTeam = preferredTeamRepository.save(preferredTeam);
		long preferredTeamId = savedTeam.getId();

		// Verify it was added
		assertTrue(preferredTeamRepository.findById(preferredTeamId).isPresent());

		// Remove the preferred team
		preferredTeamRepository.deleteById(preferredTeamId);

		// Verify it was removed
		assertTrue(preferredTeamRepository.findById(preferredTeamId).isEmpty());
	}
}
