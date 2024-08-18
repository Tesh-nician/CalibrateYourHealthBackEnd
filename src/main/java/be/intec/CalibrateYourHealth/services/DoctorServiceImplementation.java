package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import java.util.List;
import java.util.Optional;

import be.intec.CalibrateYourHealth.repositories.DoctorRepository;
import be.intec.CalibrateYourHealth.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImplementation implements DoctorService {

    private final DoctorRepository newDoctorRepository;
    private final PatientRepository newPatientRepository;

    @Autowired
    public DoctorServiceImplementation(DoctorRepository newDoctorRepository, PatientRepository newPatientRepository) {
        this.newDoctorRepository = newDoctorRepository;
        this.newPatientRepository = newPatientRepository;
    }

    // Implementing methods for CRUD operations on Doctor entities here

    @Override
    public List<Doctor> getAllDoctors() {
        return newDoctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return newDoctorRepository.findByID(id);
    }

    @Override
    public Optional<List<Doctor>> getDoctorsByLastnameContaining(String lastnameContains) {
        return Optional.of(newDoctorRepository.findDoctorsByLastNameContaining(lastnameContains));
    }

    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        return newPatientRepository.findPatientsByDoctorId(doctorId);
    }

    @Override
    public Patient addPatientToDoctor(Long doctorId, Long patientId) {
        Optional<Doctor> doctor = newDoctorRepository.findByID(doctorId);
        Optional<Patient> patient = newPatientRepository.findPatientById(patientId);
        if (doctor.isPresent() && patient.isPresent()) {
            doctor.get().addPatient(patient.get());
            newDoctorRepository.save(doctor.get());

            return patient.get();
        }
        return null;
    }


    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return newDoctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctorPassword(Doctor doctor, String password) {
        doctor.setPassword(password);
        return newDoctorRepository.save(doctor);
    }

    @Override
    public void deletePatientFromDoctorById(Long id) {
        newPatientRepository.deletePatientFromDoctorById(id);
        newDoctorRepository.deleteDoctorFromPatientById(id);
    }

    @Override
    public void deleteDoctorById(Long id) {
        newDoctorRepository.deleteById(id);
    }







}
