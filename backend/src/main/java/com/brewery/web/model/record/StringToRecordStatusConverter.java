package com.brewery.web.model.record;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToRecordStatusConverter implements Converter<String, RecordStatus> {
    @Override
    public RecordStatus convert(String source) {
        if(source == null) return null;
        return RecordStatus.fromString(source);
    }
}
