package com.api.util;

import com.api.enums.IdPrefix;
import org.springframework.stereotype.Component;

/**
 * Id를 부여하는 Generator
 * id 채번 rule : {prefix}_UUID
 * prefix : IdPrefix
 *
 */
@Component
public class IdGenerator {

    public String generateId(IdPrefix prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix is null");
        }

        return prefix + "_" + java.util.UUID.randomUUID().toString();
    }
}
