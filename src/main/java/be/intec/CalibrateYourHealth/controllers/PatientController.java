package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;
import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.model.WeightMeasurement;
import be.intec.CalibrateYourHealth.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final WeightMeasurementServiceImplementation weightMeasurementService;
    private final BloodPressureMeasurementServiceImplementation bloodPressureMeasurementService;
    private final NeuroMeasurementServiceImplementation neuroMeasurementService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PatientController(PatientServiceImplementation patientService
            , WeightMeasurementServiceImplementation weightMeasurementService
            , BloodPressureMeasurementServiceImplementation bloodPressureMeasurementService
            , NeuroMeasurementServiceImplementation neuroMeasurementService
            , BCryptPasswordEncoder passwordEncoder) {
        this.patientService = patientService;
        this.weightMeasurementService = weightMeasurementService;
        this.bloodPressureMeasurementService = bloodPressureMeasurementService;
        this.neuroMeasurementService = neuroMeasurementService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/registerPatient")
    public ResponseEntity<String> registerPatient(@RequestBody Patient patient) {
        //transfer details to the new patient object
        String newUserName = patient.getFirstName().substring(0, 3) + patient.getLastName().substring(0, 3);
        //Print new username to console
        System.out.println("New username: " + newUserName);
        Patient newPatient = new Patient();
        newPatient.setFirstName(patient.getFirstName());
        newPatient.setLastName(patient.getLastName());
        newPatient.setDateOfBirth(patient.getDateOfBirth());
        newPatient.setPassword(patient.getPassword());
        newPatient.setUsername(newUserName);

        patientService.savePatient(newPatient);

        return ResponseEntity.ok("Patient registered successfully");
    }




    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Patient> patientOpt = patientService.getPatientByUserName(username);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (patient.getPassword().equals(password)) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/logout")
    public String logout() {
        //Patient logs out
        //Clear session data, cookies, etc.
        //Set session timeout to 0 to disable logout functionality
        //Redirect to home page

        return "redirect:/login";
    }


    @GetMapping("/{id}/weight-measurements/average-month")
    public ResponseEntity<Double> getAverageWeightMeasurementByPatientForMonth(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            double averageWeight = weightMeasurementService.getAverageWeightMeasurementByPatientForMonth(patientOpt.get());
            return ResponseEntity.ok(averageWeight);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/weight-measurements/average-year")
    public ResponseEntity<Double> getAverageWeightMeasurementByPatientForYear(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            double averageWeight = weightMeasurementService.getAverageWeightMeasurementByPatientForYear(patientOpt.get());
            return ResponseEntity.ok(averageWeight);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/weight-measurements/{measurementId}")
    public ResponseEntity<WeightMeasurement> getWeightMeasurementById(@PathVariable Long measurementId) {
        Optional<WeightMeasurement> weightMeasurementOpt = weightMeasurementService.getWeightMeasurementById(measurementId);
        return ResponseEntity.ok(weightMeasurementOpt.orElseThrow(() -> new RuntimeException("Weight measurement not found")));
    }

    @PostMapping("/weight-measurements")
    public ResponseEntity<WeightMeasurement> saveWeightMeasurement(@RequestBody WeightMeasurement weightMeasurement) {
        WeightMeasurement savedMeasurement = weightMeasurementService.saveWeightMeasurement(weightMeasurement);
        return ResponseEntity.ok(savedMeasurement);
    }

    @DeleteMapping("/weight-measurements/{measurementId}")
    public ResponseEntity<String> deleteWeightMeasurementById(@PathVariable Long measurementId) {
        weightMeasurementService.deleteWeightMeasurementById(measurementId);
        return ResponseEntity.ok("Weight measurement deleted successfully");
    }


    // Blood Pressure Measurement Endpoints
    @GetMapping("/{id}/blood-pressure-measurements")
    public ResponseEntity<List<BloodPressureMeasurement>> getPatientBloodPressureMeasurements(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            List<BloodPressureMeasurement> measurements = bloodPressureMeasurementService.getBloodPressureMeasurementsByPatientId(id);
            return ResponseEntity.ok(measurements);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/blood-pressure-measurements/average-month")
    public ResponseEntity<BloodPressureMeasurement> getAverageBloodPressureMeasurementByPatientForMonth(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            BloodPressureMeasurement averageMeasurement = bloodPressureMeasurementService.getAverageBloodPressureMeasurementsByPatientIdForMonth(id);
            return ResponseEntity.ok(averageMeasurement);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/blood-pressure-measurements/average-year")
    public ResponseEntity<List<BloodPressureMeasurement>> getAverageBloodPressureMeasurementByPatientForYear(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            List<BloodPressureMeasurement> averageMeasurements = bloodPressureMeasurementService.getAverageBloodPressureMeasurementsByPatientIdForYear(id);
            return ResponseEntity.ok(averageMeasurements);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/blood-pressure-measurements/{measurementId}")
    public ResponseEntity<BloodPressureMeasurement> getBloodPressureMeasurementById(@PathVariable Long measurementId) {
        Optional<BloodPressureMeasurement> measurementOpt = bloodPressureMeasurementService.getBloodPressureMeasurementById(measurementId);
        return ResponseEntity.ok(measurementOpt.orElseThrow(() -> new RuntimeException("Blood pressure measurement not found")));
    }

    @PostMapping("/blood-pressure-measurements")
    public ResponseEntity<BloodPressureMeasurement> saveBloodPressureMeasurement(@RequestBody BloodPressureMeasurement measurement) {
        BloodPressureMeasurement savedMeasurement = bloodPressureMeasurementService.saveBloodPressureMeasurement(measurement);
        return ResponseEntity.ok(savedMeasurement);
    }

    @DeleteMapping("/blood-pressure-measurements/{measurementId}")
    public ResponseEntity<String> deleteBloodPressureMeasurementById(@PathVariable Long measurementId) {
        bloodPressureMeasurementService.deleteBloodPressureMeasurementById(measurementId);
        return ResponseEntity.ok("Blood pressure measurement deleted successfully");
    }

    // Neuro Measurement Endpoints
    @GetMapping("/{id}/neuro-measurements")
    public ResponseEntity<List<NeuroMeasurement>> getPatientNeuroMeasurements(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            List<NeuroMeasurement> measurements = neuroMeasurementService.getNeuroMeasurementsByPatient(patientOpt.get()).orElseThrow(() -> new RuntimeException("No neuro measurements found"));
            return ResponseEntity.ok(measurements);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/neuro-measurements/average-month")
    public ResponseEntity<Double> getAverageNeuroMeasurementByPatientForMonth(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            double averageMeasurement = neuroMeasurementService.getAverageNeuroMeasurementByPatientForMonth(patientOpt.get());
            return ResponseEntity.ok(averageMeasurement);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/neuro-measurements/average-year")
    public ResponseEntity<Double> getAverageNeuroMeasurementByPatientForYear(@PathVariable Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            double averageMeasurement = neuroMeasurementService.getAverageNeuroMeasurementByPatientForYear(patientOpt.get());
            return ResponseEntity.ok(averageMeasurement);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/neuro-measurements/{measurementId}")
    public ResponseEntity<NeuroMeasurement> getNeuroMeasurementById(@PathVariable Long measurementId) {
        Optional<NeuroMeasurement> measurementOpt = neuroMeasurementService.getNeuroMeasurementById(measurementId);
        return ResponseEntity.ok(measurementOpt.orElseThrow(() -> new RuntimeException("Neuro measurement not found")));
    }

    @PostMapping("/neuro-measurements")
    public ResponseEntity<NeuroMeasurement> saveNeuroMeasurement(@RequestBody NeuroMeasurement measurement) {
        NeuroMeasurement savedMeasurement = neuroMeasurementService.saveNeuroMeasurement(measurement);
        return ResponseEntity.ok(savedMeasurement);
    }

    @DeleteMapping("/neuro-measurements/{measurementId}")
    public ResponseEntity<String> deleteNeuroMeasurementById(@PathVariable Long measurementId) {
        neuroMeasurementService.deleteNeuroMeasurementById(measurementId);
        return ResponseEntity.ok("Neuro measurement deleted successfully");
    }




}
