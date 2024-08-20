package be.intec.CalibrateYourHealth.model;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "bloodpressuremeasurement")
public class BloodPressureMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bloodPressureID;


    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Systolic (high) pressure must be in mm/Hg, i.e. a number between 0 and 300")
    private double systolicPressure;


    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Diastolic (low) pressure must be in mm/Hg, i.e. a number between 0 and 300")
    private double diastolicPressure;


    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Pulse must be a number between 0 and 300")
    private double pulse;


    @NotBlank
    private LocalDate date;

    private String bloodPressureComment;


    @ManyToOne
    @JoinColumn(name = "patient_patientID")
    private Patient patient;

    public BloodPressureMeasurement() {
    }

    public BloodPressureMeasurement(int systolicPressure, int diastolicPressure, int pulse, LocalDate date, String bloodPressurebComment) {
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.pulse = pulse;
        this.bloodPressureComment = bloodPressureComment;
        this.date = date;
    }

    //TODO: check later if Constructor with bloodPressureID is necessary

    //BloodPressureMeasurement constructor with systolic, diastolic and pulse.
    // Used to return the average blood pressure in the service.
    public BloodPressureMeasurement(double systolicPressure, double diastolicPressure, double pulse) {
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.pulse = pulse;
    }


    // Getters and setters via lombok annotation to class

    @Override
    public String toString() {
        return "BloodPressureMeasurement{" +
                "id=" + bloodPressureID +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", pulse=" + pulse +
                ", bloodPressureComment='" + bloodPressureComment + '\'' +
                ", patient=" + patient +
                ", date=" + date +
                '}';
    }

    public LocalDate getMeasurementDate() {
        return this.date;
    }
}
