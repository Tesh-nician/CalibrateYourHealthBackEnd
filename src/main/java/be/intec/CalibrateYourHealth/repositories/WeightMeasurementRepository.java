package be.intec.CalibrateYourHealth.repositories;

import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightMeasurementRepository extends JpaRepository<WeightMeasurement, Long> {

    WeightMeasurement findWeightMeasurementByWeightID(Long id); //for doctor and admin

    List<WeightMeasurement> findAll(); //for admin and doctor

    List<WeightMeasurement> findWeightMeasurementByPatient_PatientID(Long patientId); // for patient and doctor

    WeightMeasurement add(WeightMeasurement weightMeasurement); // for patient

    void deleteWeightMeasurementByWeightID(Long id);// for patient, delete by weightID






}

