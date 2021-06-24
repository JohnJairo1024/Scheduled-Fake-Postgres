package com.sample.postgress.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "status", updatable = false, nullable = false)
    private String status;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments;

    // JPA requires a default constructor - we'll need to keep the getters / setters to satisfy it
    public Status() {
    }

    // Generally, prefer using a constructor where possible so we don't accidentally miss invoking a requisite setter
    public Status(final String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(final Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}