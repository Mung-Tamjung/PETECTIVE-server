package com.mungtamjung.petective.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Converter
public class LatLngListConverter implements AttributeConverter<List<LatLng>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LatLng> latLngList) {
        try {
            return objectMapper.writeValueAsString(latLngList);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting LatLng list to JSON string.", e);

        }
    }

    @Override
    public List<LatLng> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<LatLng>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON string to LatLng list.", e);

        }
    }
}