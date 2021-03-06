package com.sample.postgress.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

public class CreateAppointmentRequest {

    @NotNull
    @Future
    @DateTimeFormat(iso = ISO.DATE_TIME)
    public final ZonedDateTime scheduledDate;

    @NotNull
    @Positive
    public final Integer durationInMinutes;

    @NotBlank
    public final String doctorFullName;

    @Pattern(regexp = "Available|Booked")
    public final String status;

    @NotNull
    @NumberFormat(style = Style.CURRENCY)
    public final BigDecimal price;

    @JsonCreator
    public CreateAppointmentRequest(
            @JsonProperty final ZonedDateTime scheduledDate,
            @JsonProperty final Integer durationInMinutes,
            @JsonProperty final String doctorFullName,
            @JsonProperty final String status,
            @JsonProperty final BigDecimal price
    ) {
        this.scheduledDate = scheduledDate;
        this.durationInMinutes = durationInMinutes;
        this.doctorFullName = doctorFullName;
        this.status = status;
        this.price = price.setScale(2, RoundingMode.CEILING);
    }
}