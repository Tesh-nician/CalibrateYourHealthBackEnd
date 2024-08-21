package be.intec.CalibrateYourHealth.repositories;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BloodPressureRepository extends JpaRepository<BloodPressureMeasurement, Long> {

    Optional<BloodPressureMeasurement> findBloodPressureByBloodPressureID(Long id); //for doctor and admin

    List<BloodPressureMeasurement> findAll(); //for admin and doctor

    List<BloodPressureMeasurement> findBloodPressureMeasurementByPatientId(Long patientID); // for patient and doctor

    BloodPressureMeasurement add(BloodPressureMeasurement bloodPressure); // for patient


    void deleteBloodPressureFromPatientByBloodPressureID(Long bloodPressureID);// for patient



}