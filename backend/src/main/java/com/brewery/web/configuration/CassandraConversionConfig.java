package com.brewery.web.configuration;

import com.brewery.web.model.record.RecordStatusToStringConverter;
import com.brewery.web.model.record.StringToRecordStatusConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;

@Configuration
public class CassandraConversionConfig {
    @Bean
    public CassandraCustomConversions cassandraCustomConversions() {
        return CassandraCustomConversions.create(adapter -> adapter.registerConverters(
                new StringToRecordStatusConverter(),
                new RecordStatusToStringConverter()
        ));
    }
}
