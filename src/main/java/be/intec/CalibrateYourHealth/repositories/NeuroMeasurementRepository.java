package be.intec.CalibrateYourHealth.repositories;


import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NeuroMeasurementRepository extends JpaRepository<NeuroMeasurement, Long> {

    Optional<NeuroMeasurement> findNeuroMeasurementByNeuroID(Long id); //for doctor and patient

    //find Optional list of all neuro measurements
    Optional<List<NeuroMeasurement>> findAllNeuroMeasurements(); //for doctor and patient



    List<NeuroMeasurement> findNeuroMeasurementsByPatientId(Long patientId); // for patient and doctor

    void deleteNeuroMeasurementByNeuroID(Long id);// for patient, delete by neuroID
}
