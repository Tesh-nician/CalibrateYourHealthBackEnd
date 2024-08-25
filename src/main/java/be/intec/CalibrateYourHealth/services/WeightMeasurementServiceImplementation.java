package be.intec.CalibrateYourHealth.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import be.intec.CalibrateYourHealth.repositories.WeightMeasurementRepository;

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

    @Override
    public List<WeightMeasurement> getAllWeightMeasurements() {
        return newWeightMeasurementRepository.findAll();
    }



    //Method that gets the average weight measurement of a patient for the last month
    @Override
    public double getAverageWeightMeasurementByPatientIdForMonth(Long patientId) {
        List<WeightMeasurement> weightMeasurementsFromTheLastMonth= newWeightMeasurementRepository
                .findWeightMeasurementByPatientId(patientId)
                        .stream().filter(weightMeasurement -> weightMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusMonths(1)))
                        .toList();
        //calculate average weight from the list of average weight measurements

        double averageWeight = weightMeasurementsFromTheLastMonth
                .stream().mapToDouble(WeightMeasurement::getWeight)
                .average().orElse(0.0);

        return averageWeight;
    }

    //Method that gets the average weight measurement of a patient for the last year
    @Override
    public double getAverageWeightMeasurementByPatientIdForYear(Long patientId) {
        List<WeightMeasurement> weightMeasurementsFromTheLastYear= newWeightMeasurementRepository
                .findWeightMeasurementByPatientId(patientId)
                        .stream().filter(weightMeasurement -> weightMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusYears(1)))
                        .toList();
        //calculate average weight from the list of average weight measurements

        double averageWeight = weightMeasurementsFromTheLastYear
                .stream().mapToDouble(WeightMeasurement::getWeight)
                .average().orElse(0.0);

        return averageWeight;
    }

    //get weight by id of the weight measurement
    @Override
    public Optional<WeightMeasurement> getWeightMeasurementById(Long id) {
        return newWeightMeasurementRepository.findById(id);

    //get weight measurements by patient id
    }
   @Override
    public List<WeightMeasurement> getWeightMeasurementsByPatientId(Long patientId) {
        return newWeightMeasurementRepository.findWeightMeasurementByPatientId(patientId);
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
