package com.ptit.event.repositories;

import com.ptit.event.entities.models.Event;
import com.ptit.event.entities.values.EventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM event e " +
            "WHERE (:#{#filter.name} IS NULL OR e.name ILIKE %:#{#filter.name}%) " +
            "AND (:#{#filter.startDate} IS NULL OR e.start_date >= :#{#filter.startDate}) " +
            "AND (:#{#filter.endDate} IS NULL OR e.end_date <= :#{#filter.endDate}) " +
            "AND (:#{#filter.status} IS NULL OR e.status = :#{#filter.status})",
            countQuery = "SELECT COUNT(*) FROM event e " +
                    "WHERE (:#{#filter.name} IS NULL OR e.name ILIKE %:#{#filter.name}%) " +
                    "AND (:#{#filter.startDate} IS NULL OR e.start_date >= :#{#filter.startDate}) " +
                    "AND (:#{#filter.endDate} IS NULL OR e.end_date <= :#{#filter.endDate}) " +
                    "AND (:#{#filter.status} IS NULL OR e.status = :#{#filter.status})",
            nativeQuery = true)
    Page<Event> filter(@Param("filter") EventFilter filter, Pageable pageable);

}
