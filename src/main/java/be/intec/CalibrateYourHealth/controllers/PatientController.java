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


import java.util.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:4200")
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


    @PostMapping("/registerPatient") //params used because request body gives error with passwordenconder???
    public ResponseEntity<String> registerPatient(@RequestParam("firstname") String firstname
                                    , @RequestParam("lastname") String lastname
                                    , @RequestParam("dateofbirth") String dateofbirth
                                    , @RequestParam("password") String password) {
        //transfer details to the new patient object
        String newUserName = firstname.substring(0, 3) + lastname.substring(0, 3);
        //Print new username to console
        System.out.println("New username: " + newUserName);
        Patient newPatient = new Patient();
        newPatient.setFirstName(firstname);
        newPatient.setLastName(lastname);
        newPatient.setDateOfBirth(LocalDate.parse(dateofbirth)); //TODO: check that the date is correctly parsed
        newPatient.setPassword(password); //is encoded in the model
        newPatient.setUsername(newUserName);

        patientService.savePatient(newPatient);

        return ResponseEntity.ok("Patient registered successfully");
    }




    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Patient> patientOpt = patientService.getPatientByUserName(username);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (passwordEncoder.matches(password, patient.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("patientId", patient.getId());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid password"));
            }
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "User not found"));
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

    //get all weight measurements of a specific patient by patient id //
    @GetMapping("/{id}/weight-measurements")
    public ResponseEntity<List<WeightMeasurement>> getPatientWeightMeasurementsByPatientId(@PathVariable ("id")Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        // check if the patient exists and if the list of weight measurements is present
        if ((patientOpt.isPresent())
                && (weightMeasurementService.getPatientWeightMeasurementsByPatientID(id).isPresent())) {

                    List<WeightMeasurement> measurements = weightMeasurementService.getPatientWeightMeasurementsByPatientID(id).get();
                    return ResponseEntity.ok(measurements);
            } else {

            return ResponseEntity.status(404).body(null);
        }
    }


    //TEMPORARY, MOVE TO PLACEHOLDER LATER


    //get the average neuro measurement of a specific patient for the current month
    @GetMapping("/{id}/neuro-measurements/average-month")
    public ResponseEntity<Double> getAverageNeuroMeasurementByPatientForMonth(@PathVariable ("id") Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            double averageMeasurement = neuroMeasurementService.getAverageNeuroMeasurementByPatientForMonth(patientOpt.get());
            return ResponseEntity.ok(averageMeasurement);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    //get the average weight measurement of a specific patient for the current month
    @GetMapping("/{id}/weight-measurements/average-month")
    public ResponseEntity<Double> getAverageWeightMeasurementByPatientForMonth(@PathVariable ("id") Long id) {


        Optional<Patient> patientOpt = patientService.getPatientById(id);

        //System.out.println("Patient: " + patientOpt);

        if (patientOpt.isPresent()) {
            double averageWeight = weightMeasurementService.getAverageWeightMeasurementByPatientForMonth(patientOpt.get());
            //System.out.println("Average weight: " + averageWeight);
            return ResponseEntity.ok(averageWeight);

        } else {
            return ResponseEntity.status(404).body(null);
        }

    }
    //get the average weight measurement of a specific patient for the current year
    @GetMapping("/{id}/weight-measurements/average-year")
    public ResponseEntity<Double> getAverageWeightMeasurementByPatientForYear(@PathVariable ("id")  Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);

        //System.out.println("Patient: " + patientOpt);

        if (patientOpt.isPresent()) {
            double averageWeight = weightMeasurementService.getAverageWeightMeasurementByPatientForYear(patientOpt.get());
            System.out.println("Average weight: " + averageWeight);
            return ResponseEntity.ok(averageWeight);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    //get a specific weight measurement by measurement id
    @GetMapping("/weight-measurements/{measurementId}")
    public ResponseEntity<WeightMeasurement> getWeightMeasurementById(@PathVariable ("measurementId")  Long measurementId) {
        Optional<WeightMeasurement> weightMeasurementOpt = weightMeasurementService.getWeightMeasurementById(measurementId);
        return ResponseEntity.ok(weightMeasurementOpt.orElseThrow(() -> new RuntimeException("Weight measurement not found")));
    }

    //save a new weight measurement
    @PostMapping("/weight-measurements")
    public ResponseEntity<String> saveWeightMeasurement(@RequestBody WeightMeasurement weightMeasurement) {
        if (weightMeasurement.getPatient() == null || weightMeasurement.getPatient().getId() == null) {
            return ResponseEntity.status(400).body("Bad request: patient or patient ID is null");
        }

        Long patientId = weightMeasurement.getPatient().getId();
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isPresent()) {
            weightMeasurement.setPatient(patientOpt.get());
            weightMeasurementService.saveWeightMeasurement(weightMeasurement);
            return ResponseEntity.ok("Weight measurement saved successfully");
        } else {
            return ResponseEntity.status(404).body("Not found: patient not found");
        }
    }

    //delete a specific weight measurement by measurement id
    @DeleteMapping("/weight-measurements/{id}")
    public ResponseEntity<String> deleteWeightMeasurementById(@PathVariable ("id") Long id) {
        weightMeasurementService.deleteWeightMeasurementById(id);
        return ResponseEntity.ok("Weight measurement deleted successfully");
    }


    // Blood Pressure Measurement Endpoints

    //get all blood pressure measurements of a patient
    @GetMapping("/{id}/blood-pressure-measurements")
    public ResponseEntity<List<BloodPressureMeasurement>> getPatientBloodPressureMeasurements(@PathVariable ("id") Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            List<BloodPressureMeasurement> measurements = bloodPressureMeasurementService.getBloodPressureMeasurementsByPatientId(id);
            return ResponseEntity.ok(measurements);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }



    //get the average blood pressure measurement of a specific patient for the current month
    @GetMapping("/{id}/blood-pressure-measurements/average-month")
    public ResponseEntity<BloodPressureMeasurement> getAverageBloodPressureMeasurementByPatientForMonth(@PathVariable ("id")  Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            BloodPressureMeasurement averageMeasurement = bloodPressureMeasurementService.getAverageBloodPressureMeasurementsByPatientIdForMonth(id);
            return ResponseEntity.ok(averageMeasurement);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    //get the average blood pressure measurement of a specific patient for the current year
    @GetMapping("/{id}/blood-pressure-measurements/average-year")
    public ResponseEntity<List<BloodPressureMeasurement>> getAverageBloodPressureMeasurementByPatientForYear(@PathVariable ("id") Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            List<BloodPressureMeasurement> averageMeasurements = bloodPressureMeasurementService.getAverageBloodPressureMeasurementsByPatientIdForYear(id);
            return ResponseEntity.ok(averageMeasurements);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    //get a specific blood pressure measurement by measurement id
    @GetMapping("/blood-pressure-measurements/{measurementId}")
    public ResponseEntity<BloodPressureMeasurement> getBloodPressureMeasurementById(@PathVariable ("measurementId") Long measurementId) {
        Optional<BloodPressureMeasurement> measurementOpt = bloodPressureMeasurementService.getBloodPressureMeasurementById(measurementId);
        return ResponseEntity.ok(measurementOpt.orElseThrow(() -> new RuntimeException("Blood pressure measurement not found")));
    }




    //save a new blood pressure measurement
    @PostMapping("/blood-pressure-measurements")
    public ResponseEntity<String> saveBloodPressureMeasurement(@RequestBody BloodPressureMeasurement bloodPressureMeasurement) {

        if (bloodPressureMeasurement.getPatient() == null || bloodPressureMeasurement.getPatient().getId() == null) {
            return ResponseEntity.status(400).body("Bad request: patient or patient ID is null");
        }

        Long patientId = bloodPressureMeasurement.getPatient().getId();
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isPresent()) {
            bloodPressureMeasurement.setPatient(patientOpt.get());
            bloodPressureMeasurementService.saveBloodPressureMeasurement(bloodPressureMeasurement);
            return ResponseEntity.ok("Blood pressure measurement saved successfully");

        } else {
            return ResponseEntity.status(404).body("Not found: patient not found");
        }

    }



    //delete a specific blood pressure measurement by measurement id
    @DeleteMapping("/blood-pressure-measurements/{measurementId}")
    public ResponseEntity<String> deleteBloodPressureMeasurementById(@PathVariable ("measurementId")Long measurementId) {
        bloodPressureMeasurementService.deleteBloodPressureMeasurementById(measurementId);
        return ResponseEntity.ok("Blood pressure measurement deleted successfully");
    }

    // Neuro Measurement Endpoints

    //get all neuro measurements of a specific patient by patient id
    @GetMapping("/{id}/neuro-measurements")
    public ResponseEntity<List<NeuroMeasurement>> getPatientNeuroMeasurements(@PathVariable ("id") Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            List<NeuroMeasurement> measurements = neuroMeasurementService.getNeuroMeasurementsByPatientID(id).orElseThrow(() -> new RuntimeException("No neuro measurements found"));
            return ResponseEntity.ok(measurements);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    //PLACEHOLDER NEURO MEASUREMENT AVERAGE PER MONTH


    //get the average neuro measurement of a specific patient for the current year
    @GetMapping("/{id}/neuro-measurements/average-year")
    public ResponseEntity<Double> getAverageNeuroMeasurementByPatientForYear(@PathVariable ("id") Long id) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            double averageMeasurement = neuroMeasurementService.getAverageNeuroMeasurementByPatientForYear(patientOpt.get());
            return ResponseEntity.ok(averageMeasurement);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    //get a specific neuro measurement by measurement id
    @GetMapping("/neuro-measurements/{measurementId}")
    public ResponseEntity<NeuroMeasurement> getNeuroMeasurementById(@PathVariable ("measurementId") Long measurementId) {
        Optional<NeuroMeasurement> measurementOpt = neuroMeasurementService.getNeuroMeasurementById(measurementId);
        return ResponseEntity.ok(measurementOpt.orElseThrow(() -> new RuntimeException("Neuro measurement not found")));
    }

    //save a new neuro measurement
    @PostMapping("/neuro-measurements")
    public ResponseEntity<String> saveNeuroMeasurement(@RequestBody NeuroMeasurement neuroMeasurement) {

        if (neuroMeasurement.getPatient() == null || neuroMeasurement.getPatient().getId() == null) {
            return ResponseEntity.status(400).body("Bad request: patient or patient ID is null");
        }

        Long patientId = neuroMeasurement.getPatient().getId();
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isPresent()) {
            neuroMeasurement.setPatient(patientOpt.get());
            neuroMeasurementService.saveNeuroMeasurement(neuroMeasurement);
            return ResponseEntity.ok("Neuro measurement saved successfully");
        } else {
            return ResponseEntity.status(404).body("Not found: patient not found");
        }

    }




    //delete a specific neuro measurement by measurement id
    @DeleteMapping("/neuro-measurements/{measurementId}")
    public ResponseEntity<String> deleteNeuroMeasurementById(@PathVariable ("measurementId") Long measurementId) {
        neuroMeasurementService.deleteNeuroMeasurementById(measurementId);
        return ResponseEntity.ok("Neuro measurement deleted successfully");
    }

}
