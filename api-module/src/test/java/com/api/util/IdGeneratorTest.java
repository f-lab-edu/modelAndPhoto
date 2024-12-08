package com.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdGeneratorTest {

    @Test
    @DisplayName("Id 길이가 prefix + UUID 만큼이어야 한다.")
    void idLength_correctLength() {
        String generateMessageId = IdGenerator.getGenerateMessageId();
        String generatePhotographerId = IdGenerator.getGeneratePhotographerId();
        String generateModelId = IdGenerator.getGenerateModelId();
        String generateConversationId = IdGenerator.getGenerateConversationId();
        String generateMatchingId = IdGenerator.getGenerateMatchingId();
        String generateFileId = IdGenerator.getGenerateFileId();

        assertThat(generateMessageId).hasSize(40);
        assertThat(generatePhotographerId).hasSize(40);
        assertThat(generateModelId).hasSize(40);
        assertThat(generateConversationId).hasSize(40);
        assertThat(generateMatchingId).hasSize(40);
        assertThat(generateFileId).hasSize(40);
    }
}