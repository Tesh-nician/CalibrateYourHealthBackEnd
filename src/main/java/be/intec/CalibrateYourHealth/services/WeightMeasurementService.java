package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import java.util.List;
import java.util.Optional;


public interface WeightMeasurementService {

    List<WeightMeasurement> getAllWeightMeasurements();



    Optional<List<WeightMeasurement>> getPatientWeightMeasurementsByPatientID(Long patientID);


    double getAverageWeightMeasurementByPatientForMonth(Patient patient);

    double getAverageWeightMeasurementsByPatientIdForMonth(Long patientId);

    double getAverageWeightMeasurementByPatientForYear(Patient patient);


    double getAverageWeightMeasurementByPatientIdForYear(Long patientId);


    Optional<WeightMeasurement> getWeightMeasurementById(Long id);



    WeightMeasurement saveWeightMeasurement(WeightMeasurement weightMeasurement);

    void deleteWeightMeasurementById(Long id);


}
