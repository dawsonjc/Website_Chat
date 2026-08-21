package com.brewery.web.repositories;

import com.brewery.web.model.event.Event;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends CassandraRepository<Event, UUID> {
    @Query("SELECT * FROM event WHERE user_id = :userId LIMIT :limit")
    List<Event> findRecentByUserId(@Param("userId") UUID userId, @Param("limit") int limit);

    @Query("SELECT * FROM event WHERE user_id = :userId AND event_id = :eventId")
    Event findOne(@Param("userId") UUID userId, @Param("eventId") UUID eventId);
}
