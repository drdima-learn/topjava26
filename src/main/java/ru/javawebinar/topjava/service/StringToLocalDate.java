package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
final public class StringToLocalDate implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        String[] dateAr = source.split("-");
        return LocalDate.of(Integer.valueOf(dateAr[0]), Integer.valueOf(dateAr[1]), Integer.valueOf(dateAr[2]));
    }
}
