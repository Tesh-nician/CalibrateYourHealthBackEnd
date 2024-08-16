package be.intec.CalibrateYourHealth.repositories;


import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NeuroMeasurementRepository extends JpaRepository<NeuroMeasurement, Long> {

    Optional<NeuroMeasurement> findNeuroMeasurementByNeuroID(Long id); //for doctor and patient

    List<NeuroMeasurement> findAll(); //for admin

    List<NeuroMeasurement> findNeuroMeasurementsByPatient_PatientID(Long patientId); // for patient and doctor

    NeuroMeasurement add(NeuroMeasurement neuroMeasurement); // for patient

    void deleteNeuroMeasurementByNeuroID(Long id);// for patient, delete by neuroID
}
