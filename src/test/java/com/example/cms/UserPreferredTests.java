package com.example.cms;

import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.Power;
import com.example.cms.model.entity.Publisher;
import com.example.cms.model.entity.Team;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.PowerRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserPreferredTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private CharacterRepository characterRepository;
    @Autowired private GenreRepository genreRepository;
    @Autowired private PowerRepository powerRepository;
    @Autowired private PublisherRepository publisherRepository;
    @Autowired private TeamRepository teamRepository;

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
                .andExpect(status().isOk());

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

    @Test
    void addPreferredGenre() throws Exception {
        List<Genre> genres = genreRepository.findAll();
        assertFalse(genres.isEmpty());
        Genre seededGenre = genres.get(0);
        long genreId = seededGenre.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredgenres/{genreId}", userId, genreId))
                .andExpect(status().isOk());

            mockMvc.perform(get("/users/{id}/preferredgenres", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.genre.id == " + genreId + ")]").isNotEmpty());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredGenre() throws Exception {
        List<Genre> genres = genreRepository.findAll();
        assertFalse(genres.isEmpty());
        Genre seededGenre = genres.get(0);
        long genreId = seededGenre.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredgenres/{genreId}", userId, genreId))
                .andExpect(status().isOk());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    put("/users/{userId}/preferredgenres/remove/{genreId}", userId, genreId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

            mockMvc.perform(get("/users/{id}/preferredgenres", userId))
                .andExpect(status().isOk());

            assertEquals(
                genreId,
                objectMapper.readTree(removeResp.getContentAsString()).path("genre").path("id").asLong()
            );
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredPower() throws Exception {
        List<Power> powers = powerRepository.findAll();
        assertFalse(powers.isEmpty());
        Power seededPower = powers.get(0);
        long powerId = seededPower.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredpowers/{powerId}", userId, powerId))
                .andExpect(status().isOk());

            mockMvc.perform(get("/users/{id}/preferredpowers", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.power.id == " + powerId + ")]").isNotEmpty());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredPower() throws Exception {
        List<Power> powers = powerRepository.findAll();
        assertFalse(powers.isEmpty());
        Power seededPower = powers.get(0);
        long powerId = seededPower.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredpowers/{powerId}", userId, powerId))
                .andExpect(status().isOk());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    put("/users/{userId}/preferredpowers/remove/{powerId}", userId, powerId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

            mockMvc.perform(get("/users/{id}/preferredpowers", userId))
                .andExpect(status().isOk());

            assertEquals(
                powerId,
                objectMapper.readTree(removeResp.getContentAsString()).path("power").path("id").asLong()
            );
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredPublisher() throws Exception {
        List<Publisher> publishers = publisherRepository.findAll();
        assertFalse(publishers.isEmpty());
        Publisher seededPublisher = publishers.get(0);
        long publisherId = seededPublisher.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredpublishers/{publisherId}", userId, publisherId))
                .andExpect(status().isOk());

            mockMvc.perform(get("/users/{id}/preferredpublishers", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.publisher.id == " + publisherId + ")]").isNotEmpty());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredPublisher() throws Exception {
        List<Publisher> publishers = publisherRepository.findAll();
        assertFalse(publishers.isEmpty());
        Publisher seededPublisher = publishers.get(0);
        long publisherId = seededPublisher.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredpublishers/{publisherId}", userId, publisherId))
                .andExpect(status().isOk());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    put("/users/{userId}/preferredpublishers/remove/{publisherId}", userId, publisherId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

            mockMvc.perform(get("/users/{id}/preferredpublishers", userId))
                .andExpect(status().isOk());

            assertEquals(
                publisherId,
                objectMapper.readTree(removeResp.getContentAsString()).path("publisher").path("id").asLong()
            );
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredTeam() throws Exception {
        List<Team> teams = teamRepository.findAll();
        assertFalse(teams.isEmpty());
        Team seededTeam = teams.get(0);
        long teamId = seededTeam.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredteams/{teamId}", userId, teamId))
                .andExpect(status().isOk());

            mockMvc.perform(get("/users/{id}/preferredteams", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.team.id == " + teamId + ")]").isNotEmpty());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredTeam() throws Exception {
        List<Team> teams = teamRepository.findAll();
        assertFalse(teams.isEmpty());
        Team seededTeam = teams.get(0);
        long teamId = seededTeam.getId();

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
            mockMvc.perform(put("/users/{userId}/preferredteams/{teamId}", userId, teamId))
                .andExpect(status().isOk());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    put("/users/{userId}/preferredteams/remove/{teamId}", userId, teamId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

            mockMvc.perform(get("/users/{id}/preferredteams", userId))
                .andExpect(status().isOk());

            assertEquals(
                teamId,
                objectMapper.readTree(removeResp.getContentAsString()).path("team").path("id").asLong()
            );
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }
}