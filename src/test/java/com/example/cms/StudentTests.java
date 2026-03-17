package com.example.cms;

import com.example.cms.controller.exceptions.StudentNotFoundException;
import com.example.cms.model.entity.Student;
import com.example.cms.model.repository.StudentRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StudentRepository studentRepository;

	@Test
	void getStudent() throws Exception{
		MockHttpServletResponse response = mockMvc.perform(get("/students/1111"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());

		ObjectNode receivedJson = objectMapper.readValue(response.getContentAsString(), ObjectNode.class);
		assertEquals(1111L, receivedJson.get("id").longValue());
		assertEquals("Tyrion", receivedJson.get("firstName").textValue());
		assertEquals("Lannister", receivedJson.get("lastName").textValue());
	}

	@Test
	void addStudent() throws Exception{

		ObjectNode studentJson = objectMapper.createObjectNode();
		studentJson.put("id", 8888L);
		studentJson.put("firstName", "first");
		studentJson.put("lastName", "last");
		studentJson.put("email", "first@last.com");

		MockHttpServletResponse response = mockMvc.perform(
				post("/students").
						contentType("application/json").
						content(studentJson.toString()))
				.andReturn().getResponse();

		// assert HTTP code of response is 200 OK
		assertEquals(200, response.getStatus());

		// Assert student with id 8888 exists in our repository and then get the student object
		assertTrue(studentRepository.findById(8888L).isPresent());
		Student addedStudent = studentRepository.findById(8888L).get();

		// Assert the details of the students are correct
		assertEquals(8888L, addedStudent.getId());
		assertEquals("first", addedStudent.getFirstName());
		assertEquals("last", addedStudent.getLastName());
		assertEquals("first@last.com", addedStudent.getEmail());
	}

	@Test
	void deleteStudent() throws Exception{
		Student s = new Student();
		s.setId(123456L);
		s.setFirstName("first");
		s.setLastName("last");
		s.setEmail("first@last.com");
		studentRepository.save(s);

		MockHttpServletResponse response = mockMvc.perform(
				delete("/students/123456").
						contentType("application/json"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		assertTrue(studentRepository.findById(123456L).isEmpty());
	}

}
