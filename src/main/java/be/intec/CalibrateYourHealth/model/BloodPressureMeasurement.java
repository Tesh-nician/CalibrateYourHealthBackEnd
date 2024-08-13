package be.intec.CalibrateYourHealth.model;
import jakarta.persistence.*;
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
public class BloodPressureMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Systolic (high) pressure must be in mm/Hg, i.e. a number between 0 and 300")
    private int systolicPressure;


    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Diastolic (low) pressure must be in mm/Hg, i.e. a number between 0 and 300")
    private int diastolicPressure;


    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Pulse must be a number between 0 and 300")
    private int pulse;


    @NotBlank
    private LocalDate date;

    private String bloodPressureComment;



    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public BloodPressureMeasurement() {
    }

    public BloodPressureMeasurement(Long id, int systolicPressure, int diastolicPressure, int pulse, LocalDate date, String bloodPressurebComment) {
        this.id = id;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.pulse = pulse;
        this.bloodPressureComment = bloodPressureComment;
        this.date = date;
    }


    // Getters and setters via lombok annotation to class

    @Override
    public String toString() {
        return "BloodPressureMeasurement{" +
                "id=" + id +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", pulse=" + pulse +
                ", bloodPressureComment='" + bloodPressureComment + '\'' +
                ", patient=" + patient +
                ", date=" + date +
                '}';
    }
}
