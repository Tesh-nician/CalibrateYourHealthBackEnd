package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;

import java.util.List;
import java.util.Optional;

public interface BloodPressureMeasurementService {

    //get all bloodpressuremeasurements
    List<BloodPressureMeasurement> getAllBloodPressureMeasurements();

    //get average bloodpressuremeasurements for the last month by PatientID
    List<BloodPressureMeasurement> getAverageBloodPressureMeasurementsByPatientId(Long patientId);

    //get average bloodpressuremeasurements for the last year by PatientID
    List<BloodPressureMeasurement> getAverageBloodPressureMeasurementsByPatientIdForYear(Long patientId);



    Optional<BloodPressureMeasurement> getBloodPressureMeasurementById(Long id);

    List<BloodPressureMeasurement> getBloodPressureMeasurementsByPatientId(Long patientId);

    BloodPressureMeasurement saveBloodPressureMeasurement(BloodPressureMeasurement bloodPressureMeasurement);

    void deleteBloodPressureMeasurementById(Long id);
}
