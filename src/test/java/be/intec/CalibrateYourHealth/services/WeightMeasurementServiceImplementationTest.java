package be.intec.CalibrateYourHealth.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import be.intec.CalibrateYourHealth.repositories.WeightMeasurementRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeightMeasurementServiceImplementationTest {

    @Mock
    private WeightMeasurementRepository weightMeasurementRepository;

    @InjectMocks
    private WeightMeasurementServiceImplementation weightMeasurementService;

    @Test
    void getAllWeightMeasurements_shouldReturnAListWithOneWeightMeasurementWhenOnlyOneExists() {
        // Arrange


        WeightMeasurement weightMeasurement = new WeightMeasurement(75.5, LocalDate.of(24,8,10), "weightmeasurement 1");

        when(weightMeasurementRepository.findAll()).thenReturn(Arrays.asList(weightMeasurement));

        // Act
        List<WeightMeasurement> result = weightMeasurementService.getAllWeightMeasurements();

        // Assert
        assertEquals(1, result.size());
        assertEquals(weightMeasurement, result.get(0));
    }

    @Test
    void getAverageWeightMeasurementByPatientIdForMonth_shouldReturnTheAverageWeightMeasurementForTheLastMonth() {
        // Arrange
        Long patientId = 1L;
        WeightMeasurement weightMeasurement1 = new WeightMeasurement(75.5, LocalDate.of(2021, 8, 10), "weightmeasurement 1");
        WeightMeasurement weightMeasurement2 = new WeightMeasurement(76.5, LocalDate.of(2021, 8, 20), "weightmeasurement 2");
        WeightMeasurement weightMeasurement3 = new WeightMeasurement(77.5, LocalDate.of(2021, 8, 25), "weightmeasurement 3");

        when(weightMeasurementRepository.findWeightMeasurementByPatientId(patientId)).thenReturn(Arrays.asList(weightMeasurement1, weightMeasurement2, weightMeasurement3));

        // Act
        double result = weightMeasurementService.getAverageWeightMeasurementByPatientIdForMonth(patientId);

        // Assert
        assertEquals(76.5, result);
    }

    @Test
    void getAverageWeightMeasurementByPatientIdForYear_shouldReturnTheAverageWeightMeasurementforthelastyear() {
        // Arrange
        Long patientId = 1L;
        WeightMeasurement weightMeasurement1 = new WeightMeasurement(75.5, LocalDate.of(2024, 8, 10), "weightmeasurement 1");
        WeightMeasurement weightMeasurement2 = new WeightMeasurement(76.5, LocalDate.of(2024, 8, 20), "weightmeasurement 2");
        WeightMeasurement weightMeasurement3 = new WeightMeasurement(77.5, LocalDate.of(2024, 8, 30), "weightmeasurement 3");
        WeightMeasurement weightMeasurement4 = new WeightMeasurement(78.5, LocalDate.of(2023, 8, 40), "weightmeasurement 4");

        when(weightMeasurementRepository.findWeightMeasurementByPatientId(patientId)).thenReturn(Arrays.asList(weightMeasurement1, weightMeasurement2, weightMeasurement3));

        // Act
        double result = weightMeasurementService.getAverageWeightMeasurementByPatientIdForYear(patientId);

        // Assert
        assertEquals(76.5, result);
    }


}