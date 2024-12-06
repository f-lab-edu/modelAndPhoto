package com.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    @DisplayName("잘못된 ID 생성 테스트")
    void generateId_wrongId() {
        assertThrows(IllegalArgumentException.class, () -> IdGenerator.generateId(null));
    }

    @Test
    @DisplayName("Id 길이가 prefix + UUID 만큼이어야 한다.")
    void idLength_correctLength() {
        String generateId = IdGenerator.generateId(IdGenerator.getPrefixMessage());
        assertThat(generateId).hasSize(40);
    }
}