package com.brewery.web.repositories;

import com.brewery.web.model.event.EventType;
import com.brewery.web.model.event.EventsByType;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventsByTypeRepository extends CassandraRepository<EventsByType, UUID> {
    @Query("SELECT * FROM events_by_type WHERE user_id = :userId AND event_type = :eventType LIMIT :limit")
    List<EventsByType> findRecentByUserIdAndType(
            @Param("userId") UUID userId,
            @Param("eventType") EventType eventType,
            @Param("limit") int limit
    );
}
