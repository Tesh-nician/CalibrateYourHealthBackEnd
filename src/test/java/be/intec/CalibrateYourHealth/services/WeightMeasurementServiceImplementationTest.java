package be.intec.CalibrateYourHealth.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import be.intec.CalibrateYourHealth.repositories.WeightMeasurementRepository;
import be.intec.CalibrateYourHealth.model.Patient;

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


        WeightMeasurement weightMeasurement = new WeightMeasurement(75.5, LocalDate.of(2024,8,10), "weightmeasurement 1");

        when(weightMeasurementRepository.findAll()).thenReturn(Arrays.asList(weightMeasurement));

        // Act
        List<WeightMeasurement> result = weightMeasurementService.getAllWeightMeasurements();

        // Assert
        assertEquals(1, result.size());
        assertEquals(weightMeasurement, result.get(0));
    }

    @Test
    void getAverageWeightMeasurementByPatientForMonth_shouldReturnTheAverageWeightMeasurementForTheLastMonth() {
        // Arrange
        Long patientId = 0L;
        Patient patient = new Patient(patientId, "firstname", "secondname", LocalDate.of(1988, 10, 25),"Pasword1");
        WeightMeasurement weightMeasurement1 = new WeightMeasurement(75.5, LocalDate.of(2024, 8, 5), "weightmeasurement 1", patient);
        WeightMeasurement weightMeasurement2 = new WeightMeasurement(76.5, LocalDate.of(2024, 8, 10), "weightmeasurement 2", patient);
        WeightMeasurement weightMeasurement3 = new WeightMeasurement(77.5, LocalDate.of(2024, 8, 15), "weightmeasurement 3", patient);

        when(weightMeasurementRepository.findWeightMeasurementByPatient(patient)).thenReturn(Arrays.asList(weightMeasurement1, weightMeasurement2, weightMeasurement3));

        // Act
        double result = weightMeasurementService.getAverageWeightMeasurementByPatientForMonth(patient);

        // Assert
        assertEquals(76.5, result);
    }

    @Test
    void getAverageWeightMeasurementByPatientIdForYear_shouldReturnTheAverageWeightMeasurementforthelastyear() {
        // Arrange
        Long patientId = 0L;
        Patient patient = new Patient(patientId, "firstname", "secondname", LocalDate.of(1988, 10, 25),"Pasword1");
        WeightMeasurement weightMeasurement1 = new WeightMeasurement(75.5, LocalDate.of(2024, 2, 5), "weightmeasurement 1", patient);
        WeightMeasurement weightMeasurement2 = new WeightMeasurement(76.5, LocalDate.of(2024, 4, 10), "weightmeasurement 2", patient);
        WeightMeasurement weightMeasurement3 = new WeightMeasurement(77.5, LocalDate.of(2024, 6, 15), "weightmeasurement 3", patient);

        when(weightMeasurementRepository.findWeightMeasurementByPatient(patient)).thenReturn(Arrays.asList(weightMeasurement1, weightMeasurement2, weightMeasurement3));

        // Act
        double result = weightMeasurementService.getAverageWeightMeasurementByPatientForYear(patient);

        // Assert
        assertEquals(76.5, result);
    }


}