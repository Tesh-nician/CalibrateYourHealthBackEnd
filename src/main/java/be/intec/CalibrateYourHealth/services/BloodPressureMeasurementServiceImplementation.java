package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodPressureMeasurementServiceImplementation implements BloodPressureMeasurementService {
    @Override
    public List<BloodPressureMeasurement> getAllBloodPressureMeasurements() {
        return null;
    }

    @Override
    public List<BloodPressureMeasurement> getAverageBloodPressureMeasurementsByPatientId(Long patientId) {
        return null;
    }

    @Override
    public List<BloodPressureMeasurement> getAverageBloodPressureMeasurementsByPatientIdForYear(Long patientId) {
        return null;
    }

    @Override
    public Optional<BloodPressureMeasurement> getBloodPressureMeasurementById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<BloodPressureMeasurement> getBloodPressureMeasurementsByPatientId(Long patientId) {
        return null;
    }

    @Override
    public BloodPressureMeasurement saveBloodPressureMeasurement(BloodPressureMeasurement bloodPressureMeasurement) {
        return null;
    }

    @Override
    public void deleteBloodPressureMeasurementById(Long id) {

    }
}
