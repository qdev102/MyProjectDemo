package com.example.product_management.util;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertModel {

    private static final ModelMapper modelMapper = new ModelMapper();

    static Converter<String, Date> str2Date = new AbstractConverter<>() {
        @Override
        protected Date convert(String source) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                return format.parse(source);
            } catch (ParseException e) {
                return null;
            }
        }
    };

    static {
        modelMapper.createTypeMap(String.class, Date.class);
        modelMapper.addConverter(str2Date);
    }

    public static <T, U> U convert(T source, Class<U> targetClass) {
        return modelMapper.map(source, targetClass);
    }

}
