package be.intec.CalibrateYourHealth.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NeuroMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Balance measurement in seconds must be a number between 0 and 300")
    private int neuroMeasurement;

    private String neuroComment;

    @NotBlank
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public NeuroMeasurement() {
    }

    public NeuroMeasurement(Long id, int neuroMeasurement, LocalDate date, String neuroComment) {
        this.id = id;
        this.neuroMeasurement = neuroMeasurement;
        this.date = date;
        this.neuroComment = neuroComment;
    }

    @Override
    public String toString() {
        return "NeuroMeasurement{" +
                "id=" + id +
                ", neuroMeasurement=" + neuroMeasurement +
                ", neuroComment='" + neuroComment + '\'' +
                ", date=" + date +
                ", patient=" + patient +
                '}';
    }

    // Getters and setters via lombok annotation to class
}
