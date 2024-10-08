package be.intec.CalibrateYourHealth.repositories;


import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NeuroMeasurementRepository extends JpaRepository<NeuroMeasurement, Long> {

   NeuroMeasurement findNeuroMeasurementByNeuroID(Long id); //for doctor and patient

    List<NeuroMeasurement> findAll(); //for doctor and patient

    List <NeuroMeasurement> findAllByPatientId(Long patientID); //for doctor and patient

    List<NeuroMeasurement> findAllByPatient(Patient patient); // for patient and doctor

    void deleteNeuroMeasurementByNeuroID(Long id);// for patient, delete by neuroID
}
