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
import com.fasterxml.jackson.databind.JsonNode;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
        assertTrue(!characters.isEmpty());
        long characterId = characters.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredcharacters/{characterId}", userId, characterId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredcharacters", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredCharacters = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(1, preferredCharacters.size());
            assertEquals(characterId, preferredCharacters.get(0).path("character").path("id").asLong());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredCharacter() throws Exception {
        List<Character> characters = characterRepository.findAll();
        assertTrue(!characters.isEmpty());
        long characterId = characters.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredcharacters/{characterId}", userId, characterId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    delete("/preferredcharacters/{user_id}/{char_id}", userId, characterId))
                    .andReturn().getResponse();
            assertEquals(200, removeResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredcharacters", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredCharacters = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(0, preferredCharacters.size());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredGenre() throws Exception {
        List<Genre> genres = genreRepository.findAll();
        assertTrue(!genres.isEmpty());
        long genreId = genres.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredgenres/{genreId}", userId, genreId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredgenres", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredGenres = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(1, preferredGenres.size());
            assertEquals(genreId, preferredGenres.get(0).path("genre").path("id").asLong());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredGenre() throws Exception {
        List<Genre> genres = genreRepository.findAll();
        assertTrue(!genres.isEmpty());
        long genreId = genres.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredgenres/{genreId}", userId, genreId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    delete("/preferredgenres/{user_id}/{genre_id}", userId, genreId))
                    .andReturn().getResponse();
            assertEquals(200, removeResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredgenres", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredGenres = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(0, preferredGenres.size());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredPower() throws Exception {
        List<Power> powers = powerRepository.findAll();
        assertTrue(!powers.isEmpty());
        long powerId = powers.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredpowers/{powerId}", userId, powerId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredpowers", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredPowers = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(1, preferredPowers.size());
            assertEquals(powerId, preferredPowers.get(0).path("power").path("id").asLong());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredPower() throws Exception {
        List<Power> powers = powerRepository.findAll();
        assertTrue(!powers.isEmpty());
        long powerId = powers.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredpowers/{powerId}", userId, powerId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    delete("/preferredpowers/{user_id}/{power_id}", userId, powerId))
                    .andReturn().getResponse();
            assertEquals(200, removeResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredpowers", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredPowers = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(0, preferredPowers.size());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredPublisher() throws Exception {
        List<Publisher> publishers = publisherRepository.findAll();
        assertTrue(!publishers.isEmpty());
        long publisherId = publishers.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredpublishers/{publisherId}", userId, publisherId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredpublishers", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredPublishers = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(1, preferredPublishers.size());
            assertEquals(publisherId, preferredPublishers.get(0).path("publisher").path("id").asLong());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredPublisher() throws Exception {
        List<Publisher> publishers = publisherRepository.findAll();
        assertTrue(!publishers.isEmpty());
        long publisherId = publishers.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredpublishers/{publisherId}", userId, publisherId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    delete("/preferredpublishers/{user_id}/{publishers_id}", userId, publisherId))
                    .andReturn().getResponse();
            assertEquals(200, removeResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredpublishers", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredPublishers = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(0, preferredPublishers.size());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void addPreferredTeam() throws Exception {
        List<Team> teams = teamRepository.findAll();
        assertTrue(!teams.isEmpty());
        long teamId = teams.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredteams/{teamId}", userId, teamId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredteams", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredTeams = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(1, preferredTeams.size());
            assertEquals(teamId, preferredTeams.get(0).path("team").path("id").asLong());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }

    @Test
    void removePreferredTeam() throws Exception {
        List<Team> teams = teamRepository.findAll();
        assertTrue(!teams.isEmpty());
        long teamId = teams.get(0).getId();

        ObjectNode createUserBody = objectMapper.createObjectNode();
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(createUserBody.toString()))
                .andReturn().getResponse();

        assertEquals(200, createUserResp.getStatus());
        long userId = objectMapper.readTree(createUserResp.getContentAsString()).path("id").asLong();
        assertTrue(userId > 0);

        try {
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredteams/{teamId}", userId, teamId))
                    .andReturn().getResponse();
            assertEquals(200, addResp.getStatus());

            MockHttpServletResponse removeResp = mockMvc.perform(
                    delete("/preferredteams/{user_id}/{teams_id}", userId, teamId))
                    .andReturn().getResponse();
            assertEquals(200, removeResp.getStatus());

            MockHttpServletResponse getResp = mockMvc.perform(
                    get("/users/{id}/preferredteams", userId))
                    .andReturn().getResponse();
            assertEquals(200, getResp.getStatus());

            JsonNode preferredTeams = objectMapper.readTree(getResp.getContentAsString());
            assertEquals(0, preferredTeams.size());
        } finally {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }
}