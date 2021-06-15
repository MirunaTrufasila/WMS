package com.lts.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void serialize(LocalTime date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        synchronized (dtf) {
            jsonGenerator.writeString(date.format(dtf));
        }
    }
}
