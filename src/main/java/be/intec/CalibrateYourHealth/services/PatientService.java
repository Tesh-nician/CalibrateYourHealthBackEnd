package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);

    List<Patient> getPatientsByDoctorId(Long doctorId);

    Doctor addDoctorToPatient(Long patientId, Long doctorId);

    Patient savePatient(Patient patient);

    Patient updatePatientPassword(Patient patient, String password);

    void deletePatientFromDoctorById(Long id);

    void deletePatientById(Long id);




}
