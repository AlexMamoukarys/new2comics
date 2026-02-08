package com.example.cms;

import com.example.cms.controller.exceptions.CourseNotFoundException;
import com.example.cms.model.entity.Course;
import com.example.cms.model.repository.CourseRepository;
import com.example.cms.model.repository.ProfessorRepository;
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
class CourseProfessorTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Test
	void addProfessorAndCourse() throws Exception{

		ObjectNode profJson = objectMapper.createObjectNode();
		profJson.put("id", 10000);
		profJson.put("firstName", "first");
		profJson.put("lastName", "last");
		profJson.put("office", "BA0000");
		profJson.put("email", "first.last@prof.com");

		MockHttpServletResponse response_profs = mockMvc.perform(post("/professors").
				contentType("application/json").
				content(profJson.toString())).
				andReturn().getResponse();

		assertEquals(200, response_profs.getStatus());

		ObjectNode courseJson = objectMapper.createObjectNode();
		courseJson.put("name", "New Course");
		courseJson.put("code", "NEW100");
		courseJson.put("professorId", 10000);

		MockHttpServletResponse response_courses = mockMvc.perform(post("/courses").
				contentType("application/json").
				content(courseJson.toString())).
				andReturn().getResponse();

		assertEquals(200, response_courses.getStatus());

		// Assert course with code NEW100 exists in our repository and then get the course object
		assertTrue(courseRepository.findById("NEW100").isPresent());
		Course addedCourse = courseRepository.findById("NEW100").get();

		// Assert the details of the course and the associateed professor are correct
		assertEquals("NEW100", addedCourse.getCode());
		assertEquals("New Course", addedCourse.getName());

		assertEquals(10000L, addedCourse.getProfessor().getId());
		assertEquals("first", addedCourse.getProfessor().getFirstName());
		assertEquals("last", addedCourse.getProfessor().getLastName());
		assertEquals("BA0000", addedCourse.getProfessor().getOffice());
		assertEquals("first.last@prof.com", addedCourse.getProfessor().getEmail());
	}

}
