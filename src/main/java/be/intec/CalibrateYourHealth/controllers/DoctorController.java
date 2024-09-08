package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.services.DoctorService;
import be.intec.CalibrateYourHealth.services.PatientService;
import be.intec.CalibrateYourHealth.model.BloodPressureMeasurement;
import be.intec.CalibrateYourHealth.model.NeuroMeasurement;
import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.model.WeightMeasurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DoctorController(DoctorService doctorService, PatientService patientService, BCryptPasswordEncoder passwordEncoder) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
    }

    //Register new Doctor
    @PostMapping("/registerDoctor") // params used because request body gives error with passwordencoder???
    public ResponseEntity<String> registerDoctor(@RequestParam("firstname") String firstname
                                                , @RequestParam("lastname") String lastname
                                                , @RequestParam("rizivnumber") long rizivnumber
                                                , @RequestParam("password") String password) {
        //transfer details to the new doctor object
            //username = first 3 letters of firstname and first 3 letters of lastname
        String newUsername = firstname.substring(0, 3) + lastname.substring(0, 3);
        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName(firstname);
        newDoctor.setLastName(lastname);
        newDoctor.setRizivNumber(rizivnumber);
        newDoctor.setPassword(password); //is encoded in the model
        newDoctor.setUsername(newUsername);

        //Save doctor to database
        doctorService.saveDoctor(newDoctor);

        //Return success message??
        return ResponseEntity.ok("Doctor registered successfully");

    }



    //Doctor logs out
    @PostMapping("/logout")
    public String logout() {
        //Doctor logs out
        //Redirect to login page
        return "redirect:/login";
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorByUserName(username);

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (passwordEncoder.matches(password, doctor.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("doctorId", doctor.getDoctorID());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid password"));
            }
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "User not found"));
        }
    }


    @PutMapping("/{id}/update-password")
    public ResponseEntity<String> updateDoctorPassword(@PathVariable ("id") Long id, @RequestParam("password") String password) {
        Optional<Doctor> doctortOpt = doctorService.getDoctorById(id);
        if (doctortOpt.isPresent()) {
          Doctor doctort = doctortOpt.get();
            doctort.setPassword(password);
            doctorService.saveDoctor(doctort);
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(404).body("Doctor not found");
        }
    }


    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    //get list of all Patients
    @GetMapping("/patients")
    public ResponseEntity<String> getPatients() {
        return ResponseEntity.ok(patientService.getAllPatients().toString());
    }

    //TODO add methods to get information by patient



}
