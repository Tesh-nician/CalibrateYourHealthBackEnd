package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import java.util.List;
import java.util.Optional;



public interface DoctorService {

    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Long id);
    Optional<Doctor> getDoctorByUserName(String userName); //used for login

    Optional <List<Doctor>> getDoctorsByLastnameContaining(String lastnameContains);

    //List<Patient> getPatientsByDoctorId(Long doctorId);

    //Patient addPatientToDoctor(Long doctorId, Long patientId);

    //void deletePatientFromDoctorById(Long id);

    Doctor saveDoctor(Doctor doctor);

    Doctor updateDoctorPassword(Doctor doctor, String password);

    void deleteDoctorById(Long id);
}
