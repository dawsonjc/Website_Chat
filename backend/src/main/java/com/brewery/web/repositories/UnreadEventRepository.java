package com.brewery.web.repositories;

import com.brewery.web.model.event.UnreadEvent;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnreadEventRepository extends CassandraRepository<UnreadEvent, UUID> {
    @Query("SELECT * FROM unread_events WHERE user_id = :userId LIMIT :limit")
    List<UnreadEvent> findRecentByUserId(@Param("userId") UUID userId, @Param("limit") int limit);

    @Query("SELECT * FROM unread_events WHERE user_id = :userId")
    List<UnreadEvent> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(*) FROM unread_events WHERE user_id = :userId")
    long countByUserId(@Param("userId") UUID userId);

    @Query("DELETE FROM unread_events WHERE user_id = :userId AND event_id = :eventId")
    void deleteOne(@Param("userId") UUID userId, @Param("eventId") UUID eventId);
}
