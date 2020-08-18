package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogEventRepository extends JpaRepository<LogEvent, Long> {

    @Query("SELECT logEvent FROM LogEvent logEvent " +
            "WHERE (:level is null or logEvent.level = :level) " +
            "and (:description is null or logEvent.description = :description) " +
            "and (:log is null or logEvent.log = :log) " +
            "and (:source is null or logEvent.source = :source) " +
            "and (logEvent.date BETWEEN :startDate and :endDate) " +
            "and (:quantity is null or logEvent.quantity = :quantity)")
    Page<LogEvent> findAllByFilters(@Param("level") Level level,
                                    @Param("description") String description,
                                    @Param("log") String log,
                                    @Param("source") User source,
                                    @Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate,
                                    @Param("quantity") Integer quantity,
                                    Pageable pageable);

}
