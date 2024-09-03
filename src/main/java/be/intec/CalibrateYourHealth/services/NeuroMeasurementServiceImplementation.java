package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.repositories.NeuroMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<NeuroMeasurement> getNeuroMeasurementById(Long id) {
        return neuroMeasurementRepository.findById(id);
    }

    @Override
    public Optional<List<NeuroMeasurement>> getNeuroMeasurementsByPatient(Patient patient) {
        return Optional.of(neuroMeasurementRepository.findAllByPatient(patient));
    }


    @Override
    public double getAverageNeuroMeasurementByPatientForMonth(Patient patient) {
        double neuroMeasurementsFromTheLastMonth = neuroMeasurementRepository
                .findAllByPatient(patient).stream()
                .filter(neuroMeasurement -> neuroMeasurement.getMeasurementDate()
                .isAfter(LocalDate.now().minusMonths(1))).
                mapToDouble(NeuroMeasurement::getNeuroMeasurement).
                average().orElse(0.0);

        return neuroMeasurementsFromTheLastMonth;
    }


    @Override
    public double getAverageNeuroMeasurementByPatientForYear(Patient patient) {
        double neuroMeasurementsFromTheLastYear = neuroMeasurementRepository
                .findAllByPatient(patient).stream()
                .filter(neuroMeasurement -> neuroMeasurement.getMeasurementDate()
                .isAfter(LocalDate.now().minusYears(1))).
                mapToDouble(NeuroMeasurement::getNeuroMeasurement).
                average().orElse(0.0);

        return neuroMeasurementsFromTheLastYear;
    }




    @Override
    public NeuroMeasurement saveNeuroMeasurement(NeuroMeasurement neuroMeasurement) {
        return neuroMeasurementRepository.save(neuroMeasurement);
    }

    @Override
    @Transactional
    public void deleteNeuroMeasurementById(Long id) {
        neuroMeasurementRepository.deleteNeuroMeasurementByNeuroID(id);
    }

}
