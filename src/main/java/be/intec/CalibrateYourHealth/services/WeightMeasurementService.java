package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import java.util.List;
import java.util.Optional;


public interface WeightMeasurementService {

    List<WeightMeasurement> getAllWeightMeasurements();

    Optional<List<WeightMeasurement>> getPatientWeightMeasurements(Patient patient);

    //List<WeightMeasurement> getWeightMeasurementsByPatientID(long patientID);

    double getAverageWeightMeasurementByPatientForMonth(Patient patient);

    double getAverageWeightMeasurementByPatientForYear(Patient patient);

    Optional<WeightMeasurement> getWeightMeasurementById(Long id);

    WeightMeasurement saveWeightMeasurement(WeightMeasurement weightMeasurement);

    void deleteWeightMeasurementById(Long id);


}
