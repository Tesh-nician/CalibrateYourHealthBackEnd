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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//import testing stuff
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceImplementationTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PatientServiceImplementation patientService;

    private Patient patient;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        patient = new Patient("firstname", "secondname", LocalDate.of(1988, 10,25));
        patient.setPassword("Plaintextpassword1");
    }



@Test
void getAllPatients_shouldReturnAListOfPatientsWhenPatientsAreFound() {
        // Arrange
        Patient patient1 = new Patient("firstname", "secondname",LocalDate.of(1988, 10,25));
        Patient patient2 = new Patient("firstname2", "secondname2",LocalDate.of(1989, 11,26));
        Patient patient3 = new Patient("firstname3", "secondname3",LocalDate.of(1990, 12,27));
        when(patientRepository.findAllPatients()).thenReturn(Optional.of(List.of(patient1, patient2, patient3)));
        // Act
        Optional<List<Patient>> result = patientService.getAllPatients();
        // Assert
        assertTrue(result.isPresent());
        assertEquals(3, result.get().size());
        assertEquals(patient1, result.get().get(0));


}



    @Test
    void getPatientByIDShouldAlsoProvideUserName() {
        // Arrange

        Patient patient = new Patient(0L,"firstname","secondname",LocalDate.of(1988, 10,25),"password");
        when(patientRepository.findPatientById(0L)).thenReturn(Optional.of(patient));
        //add patient to the database
        patientRepository.save(patient);
        Optional<Patient> result = patientService.getPatientById(0L);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(patient.getUsername(), result.get().getUsername());


    }



}
