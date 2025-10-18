package com.moira.pennibackend.domain.group.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

import static com.moira.pennibackend.global.constant.PenniConstant.CHARACTERS;
import static com.moira.pennibackend.global.constant.PenniConstant.CODE_LENGTH;

@Component
@RequiredArgsConstructor
public class GroupCodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateRandomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
