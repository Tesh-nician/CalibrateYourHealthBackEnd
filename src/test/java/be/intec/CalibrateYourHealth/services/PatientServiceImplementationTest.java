package be.intec.CalibrateYourHealth.services;

//import PatientRepository
import be.intec.CalibrateYourHealth.repositories.PatientRepository;
//import Patient
import be.intec.CalibrateYourHealth.model.Patient;
//import MockitoAnnotations
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import utilities
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import testing stuff
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PatientServiceImplementationTest {

    @Mock
    public PatientRepository patientRepository;

    @InjectMocks
    public PatientServiceImplementation patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients_shouldReturnAnEmptyListWhenNoPatientsAreFound() {

    // Arrange
        //ensure that patientRepository.findAll returns an empty list
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
        //get Id of the second patient
        assertEquals(patient2.getId(), result.get().get(2).getId());

}



    @Test
    void getPatientByIDShouldAlsoProvideUserName() {
        // Arrange

        Patient patient = new Patient("firstname","secondname",LocalDate.of(1988, 10,25));
        //add patient to the database
        patientRepository.save(patient);
        String result = patientService.getPatientByUserName(patient.getUsername()).get().getUsername();
        // Assert
        assertEquals(patient.getUsername(), result);


    }



}
