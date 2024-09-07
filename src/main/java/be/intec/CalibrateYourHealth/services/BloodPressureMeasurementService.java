package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;

import java.util.List;
import java.util.Optional;

public interface BloodPressureMeasurementService {

    //get all bloodpressuremeasurements
    List<BloodPressureMeasurement> getAllBloodPressureMeasurements();

    //get average bloodpressuremeasurement for the last month by PatientID
    //BloodPressureMeasurement getAverageBloodPressureMeasurementByPatientIdForMonth(Long patientId);


    //Method that gets the average blood pressure measurements of a patient for the last month
    BloodPressureMeasurement getAverageBloodPressureMeasurementsByPatientIdForMonth(Long patientId);

    //get average bloodpressuremeasurement for the last year by PatientID
    BloodPressureMeasurement getAverageBloodPressureMeasurementByPatientIdForYear(Long patientId);



    Optional<BloodPressureMeasurement> getBloodPressureMeasurementById(Long id);


    List<BloodPressureMeasurement> getBloodPressureMeasurementsByPatientId(Long patientId);

    BloodPressureMeasurement saveBloodPressureMeasurement(BloodPressureMeasurement bloodPressureMeasurement);

    void deleteBloodPressureMeasurementById(Long id);
}
