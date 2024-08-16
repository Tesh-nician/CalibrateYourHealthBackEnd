package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import java.util.List;
import java.util.Optional;



public interface DoctorService {

    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Long id);

    List<Patient> getPatientsByDoctorId(Long doctorId);

    Patient addPatientToDoctor(Long doctorId, Long patientId);

    Doctor saveDoctor(Doctor doctor);

    Doctor updateDoctorPassword(Doctor doctor, String password);

    void deletePatientFromDoctorById(Long id);

    void deleteDoctorById(Long id);
}
