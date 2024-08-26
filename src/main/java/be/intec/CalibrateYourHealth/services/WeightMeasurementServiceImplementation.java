package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import be.intec.CalibrateYourHealth.repositories.WeightMeasurementRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;


@Service
public class WeightMeasurementServiceImplementation implements WeightMeasurementService{
    private final WeightMeasurementRepository newWeightMeasurementRepository;








    @Autowired
    public WeightMeasurementServiceImplementation(WeightMeasurementRepository newWeightMeasurementRepository) {
        this.newWeightMeasurementRepository = newWeightMeasurementRepository;
    }

    @Override
    public List<WeightMeasurement> getAllWeightMeasurements() {
        return newWeightMeasurementRepository.findAll();
    }

    @Override
    Optional <List<WeightMeasurement>> getWeightMeasurementsByPatientID(Long patientId) {
        //TODO: implement this method, as the old repository method with regex or MatchByPatientID no longer works
        //first identify the patient by the patientId using the patient service
        Patient patient = newPatientService.findPatientById(patientId).orElseThrow();

        //then get the weight measurements of the patient
        return Optional.of(newWeightMeasurementRepository.findWeightMeasurementByPatient(patient));


        //then get the weight measurements of the patient
        return newWeightMeasurementRepository.findWeightMeasurementByPatient(patient);

    }


    //Method that gets the average weight measurement of a patient for the last month
    @Override
    public double getAverageWeightMeasurementByPatientIdForMonth(Long patientId) {

        Double averageWeightMeasurementsForMonth = newWeightMeasurementRepository.getByPatientIdMatchesRegex(patientId)
        .stream().filter(weightMeasurement -> weightMeasurement.getMeasurementDate().isAfter(LocalDate.now().minusMonths(1)))
                .toList()
                .stream()
                .mapToDouble(WeightMeasurement::getWeight)
                .average().orElse(0.0);

        return averageWeightMeasurementsForMonth;


    }

    //Method that gets the average weight measurement of a patient for the last year
    @Override
    public double getAverageWeightMeasurementByPatientIdForYear(Long patientId) {
        Double averageWeightMeasurementsForYear = newWeightMeasurementRepository.getByPatientIdMatchesRegex(patientId)
                .stream().filter(weightMeasurement -> weightMeasurement.getMeasurementDate().isAfter(LocalDate.now().minusYears(1)))
                .toList()
                .stream()
                .mapToDouble(WeightMeasurement::getWeight)
                .average().orElse(0.0);

        return averageWeightMeasurementsForYear;
    }

    //get weight by id of the weight measurement
    @Override
    public Optional<WeightMeasurement> getWeightMeasurementById(Long id) {
        return newWeightMeasurementRepository.findById(id);

    //get weight measurements by patient id
    }




    @Override
    public WeightMeasurement saveWeightMeasurement(WeightMeasurement weightMeasurement) {
        return newWeightMeasurementRepository.save(weightMeasurement);
    }

    @Override
    public void deleteWeightMeasurementById(Long id) {
        newWeightMeasurementRepository.deleteById(id);
    }



}
