package be.intec.CalibrateYourHealth.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorID;

    @Getter
    @NotBlank(message = "First name is required")
    private String firstName;

    @Getter
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Getter
    @Column(unique = true)
    @NotBlank(message = "RIZIV number is required")
    private long rizivNumber;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{1,6}$",
            message = "Your password must contain at least one capital letter, one number, one lowercase letter and must be at least 6 characters long."
    )
    @Column(length = 60)
    private String password;

    @Transient
    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> myPatients = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return getRizivNumber() == doctor.getRizivNumber() && Objects.equals(getDoctorID(), doctor.getDoctorID()) && Objects.equals(getFirstName(), doctor.getFirstName()) && Objects.equals(getLastName(), doctor.getLastName());
    }

    //add patient to List <Patients> myPatients
    public void addPatient(Patient patient) {
        myPatients.add(patient);
        patient.addDoctor(this); //add doctor to patient's list of doctors
    }


    //remove patient from List <Patients> myPatients
    public void removePatient(Patient patient) {
        myPatients.remove(patient);
        patient.removeDoctor(this); //remove doctor from patient's list of doctors
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDoctorID(), getFirstName(), getLastName(), getRizivNumber());
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + doctorID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rizivNumber=" + rizivNumber +
                '}';
    }
}
