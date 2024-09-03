package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import be.intec.CalibrateYourHealth.repositories.WeightMeasurementRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class WeightMeasurementServiceImplementation implements WeightMeasurementService{
    private final WeightMeasurementRepository newWeightMeasurementRepository;



    @Autowired
    public WeightMeasurementServiceImplementation(WeightMeasurementRepository newWeightMeasurementRepository) {
        this.newWeightMeasurementRepository = newWeightMeasurementRepository;
    }

    //make connection to the patient repository


    @Override
    public List<WeightMeasurement> getAllWeightMeasurements() {
        return newWeightMeasurementRepository.findAll();
    }



    @Override
    public Optional<List<WeightMeasurement>> getPatientWeightMeasurements(Patient patient) {

        //get all the weight measurements of the patient
        Optional<List<WeightMeasurement>> weightMeasurementsResult = Optional.of(newWeightMeasurementRepository.findWeightMeasurementByPatient(patient));
        return weightMeasurementsResult;
    }


    //Method that gets the average weight measurement of a patient for the last month
    @Override
    public double getAverageWeightMeasurementByPatientForMonth(Patient patient) {

        return  newWeightMeasurementRepository.findWeightMeasurementByPatient(patient)
                .stream().filter(weightMeasurement -> weightMeasurement.getMeasurementDate().isAfter(LocalDate.now().minusMonths(1)))
                .toList()
                .stream()
                .mapToDouble(WeightMeasurement::getWeight)
                .average().orElse(0.0);


    }

    //Method that gets the average weight measurement of a patient for the last year
    @Override
    public double getAverageWeightMeasurementByPatientForYear(Patient patient) {

        return newWeightMeasurementRepository.findWeightMeasurementByPatient(patient)
                .stream().filter(weightMeasurement -> weightMeasurement.getMeasurementDate().isAfter(LocalDate.now().minusYears(1)))
                .toList()
                .stream()
                .mapToDouble(WeightMeasurement::getWeight)
                .average().orElse(0.0);

    }

    //get weight by id of the weight measurement
    @Override
    public Optional<WeightMeasurement> getWeightMeasurementById(Long id) {
        return newWeightMeasurementRepository.findById(id);

    }



    @Override
    public WeightMeasurement saveWeightMeasurement(WeightMeasurement weightMeasurement) {
        return newWeightMeasurementRepository.save(weightMeasurement);
    }

    @Override
    @Transactional
    public void deleteWeightMeasurementById(Long id) {
        newWeightMeasurementRepository.deleteById(id);
    }


}
