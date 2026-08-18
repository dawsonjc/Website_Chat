package com.brewery.web.model.record;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class RecordStatusToStringConverter implements Converter<RecordStatus, String> {
    @Override
    public String convert(RecordStatus source) {
        return source.getStatus();
    }
}
