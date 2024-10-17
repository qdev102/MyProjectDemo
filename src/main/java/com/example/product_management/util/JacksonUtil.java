package com.example.product_management.util;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

public final class JacksonUtil {
    private final ObjectMapper objectMapper;

    private JacksonUtil() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature());
    }

    private static class SingletonHelper {
        private static final JacksonUtil INSTANCE = new JacksonUtil();
    }

    @SneakyThrows
    public static <T> T stringToObject(String input, final Class<T> clazz) {
        return SingletonHelper.INSTANCE.objectMapper.readValue(input, clazz);
    }

    @SneakyThrows
    public static <T> T stringToObject(String input, final TypeReference<T> clazz) {
        return SingletonHelper.INSTANCE.objectMapper.readValue(input, clazz);
    }

    @SneakyThrows
    public static String objectToString(Object obj) {
        return SingletonHelper.INSTANCE.objectMapper.writeValueAsString(obj);
    }
}
