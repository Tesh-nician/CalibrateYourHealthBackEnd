package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import java.util.List;
import java.util.Optional;


public interface WeightMeasurementService {

    List<WeightMeasurement> getAllWeightMeasurements();

    double getAverageWeightMeasurementByPatientIdForMonth(Long patientId);

    double getAverageWeightMeasurementByPatientIdForYear(Long patientId);

    Optional<WeightMeasurement> getWeightMeasurementById(Long id);

    List<WeightMeasurement> getWeightMeasurementsByPatientId(Long patientId);

    WeightMeasurement saveWeightMeasurement(WeightMeasurement weightMeasurement);

    void deleteWeightMeasurementById(Long id);


}
