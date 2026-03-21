package com.example.cms;

import com.example.cms.controller.exceptions.*;
import com.example.cms.model.repository.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.Character;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
	private PreferredCharacterRepository preferredCharacterRepository;

	@Autowired
	private PreferredGenreRepository preferredGenreRepository;

	@Autowired
	private PreferredTeamRepository preferredTeamRepository;

	@Autowired
	private PreferredPublisherRepository preferredPublisherRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private PublisherRepository publisherRepository;
	
    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private TeamRepository teamRepository;

	@Test
	void addPreferredCharacter() throws Exception{

		ObjectNode userJson = objectMapper.createObjectNode();
		userJson.put("id", 10000);
		userJson.put("firstName", "first");
		userJson.put("lastName", "last");
		userJson.put("office", "BA0000");
		userJson.put("email", "first.last@prof.com");

		MockHttpServletResponse response_profs = mockMvc.perform(post("/professors").
				contentType("application/json").
				content(userJson.toString())).
				andReturn().getResponse();

		assertEquals(200, response_profs.getStatus());

		ObjectNode characterJson = objectMapper.createObjectNode();
		characterJson.put("name", "New Course");
		characterJson.put("code", "NEW100");
		characterJson.put("professorId", 10000);

		MockHttpServletResponse response_courses = mockMvc.perform(post("/courses").
				contentType("application/json").
				content(characterJson.toString())).
				andReturn().getResponse();

		assertEquals(200, response_courses.getStatus());

		// Assert course with code NEW100 exists in our repository and then get the course object
		assertTrue(characterRepository.findById("NEW100").isPresent());
		Character addedCharacter = characterRepository.findById("NEW100").get();

		// Assert the details of the course and the associateed professor are correct
		assertEquals("NEW100", addedCharacter.getCode());
		assertEquals("New Course", addedCharacter.getName());

		assertEquals(10000L, addedCharacter.getProfessor().getId());
		assertEquals("first", addedCharacter.getProfessor().getFirstName());
		assertEquals("last", addedCharacter.getProfessor().getLastName());
		assertEquals("BA0000", addedCharacter.getProfessor().getOffice());
		assertEquals("first.last@prof.com", addedCharacter.getProfessor().getEmail());
	}

}
