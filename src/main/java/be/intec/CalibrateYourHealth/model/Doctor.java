package be.intec.CalibrateYourHealth.model;

import jakarta.persistence.*;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;





@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorID;


    @NotBlank(message = "First name is required")
    private String firstName;


    @NotBlank(message = "Last name is required")
    private String lastName;


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

   /*@Column(unique = true)
    private Set<Patient> myPatients = new Set<>();
    */




    //No-args constructor
    public Doctor() {
    }

    //Constructor with doctorID
    public Doctor(Long doctorID) {
        this.doctorID = doctorID;
    }

    //Constructor with all fields
    public Doctor(Long doctorID, String firstName, String lastName, long rizivNumber, String password, List<Patient> myPatients) {
        this.doctorID = doctorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rizivNumber = rizivNumber;
        this.setPassword(password);
        //this.myPatients = myPatients;
    }


    //Constructor without password or patients
    public Doctor(String firstName, String lastName, long rizivNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rizivNumber = rizivNumber;
    }

    //Constructor with password and patients
    public Doctor(String firstName, String lastName, long rizivNumber, String password, List<Patient> myPatients) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rizivNumber = rizivNumber;
        this.setPassword(password);
        //this.myPatients = myPatients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return getRizivNumber() == doctor.getRizivNumber() && Objects.equals(getDoctorID(), doctor.getDoctorID()) && Objects.equals(getFirstName(), doctor.getFirstName()) && Objects.equals(getLastName(), doctor.getLastName());
    }

    /*
    //add patient to List <Patients> myPatients
    public void addPatient(Patient patient) {
        //myPatients.add(patient);
        patient.addDoctor(this); //add doctor to patient's list of doctors
    }


    //remove patient from List <Patients> myPatients
    public void removePatient(Patient patient) {
        //myPatients.remove(patient);
        patient.removeDoctor(this); //remove doctor from patient's list of doctors
    }

     */

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
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
