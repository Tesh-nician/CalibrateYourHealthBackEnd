package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.repositories.DoctorRepository;
import be.intec.CalibrateYourHealth.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PatientserviceImplementationTest {

    @Mock
    private PatientRepository newpatientRepository;

    @Mock
    private DoctorRepository newDoctorRepository;

    @InjectMocks
    private PatientServiceImplementation patientService;


    @Test
void getAllPatients_shouldReturnAnEmptyListWhenNoPatientsAreFound() {
    // Arrange
    when(newpatientRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    PatientServiceImplementation patientService = null;


    Optional<List<Patient>> result = patientService.getAllPatients();

    // Assert
    assertEquals(0, result.get().size());
}

@Test
void getAllPatients_shouldReturnAListOfPatientsWhenPatientsAreFound() {
    // Arrange
    Patient patient1 = new Patient("Margriet", "Hermans", LocalDate.of(1985, 12, 1));
    Patient patient2 = new Patient("Johannes", "Bach", LocalDate.of(1958, 5, 15));
    //when(newpatientRepository.findAll()).thenReturn(List.of(patient1, patient2));
    PatientRepository newPatientRepository = null;
    DoctorRepository newDoctorRepository = null;
    newPatientRepository.save(patient1);
    newPatientRepository.save(patient2);
    PatientServiceImplementation patientService = new PatientServiceImplementation(newDoctorRepository, newPatientRepository);
    // Act


    Optional<List<Patient>> result = patientService.getAllPatients();

    // Assert
    assertEquals(2, result.get().size());
    assertEquals(patient1, result.get().get(0));
    assertEquals(patient2, result.get().get(1));
}

}
