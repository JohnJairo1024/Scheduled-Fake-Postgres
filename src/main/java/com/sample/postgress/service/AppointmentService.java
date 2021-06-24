package com.sample.postgress.service;

import com.sample.postgress.entity.Appointment;
import com.sample.postgress.entity.Status;
import com.sample.postgress.models.AppointmentResponse;
import com.sample.postgress.models.CreateAppointmentRequest;
import com.sample.postgress.repos.AppointmentRepository;
import com.sample.postgress.repos.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StatusRepository statusRepository;

    private static AppointmentResponse from(final Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getCreatedAt(),
                appointment.getScheduledDate(),
                appointment.getDurationInMinutes(),
                appointment.getDoctorFullName(),
                appointment.getStatus().getStatus(),
                appointment.getPrice()
        );
    }

    public AppointmentResponse create(final CreateAppointmentRequest request) {

        // Find the requested status - if it doesn't exist, we will not move forward.
        final Status status = findStatus(request.status);

        // TODO : Ensure all datetimes are in UTC
        final Appointment appointment = new Appointment(
                request.scheduledDate,
                request.durationInMinutes,
                request.doctorFullName,
                status,
                request.price
        );

        // Persist to the database
        return from(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponse> list() {
        return appointmentRepository
                .findAll()
                .stream()
                .map(AppointmentService::from)
                .collect(Collectors.toList());
    }

    public AppointmentResponse find(final Long id) {
        return from(
                appointmentRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown id %s", id)))
        );
    }

    public AppointmentResponse update(final Long id, final String status) {

        // Find the requested appointment by ID - if it doesn't exist, we cannot update it
        final Appointment foundAppointment = appointmentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown id %s", id)));

        foundAppointment.setStatus(findStatus(status));

        return from(appointmentRepository.save(foundAppointment));
    }

    public AppointmentResponse delete(final Long id) {

        // Find the requested appointment by ID - if it doesn't exist, we cannot update it
        final Appointment foundAppointment = appointmentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown id %s", id)));

        appointmentRepository.delete(foundAppointment);

        return from(foundAppointment);
    }

    public List<AppointmentResponse> search(final ZonedDateTime start, final ZonedDateTime end) {
        return appointmentRepository.findByScheduledDateBetweenOrderByPriceDesc(start, end)
                .stream()
                .map(AppointmentService::from)
                .collect(Collectors.toList());
    }

    public Status findStatus(final String status) {
        return statusRepository
                .findOne(Example.of(new Status(status)))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown status %s", status)));
    }

}
