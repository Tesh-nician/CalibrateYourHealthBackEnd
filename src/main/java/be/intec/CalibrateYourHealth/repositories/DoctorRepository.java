package be.intec.CalibrateYourHealth.repositories;

import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import be.intec.CalibrateYourHealth.model.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByID(Long id); //for patient and admin

    List<Doctor> findAll(); //for admin and patient

    List<Doctor> findDoctorsByPatientId(Long patientID); // for patient and patient

    List<Doctor> findDoctorsByLastNameContaining(String lastnameContains); // for patient and patient

    Doctor add(Doctor doctor); // for Doctor

    void deleteDoctorFromPatientById(Long id);// for admin and patient

    //Doctor updateDoctorPassword(Doctor doctor, String password); //for admin:if patient forgets password, reset password.


}
