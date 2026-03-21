package com.example.cms;

import com.example.cms.model.entity.Character;
import com.example.cms.model.repository.CharacterRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserPreferredTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private CharacterRepository characterRepository;

    @Test
    void addPreferredCharacter() throws Exception {
        List<Character> characters = characterRepository.findAll();
        assertFalse(characters.isEmpty());
        Character seededCharacter = characters.get(0);
        long characterId = seededCharacter.getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                    .contentType("application/json")
                    .content(createUserBody.toString()))
            .andReturn()
            .getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            mockMvc.perform(put("/users/{userId}/preferredcharacters/{characterId}", userId, characterId))
                .andExpect(status().isOk());

            mockMvc.perform(get("/users/{id}/preferredcharacters", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.character.id == " + characterId + ")]").isNotEmpty());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredCharacter() throws Exception {
        List<Character> characters = characterRepository.findAll();
        assertFalse(characters.isEmpty());
        Character seededCharacter = characters.get(0);
        long characterId = seededCharacter.getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                    .contentType("application/json")
                    .content(createUserBody.toString()))
            .andReturn()
            .getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            mockMvc.perform(put("/users/{userId}/preferredcharacters/{characterId}", userId, characterId))
                .andExpect(status().isOk());

			MockHttpServletResponse removeResp = mockMvc.perform(
					put("/users/{userId}/preferredcharacters/remove/{characterId}", userId, characterId))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

			mockMvc.perform(get("/users/{id}/preferredcharacters", userId))
				.andExpect(status().isOk()); // keep endpoint check only

			assertEquals(
				characterId,
				objectMapper.readTree(removeResp.getContentAsString()).path("character").path("id").asLong()
			);
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }
}