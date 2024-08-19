package be.intec.CalibrateYourHealth.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Data
@Table(name = "patient")
public class Patient {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientID;

    @Getter
    @Setter
    @NotBlank(message = "First name is required")
    private String firstName;

    @Getter
    @Setter
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Getter
    @Setter
    @Column(unique = true)
    @NotBlank(message = "date of birth is required")
    private LocalDate dateOfBirth;

    @Getter
    @Setter
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

    @Getter
    @Setter
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> myDoctors = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BloodPressureMeasurement> myBloodPressureMeasurements;


    public Patient() {
    }

    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String password, List<Doctor> myDoctors) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.myDoctors = myDoctors;
    }

    //Constructor without password or doctors
    public Patient(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

        //TODO: check later if Constructor with patientID is necessary

    //standard getters and setters have been added to this class with lombok annotation


    //add a doctor to List<Doctors> myDoctors
    public void addDoctor(Doctor doctor) {
        myDoctors.add(doctor);
        doctor.addPatient(this); //add patient to doctor's list of patients
    }

    //remove a doctor from List<Doctors> myDoctors
    public void removeDoctor(Doctor doctor) {
        myDoctors.remove(doctor);
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }


    @Override
    public String toString() {
        return "Patient{" +
                "id=" + patientID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", myDoctors=" + myDoctors +
                ", myBloodPressureMeasurements=" + myBloodPressureMeasurements +
                '}';
    }

    public Doctor getDoctor(long doctorID) {
        for (Doctor doctor : myDoctors) {
            if (doctor.getDoctorID() == doctorID) {
                return doctor;
            }
        }
        return null;
    }
}
