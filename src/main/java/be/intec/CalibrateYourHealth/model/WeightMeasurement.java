package be.intec.CalibrateYourHealth.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WeightMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Weight in kg must be a number between 0 and 300")
    private int weight;


    private String weightComment;


    @NotBlank
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public WeightMeasurement() {
    }

    public WeightMeasurement(Long id, int weight, LocalDate date, String weightComment) {
        this.id = id;
        this.weight = weight;
        this.date = date;
        this.weightComment = weightComment;
    }

    @Override
    public String toString() {
        return "WeightMeasurement{" +
                "id=" + id +
                ", weight=" + weight +
                ", weightComment='" + weightComment + '\'' +
                ", date=" + date +
                ", patient=" + patient +
                '}';
    }

    // Getters and setters via lombok annotation to class

}
