package com.example.cms;

import com.example.cms.model.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CharacterControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CharacterRepository characterRepository;

	@Test
	void retrieveAllCharacters() throws Exception {
		// Call endpoint
		MockHttpServletResponse response = mockMvc.perform(
				get("/characters"))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
	}

}
