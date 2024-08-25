package be.intec.CalibrateYourHealth.model;
import jakarta.persistence.*;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "weightmeasurement")
public class WeightMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weightID;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2,3}", message = "Weight in kg must be a number between 0 and 300")
    private double weight;


    private String weightComment;


    @NotBlank
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    public WeightMeasurement() {
    }

    public WeightMeasurement( double weight, LocalDate date, String weightComment) {

        this.weight = weight;
        this.date = date;
        this.weightComment = weightComment;
    }

    public WeightMeasurement(Long weightID, double weight, LocalDate date, String weightComment) {
        this.weightID = weightID;
        this.weight = weight;
        this.date = date;
        this.weightComment = weightComment;
    }


    //TODO: check later if Constructor with weightID is necessary



    @Override
    public String toString() {
        return "WeightMeasurement{" +
                "weightID=" + weightID +
                ", weight=" + weight +
                ", weightComment='" + weightComment + '\'' +
                ", date=" + date +
                ", patient=" + patient +
                '}';
    }
    //get the measurement date, needed to calculate the average weight of a patient
    public LocalDate getMeasurementDate() {
        return this.date;
    }



    // Getters and setters via lombok annotation to class

}
