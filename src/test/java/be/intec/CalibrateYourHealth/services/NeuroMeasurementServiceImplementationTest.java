package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.repositories.NeuroMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.intec.CalibrateYourHealth.model.NeuroMeasurement;

import be.intec.CalibrateYourHealth.services.NeuroMeasurementServiceImplementation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NeuroMeasurementServiceImplementationTest {


    @Mock
    NeuroMeasurementRepository neuroMeasurementRepository;
    @InjectMocks
    NeuroMeasurementServiceImplementation neuroMeasurementService;




    //should return all neuro measurements in the database
    @Test
    void getAllNeuroMeasurements_shouldReturnAllNeuroMeasurements() {


        // Arrange
        Long PatientId = 0L;
        Patient patient = new Patient(PatientId, "firstname", "secondname", LocalDate.of(1988, 10, 25),"Pasword1");
        NeuroMeasurement neuroMeasurement1 = new NeuroMeasurement(15 , LocalDate.of(2023, 8, 10), "neuro measurement 1", patient);
        NeuroMeasurement neuroMeasurement2 = new NeuroMeasurement(25 , LocalDate.of(2024, 7, 20), "neuro measurement 2", patient);
        NeuroMeasurement neuroMeasurement3 = new NeuroMeasurement(35 , LocalDate.of(2024, 1, 30), "neuro measurement 3", patient);
        when(neuroMeasurementRepository.findAll()).thenReturn(java.util.List.of(neuroMeasurement1, neuroMeasurement2, neuroMeasurement3));




        // Act
        List<NeuroMeasurement>   result = neuroMeasurementService.getAllNeuroMeasurements().get();

        // Assert

        assertThat(result.size()).isEqualTo(3);
    }

    //should return the average neuro measurement for the last month

    @Test
    void getAverageNeuroMeasurementByPatientForMonth_shouldReturnTheAverageNeuroMeasurementForTheLastMonth() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "firstname", "secondname", LocalDate.of(1988, 10, 25),"Pasword1");
        NeuroMeasurement neuroMeasurement1 = new NeuroMeasurement(15 , LocalDate.of(2021, 8, 10), "neuro measurement 1", patient);
        NeuroMeasurement neuroMeasurement2 = new NeuroMeasurement(25 , LocalDate.of(2021, 8, 20), "neuro measurement 2", patient);
        NeuroMeasurement neuroMeasurement3 = new NeuroMeasurement(35 , LocalDate.of(2021, 8, 30), "neuro measurement 3", patient);

        when(neuroMeasurementRepository.findAllByPatient(patient)).thenReturn(java.util.List.of(neuroMeasurement1, neuroMeasurement2, neuroMeasurement3));

        // Act
        double result = neuroMeasurementService.getAverageNeuroMeasurementByPatientForMonth(patient);

        // Assert
        assertThat(result).isEqualTo(25.0);
    }


}
