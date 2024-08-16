package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import java.util.List;
import java.util.Optional;


public interface NeuroMeasurementService {

    List<NeuroMeasurement> getAllNeuroMeasurements();

    double getAverageNeuroMeasurementByPatientIdForMonth(Long patientId);

    double getAverageNeuroMeasurementByPatientIdForYear(Long patientId);

    Optional<NeuroMeasurement> getNeuroMeasurementById(Long id);

    List<NeuroMeasurement> getNeuroMeasurementsByPatientId(Long patientId);

    NeuroMeasurement saveNeuroMeasurement(NeuroMeasurement neuroMeasurement);

    void deleteNeuroMeasurementById(Long id);
}
