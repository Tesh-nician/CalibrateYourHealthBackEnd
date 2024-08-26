package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;

import java.util.List;
import java.util.Optional;


public interface NeuroMeasurementService {

    Optional <List<NeuroMeasurement>> getAllNeuroMeasurements();

    double getAverageNeuroMeasurementByPatientForMonth(Patient patient);

    double getAverageNeuroMeasurementByPatientForYear(Patient patient);


   Optional<NeuroMeasurement> getNeuroMeasurementById(Long id);

    Optional<List<NeuroMeasurement>> getNeuroMeasurementsByPatient(Patient patient);

    NeuroMeasurement saveNeuroMeasurement(NeuroMeasurement neuroMeasurement);

    void deleteNeuroMeasurementById(Long id);
}
