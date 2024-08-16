package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.repositories.DoctorRepository;
import be.intec.CalibrateYourHealth.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientServiceImplementation implements PatientService {

    private final DoctorRepository newDoctorRepository;
    private final PatientRepository newpatientRepository;

    @Autowired
    public PatientServiceImplementation(DoctorRepository newDoctorRepository, PatientRepository newpatientRepository) {
        this.newDoctorRepository = newDoctorRepository;
        this.newpatientRepository = newpatientRepository;
            }


    // Implementing methods for CRUD operations on Patient entities here

    @Override
    public List<Patient> getAllPatients() {
        return newpatientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return newpatientRepository.findPatientById(id);
    }

    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        return newpatientRepository.findPatientsByDoctorId(doctorId);
    }

    @Override
    public Doctor addDoctorToPatient(Long patientId, Long doctorId) {
        Optional<Patient> patient = newpatientRepository.findPatientById(patientId);
        Optional<Doctor> doctor = newDoctorRepository.findByID(doctorId);
        if (patient.isPresent() && doctor.isPresent()) {
            patient.get().addDoctor(doctor.get());
            newpatientRepository.save(patient.get());
            return doctor.get();
        }
        return null;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return newpatientRepository.save(patient);
    }

    @Override
    public Patient updatePatientPassword(Patient patient, String password) {
        return newpatientRepository.updatePatientPassword(patient, password);
    }

    @Override
    public void deletePatientFromDoctorById(Long id) {
        newpatientRepository.deletePatientFromDoctorById(id);
        newDoctorRepository.deleteDoctorFromPatientById(id);
    }

    @Override
    public void deletePatientById(Long id) {
        newpatientRepository.deleteById(id);
    }



}
