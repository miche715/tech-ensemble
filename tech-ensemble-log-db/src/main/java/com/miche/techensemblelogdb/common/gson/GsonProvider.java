package com.miche.techensemblelogdb.common.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GsonProvider {
    private static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMMDD = "yyyy-MM-dd";
    private static final String HHMMSS = "HH:mm:ss";

    public static Gson getInstance() {
        return LazyHolder.GSON;
    }

    private static class LazyHolder {
        private static final Gson GSON = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat(YYYYMMDDHHMMSS)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter().nullSafe())
                .create();
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS);

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            if(localDateTime != null) jsonWriter.value(localDateTime.format(dateTimeFormatter));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), dateTimeFormatter);
        }
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS);

        @Override
        public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
            if(localDate != null) jsonWriter.value(localDate.format(dateTimeFormatter));
        }

        @Override
        public LocalDate read(JsonReader jsonReader) throws IOException {
            return LocalDate.parse(jsonReader.nextString(), dateTimeFormatter);
        }
    }

    private static class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(HHMMSS);

        @Override
        public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {
            if(localTime != null) jsonWriter.value(localTime.format(dateTimeFormatter));
        }

        @Override
        public LocalTime read(JsonReader jsonReader) throws IOException {
            return LocalTime.parse(jsonReader.nextString(), dateTimeFormatter);
        }
    }
}