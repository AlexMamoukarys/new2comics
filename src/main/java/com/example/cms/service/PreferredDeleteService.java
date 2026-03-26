package com.example.cms.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class PreferredDeleteService {
    private static final String IDENTIFIER_PATTERN = "[A-Za-z_][A-Za-z0-9_]*";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void foreignDelete(Long preferenceId, Long userId, String tableName, String preferenceColumn) {
        validateIdentifier(tableName, "tableName");
        validateIdentifier(preferenceColumn, "preferenceColumn");

        String sql = String.format(
                "DELETE FROM %s WHERE userId = :userId AND %s = :preferenceId",
                tableName,
                preferenceColumn
        );

        entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("preferenceId", preferenceId)
                .executeUpdate();
    }

    private void validateIdentifier(String identifier, String label) {
        if (identifier == null || !identifier.matches(IDENTIFIER_PATTERN)) {
            throw new IllegalArgumentException("Invalid SQL identifier for " + label + ": " + identifier);
        }
    }
}
