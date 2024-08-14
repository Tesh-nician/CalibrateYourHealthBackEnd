package be.intec.CalibrateYourHealth.repositories;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightMeasurementRepository extends JpaRepository<WeightMeasurement, Long> {

    Optional<WeightMeasurement> findWeightMeasurementByWeightID(Long id); //for doctor and admin

    List<WeightMeasurement> findAll(); //for admin and doctor

    List<WeightMeasurement> findWeightMeasurementByPatientId(Long patientId); // for patient and doctor

    WeightMeasurement save(WeightMeasurement weightMeasurement); // for patient

    void deleteWeightMeasurementByWeightID(Long id);// for patient, delete by weightID






}

