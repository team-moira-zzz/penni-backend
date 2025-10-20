package com.moira.pennibackend.global.utility;

import org.springframework.stereotype.Component;

@Component
public class StringDateFormatter {
    public String getYYYYMMDDStr(int year, int month, int day) {
        return "%4d%2d%2d".formatted(year, month, day);
    }

    public String getYYYYMMStr(int year, int month) {
        return "%4d%2d".formatted(year, month);
    }
}
