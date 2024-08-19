package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.repositories.NeuroMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.intec.CalibrateYourHealth.model.NeuroMeasurement;

import be.intec.CalibrateYourHealth.services.NeuroMeasurementServiceImplementation;
import java.time.LocalDate;
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
        NeuroMeasurement neuroMeasurement1 = new NeuroMeasurement(15 , LocalDate.of(2023, 8, 10), "neuro measurement 1");
        NeuroMeasurement neuroMeasurement2 = new NeuroMeasurement(25 , LocalDate.of(2024, 7, 20), "neuro measurement 2");
        NeuroMeasurement neuroMeasurement3 = new NeuroMeasurement(35 , LocalDate.of(2024, 1, 30), "neuro measurement 3");
        when(neuroMeasurementRepository.findAll()).thenReturn(java.util.List.of(neuroMeasurement1, neuroMeasurement2, neuroMeasurement3));

        // Act
        var result = neuroMeasurementService.getAllNeuroMeasurements();

        // Assert
        assertThat(result).containsExactly(neuroMeasurement1, neuroMeasurement2, neuroMeasurement3);
    }

    //should return the average neuro measurement for the last month

    @Test
    void getAverageNeuroMeasurementByPatientIdForMonth_shouldReturnTheAverageNeuroMeasurementForTheLastMonth() {
        // Arrange
        Long patientId = 1L;
        NeuroMeasurement neuroMeasurement1 = new NeuroMeasurement(15 , LocalDate.of(2021, 8, 10), "neuro measurement 1");
        NeuroMeasurement neuroMeasurement2 = new NeuroMeasurement(25 , LocalDate.of(2021, 8, 20), "neuro measurement 2");
        NeuroMeasurement neuroMeasurement3 = new NeuroMeasurement(35 , LocalDate.of(2021, 8, 30), "neuro measurement 3");

        when(neuroMeasurementRepository.findNeuroMeasurementsByPatient_PatientID(patientId)).thenReturn(java.util.List.of(neuroMeasurement1, neuroMeasurement2, neuroMeasurement3));

        // Act
        double result = neuroMeasurementService.getAverageNeuroMeasurementByPatientIdForMonth(patientId);

        // Assert
        assertThat(result).isEqualTo(25.0);
    }


}
