package be.intec.CalibrateYourHealth.repositories;


import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientById(Long id); //for doctor and admin
    //Optional list of all patients
    List<Patient> findAll(); //for admin and doctor

    //List<Patient> findPatientsById(Long doctorId); // for patient and doctor




    Optional <Patient> findPatientByUsername(String userName); // for patient

    void deletePatientById(Long id); //for admin and doctor

    //Patient updatePatientPassword(Patient patient, String password); //for admin:if patient forgets password, reset password.


}
