package com.sample.postgress.repos;


import com.sample.postgress.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result
    List<Appointment> findByScheduledDateBetweenOrderByPriceDesc(final ZonedDateTime start, final ZonedDateTime scheduledDate);

}