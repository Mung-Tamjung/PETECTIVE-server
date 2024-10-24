package com.mungtamjung.petective.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

import java.io.IOException;

public class LatLngConverter implements AttributeConverter<LatLng, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LatLng latLng) {
        try {
            return objectMapper.writeValueAsString(latLng);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting LatLng to JSON string.", e);
        }
    }

    @Override
    public LatLng convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, LatLng.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON string to LatLng.", e);
        }
    }
}
