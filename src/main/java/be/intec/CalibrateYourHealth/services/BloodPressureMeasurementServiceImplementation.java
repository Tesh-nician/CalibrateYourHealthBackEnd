package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BloodPressureMeasurementServiceImplementation implements BloodPressureMeasurementService {

    private final BloodPressureMeasurementService newBloodPressureMeasurementService;

    @Autowired
    public BloodPressureMeasurementServiceImplementation(BloodPressureMeasurementService newBloodPressureMeasurementService) {
        this.newBloodPressureMeasurementService = newBloodPressureMeasurementService;
    }




    //Returns a list of ALL blood pressure measurements, only for use by admin. Is this necessary?
    @Override
    public List<BloodPressureMeasurement> getAllBloodPressureMeasurements() {
       return newBloodPressureMeasurementService.getAllBloodPressureMeasurements();
    }



    //Method that gets the average blood pressure measurements of a patient for the last month
    @Override
    public BloodPressureMeasurement getAverageBloodPressureMeasurementsByPatientIdForMonth(Long patientId) {
        List<BloodPressureMeasurement> bloodPressureMeasurementsFromTheLastMonth= newBloodPressureMeasurementService
                .getBloodPressureMeasurementsByPatientId(patientId)
                        .stream().filter(bloodPressureMeasurement -> bloodPressureMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusMonths(1)))
                        .toList();
        //calculate average blood pressures from the list of average blood pressure measurements

        double averageSystolicPressure = bloodPressureMeasurementsFromTheLastMonth
                .stream().mapToDouble(BloodPressureMeasurement::getSystolicPressure)
                .average().orElse(0.0);
        double averageDiastolicPressure = bloodPressureMeasurementsFromTheLastMonth
                .stream().mapToDouble(BloodPressureMeasurement::getDiastolicPressure)
                .average().orElse(0.0);
        double averagePulse = bloodPressureMeasurementsFromTheLastMonth
                .stream().mapToDouble(BloodPressureMeasurement::getPulse)
                .average().orElse(0.0);
        return new BloodPressureMeasurement (averageSystolicPressure, averageDiastolicPressure, averagePulse);
    }


    //Method that gets the average blood pressure measurements of a patient for the last year
    @Override
    public List<BloodPressureMeasurement> getAverageBloodPressureMeasurementsByPatientIdForYear(Long patientId) {
        List<BloodPressureMeasurement> bloodPressureMeasurementsFromTheLastYear= newBloodPressureMeasurementService
                .getBloodPressureMeasurementsByPatientId(patientId)
                        .stream().filter(bloodPressureMeasurement -> bloodPressureMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusYears(1)))
                        .toList();
        //calculate average blood pressures from the list of average blood pressure measurements

        double averageSystolicPressure = bloodPressureMeasurementsFromTheLastYear
                .stream().mapToDouble(BloodPressureMeasurement::getSystolicPressure)
                .average().orElse(0.0);
        double averageDiastolicPressure = bloodPressureMeasurementsFromTheLastYear
                .stream().mapToDouble(BloodPressureMeasurement::getDiastolicPressure)
                .average().orElse(0.0);
        double averagePulse = bloodPressureMeasurementsFromTheLastYear
                .stream().mapToDouble(BloodPressureMeasurement::getPulse)
                .average().orElse(0.0);
        return List.of(new BloodPressureMeasurement (averageSystolicPressure, averageDiastolicPressure, averagePulse));

    }


    //get blood pressure by id of the blood pressure measurement
    @Override
    public Optional<BloodPressureMeasurement> getBloodPressureMeasurementById(Long id) {
        return newBloodPressureMeasurementService.getBloodPressureMeasurementById(id);
    }

    //get blood pressure measurements by patient id
    @Override
    public List<BloodPressureMeasurement> getBloodPressureMeasurementsByPatientId(Long patientId) {
        return newBloodPressureMeasurementService.getBloodPressureMeasurementsByPatientId(patientId);
    }

    @Override
    public BloodPressureMeasurement saveBloodPressureMeasurement(BloodPressureMeasurement bloodPressureMeasurement) {
        return newBloodPressureMeasurementService.saveBloodPressureMeasurement(bloodPressureMeasurement);

    }

    @Override
    public void deleteBloodPressureMeasurementById(Long id) {
        newBloodPressureMeasurementService.deleteBloodPressureMeasurementById(id);

    }




}
