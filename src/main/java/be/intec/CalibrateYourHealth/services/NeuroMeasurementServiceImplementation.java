package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import be.intec.CalibrateYourHealth.repositories.NeuroMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class NeuroMeasurementServiceImplementation implements NeuroMeasurementService {

    private final NeuroMeasurementRepository neuroMeasurementRepository;

    @Autowired
    public NeuroMeasurementServiceImplementation(NeuroMeasurementRepository neuroMeasurementRepository) {
        this.neuroMeasurementRepository = neuroMeasurementRepository;
    }



    @Override
    public Optional<List<NeuroMeasurement>> getAllNeuroMeasurements() {
        return Optional.of(neuroMeasurementRepository.findAll());
    }

    @Override
    public double getAverageNeuroMeasurementByPatientIdForMonth(Long patientId) {
        List<NeuroMeasurement> neuroMeasurementsFromTheLastMonth= neuroMeasurementRepository
                .findNeuroMeasurementsByPatientId(patientId)
                        .stream().filter(neuroMeasurement -> neuroMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusMonths(1)))
                        .toList();
        //calculate average neuro measurements from the list of average neuro measurements

        double averageNeuroMeasurement = neuroMeasurementsFromTheLastMonth
                .stream().mapToDouble(NeuroMeasurement::getNeuroMeasurement)
                .average().orElse(0.0);

        return averageNeuroMeasurement;
    }

    @Override
    public double getAverageNeuroMeasurementByPatientIdForYear(Long patientId) {
        List<NeuroMeasurement> neuroMeasurementsFromTheLastYear= neuroMeasurementRepository
                .findNeuroMeasurementsByPatientId(patientId)
                        .stream().filter(neuroMeasurement -> neuroMeasurement.getMeasurementDate()
                        .isAfter(LocalDate.now().minusYears(1)))
                        .toList();
        //calculate average neuro measurements from the list of average neuro measurements

        double averageNeuroMeasurement = neuroMeasurementsFromTheLastYear
                .stream().mapToDouble(NeuroMeasurement::getNeuroMeasurement)
                .average().orElse(0.0);

        return averageNeuroMeasurement;
    }

    @Override
    public Optional<NeuroMeasurement> getNeuroMeasurementById(Long id) {
        return neuroMeasurementRepository.findNeuroMeasurementByNeuroID(id);
    }

    @Override
    public List<NeuroMeasurement> getNeuroMeasurementsByPatientId(Long patientId) {
        return neuroMeasurementRepository.findNeuroMeasurementsByPatientId(patientId);
    }

    @Override
    public NeuroMeasurement saveNeuroMeasurement(NeuroMeasurement neuroMeasurement) {
        return neuroMeasurementRepository.save(neuroMeasurement);
    }

    @Override
    public void deleteNeuroMeasurementById(Long id) {
        neuroMeasurementRepository.deleteNeuroMeasurementByNeuroID(id);
    }

}
