package com.example.product_management.util;

import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class CharUtil {
    private static final Pattern PATTERN_ACCENT = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static String normalize(String input) {
        if (!StringUtils.hasText(input)) {
            return input;
        }

        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        return PATTERN_ACCENT.matcher(temp).replaceAll("")
                .toLowerCase().replace("đ", "d");
    }
}
