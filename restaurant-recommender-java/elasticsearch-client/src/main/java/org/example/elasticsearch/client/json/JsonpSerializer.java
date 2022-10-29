package org.example.elasticsearch.client.json;

import jakarta.json.stream.JsonGenerator;

public interface JsonpSerializer<T> {
    void serialize(T value, JsonGenerator generator, JsonpMapper mapper);
}