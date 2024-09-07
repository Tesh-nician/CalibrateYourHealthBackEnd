package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;
import be.intec.CalibrateYourHealth.repositories.BloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BloodPressureMeasurementServiceImplementation implements BloodPressureMeasurementService {

    private final BloodPressureRepository bloodPressureRepository; //The error was implementing the service instead of the repository




    @Autowired
    public BloodPressureMeasurementServiceImplementation(BloodPressureRepository bloodPressureRepository) {
        this.bloodPressureRepository = bloodPressureRepository;

    }






    //Returns a list of ALL blood pressure measurements, only for use by admin. Is this necessary?
    @Override
    public List<BloodPressureMeasurement> getAllBloodPressureMeasurements() {
       return bloodPressureRepository.findAll();
    }

    @Override
    public BloodPressureMeasurement getAverageBloodPressureMeasurementsByPatientIdForMonth(Long patientId) {
        List<BloodPressureMeasurement> bloodPressureMeasurementsFromTheLastMonth = bloodPressureRepository
                .findBloodPressureMeasurementByPatientId(patientId)
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


    //Method that gets the average blood pressure measurements of a patient for the last month



    //Method that gets the average blood pressure measurements of a patient for the last year
    @Override
    public BloodPressureMeasurement getAverageBloodPressureMeasurementByPatientIdForYear(Long patientId) {
        //List of blood pressure measurements from the last year
        List<BloodPressureMeasurement> annualBloodPressureMeasurements= bloodPressureRepository
                        .findBloodPressureMeasurementByPatientId(patientId)
                        .stream().filter(bloodPressureMeasurement -> bloodPressureMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusYears(1)))
                        .toList();


        //calculate average blood pressures from the list of average blood pressure measurements
        double averageSystolicPressure = annualBloodPressureMeasurements
                .stream().mapToDouble(BloodPressureMeasurement::getSystolicPressure)
                .average().orElse(0.0);
        double averageDiastolicPressure = annualBloodPressureMeasurements
                .stream().mapToDouble(BloodPressureMeasurement::getDiastolicPressure)
                .average().orElse(0.0);
        double averagePulse = annualBloodPressureMeasurements
                .stream().mapToDouble(BloodPressureMeasurement::getPulse)
                .average().orElse(0.0);
        return new BloodPressureMeasurement (averageSystolicPressure, averageDiastolicPressure, averagePulse);

    }


    //get blood pressure by id of the blood pressure measurement
    @Override
    public Optional<BloodPressureMeasurement> getBloodPressureMeasurementById(Long id) {
        return bloodPressureRepository.findBloodPressureByBloodPressureID(id);
    }

    //get blood pressure measurements by patient id
    @Override
    public List<BloodPressureMeasurement> getBloodPressureMeasurementsByPatientId(Long patientId) {

        List<BloodPressureMeasurement> bloodPressureMeasurements = bloodPressureRepository.findBloodPressureMeasurementByPatientId(patientId);

        //sort the blood pressure measurements by date
        bloodPressureMeasurements.sort((o1, o2) -> o2.getMeasurementDate().compareTo(o1.getMeasurementDate()));

        return bloodPressureMeasurements;
    }

    @Override
    public BloodPressureMeasurement saveBloodPressureMeasurement(BloodPressureMeasurement bloodPressureMeasurement) {
        return bloodPressureRepository.save(bloodPressureMeasurement);

    }

    @Override
    @Transactional
    public void deleteBloodPressureMeasurementById(Long id) {
        bloodPressureRepository.deleteBloodPressureFromPatientByBloodPressureID(id);

    }




}
