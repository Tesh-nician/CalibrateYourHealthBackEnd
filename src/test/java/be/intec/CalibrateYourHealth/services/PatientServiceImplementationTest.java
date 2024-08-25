package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PatientServiceImplementationTest {

    @Mock
    private PatientRepository patientRepository;


    @InjectMocks
    private PatientServiceImplementation patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients_shouldReturnAnEmptyListWhenNoPatientsAreFound() {

// Arrange
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Optional<List<Patient>> result = patientService.getAllPatients();

        // Assert
        assertEquals(0, result.get().size());
    }

@Test
void getAllPatients_shouldReturnAListOfPatientsWhenPatientsAreFound() {
        // Arrange
        Patient patient1 = new Patient("firstname", "secondname",LocalDate.of(1988, 10,25));
        Patient patient2 = new Patient("firstname2", "secondname2",LocalDate.of(1989, 11,26));
        Patient patient3 = new Patient("firstname3", "secondname3",LocalDate.of(1990, 12,27));
        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2, patient3));
        // Act
        Optional<List<Patient>> result = patientService.getAllPatients();
        // Assert
        assertEquals(3, result.get().size());
        assertEquals(patient1, result.get().get(0));

}



    @Test
    void getPatientById_shouldReturnAPatientWhenPatientIsFound() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "firstname","secondname",LocalDate.of(1988, 10,25),"password");
        //add patient to the database
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        // Act
        Optional<Patient> result = patientService.getPatientById(patientId);
        // Assert
        assertEquals(patient, result.get());

    }



}
