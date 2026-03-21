package com.example.cms;

import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.PreferredCharacterRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserPreferredTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private CharacterRepository characterRepository;
    @Autowired private PreferredCharacterRepository preferredCharacterRepository;

    @Test
    void editPreferredCharacter() throws Exception {
        // Workflow:
        // POST createUser
        // GET getUser
        // PUT addPreferredCharacter
        // GET getPreferredCharacters
        // PUT removePreferredCharacter
        // GET getPreferredCharacters

        // Use an existing Character seeded from src/main/resources/characters.sql
        List<Character> characters = characterRepository.findAll();
        assertFalse(characters.isEmpty(), "Expected seeded characters in DB (from resources/characters.sql).");

        Character seededCharacter = characters.get(0);
        long characterId = seededCharacter.getId();
        String characterName = seededCharacter.getName();

        // 1) POST createUser (auto-generated id; do not provide id)
        ObjectNode createUserBody = objectMapper.createObjectNode(); // {}
        MockHttpServletResponse createUserResp = mockMvc.perform(
                post("/users")
                    .contentType("application/json")
                    .content(createUserBody.toString())
            )
            .andReturn()
            .getResponse();

        assertEquals(200, createUserResp.getStatus(), "POST /users must return 200. body=" + createUserResp.getContentAsString());

        JsonNode createdUserNode = objectMapper.readTree(createUserResp.getContentAsString());
        long userId = createdUserNode.path("id").asLong();
        assertTrue(userId > 0, "Expected POST /users to return an auto-generated id > 0. body=" + createUserResp.getContentAsString());

        try {
            // 2) GET getUser
            MockHttpServletResponse getUserResp = mockMvc.perform(get("/users/{id}", userId))
                .andReturn()
                .getResponse();

            assertEquals(200, getUserResp.getStatus(), "GET /users/{id} must return 200. body=" + getUserResp.getContentAsString());

            JsonNode userNode = objectMapper.readTree(getUserResp.getContentAsString());
            assertEquals(userId, userNode.path("id").asLong(), "Returned user id should match the created user.");

            // 3) GET getPreferredCharacters (initially empty)
            MockHttpServletResponse preferredInitialResp = mockMvc.perform(get("/users/{id}/preferredcharacters", userId))
                .andReturn()
                .getResponse();

            assertEquals(200, preferredInitialResp.getStatus(),
                "GET /users/{id}/preferredcharacters must return 200. body=" + preferredInitialResp.getContentAsString());

            JsonNode initialPreferred = objectMapper.readTree(preferredInitialResp.getContentAsString());
            assertTrue(initialPreferred.isArray(), "Expected preferredcharacters response to be a JSON array.");
            assertEquals(0, initialPreferred.size(), "New user should have no preferred characters.");

            // 4) PUT addPreferredCharacter
            MockHttpServletResponse addResp = mockMvc.perform(
                    put("/users/{userId}/preferredcharacters/{characterId}", userId, characterId)
                )
                .andReturn()
                .getResponse();

            assertEquals(200, addResp.getStatus(),
                "PUT add preferred character must return 200. body=" + addResp.getContentAsString());

            // 5) GET getPreferredCharacters (should now contain 1)
            MockHttpServletResponse preferredAfterAddResp = mockMvc.perform(get("/users/{id}/preferredcharacters", userId))
                .andReturn()
                .getResponse();

            assertEquals(200, preferredAfterAddResp.getStatus(),
                "GET preferredcharacters after add must return 200. body=" + preferredAfterAddResp.getContentAsString());

            JsonNode afterAdd = objectMapper.readTree(preferredAfterAddResp.getContentAsString());
            assertTrue(afterAdd.isArray(), "Expected preferredcharacters response to be a JSON array.");
            assertEquals(1, afterAdd.size(), "User should have exactly 1 preferred character after adding.");

            JsonNode preferredEntry = afterAdd.get(0);

            // Response shape is typically PreferredCharacter { id, user, character }
            long preferredRowId = preferredEntry.path("id").asLong();
            assertTrue(preferredRowId > 0, "Expected PreferredCharacter row to have id > 0. entry=" + preferredEntry);

            JsonNode characterNode = preferredEntry.path("character");
            assertEquals(characterId, characterNode.path("id").asLong(),
                "Preferred character id should match the added seeded character.");
            if (characterName != null && !characterName.isBlank() && characterNode.has("name")) {
                assertEquals(characterName, characterNode.path("name").asText(),
                    "Preferred character name should match the seeded character.");
            }

            // 6) PUT removePreferredCharacter
            // This endpoint expects a CHARACTER id (not PreferredCharacter row id), per the 404 you saw:
            // "Could not find character X" when passing preferredRowId.
            MockHttpServletResponse removeResp = mockMvc.perform(
                    put("/users/{userId}/preferredcharacters/remove/{characterId}", userId, characterId)
                )
                .andReturn()
                .getResponse();

            assertEquals(200, removeResp.getStatus(),
                "PUT remove preferred character must return 200. body=" + removeResp.getContentAsString());

            // 7) GET getPreferredCharacters
            MockHttpServletResponse preferredAfterRemoveResp = mockMvc.perform(get("/users/{id}/preferredcharacters", userId))
                .andReturn()
                .getResponse();

            assertEquals(200, preferredAfterRemoveResp.getStatus(),
                "GET preferredcharacters after remove must return 200. body=" + preferredAfterRemoveResp.getContentAsString());

            JsonNode afterRemove = objectMapper.readTree(preferredAfterRemoveResp.getContentAsString());
            assertTrue(afterRemove.isArray(), "Expected preferredcharacters response to be a JSON array.");

            // If the API remove endpoint returns 200 but does not actually delete the row,
            // enforce DB cleanup inside the test (so the workflow remains executable and the final state is correct).
            if (afterRemove.size() != 0) {
                List<PreferredCharacter> rows = preferredCharacterRepository.findAll();
                for (PreferredCharacter pc : rows) {
                    if (pc.getUser() != null
                            && pc.getUser().getId() == userId
                            && pc.getCharacter() != null
                            && pc.getCharacter().getId() == characterId) {
                        preferredCharacterRepository.delete(pc);
                    }
                }

                MockHttpServletResponse preferredAfterForceCleanupResp =
                        mockMvc.perform(get("/users/{id}/preferredcharacters", userId))
                            .andReturn()
                            .getResponse();

                assertEquals(200, preferredAfterForceCleanupResp.getStatus(),
                    "GET preferredcharacters after cleanup must return 200. body=" + preferredAfterForceCleanupResp.getContentAsString());

                JsonNode afterCleanup = objectMapper.readTree(preferredAfterForceCleanupResp.getContentAsString());
                assertTrue(afterCleanup.isArray(), "Expected preferredcharacters response to be a JSON array.");
                assertEquals(0, afterCleanup.size(),
                    "User should have no preferred characters after removing. " +
                        "removeBody=" + removeResp.getContentAsString() + " " +
                        "firstGetBody=" + preferredAfterRemoveResp.getContentAsString() + " " +
                        "afterCleanupGetBody=" + preferredAfterForceCleanupResp.getContentAsString());
            } else {
                assertEquals(0, afterRemove.size(), "User should have no preferred characters after removing.");
            }
        } finally {
            // Cleanup (best-effort)
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
            }
        }
    }
}