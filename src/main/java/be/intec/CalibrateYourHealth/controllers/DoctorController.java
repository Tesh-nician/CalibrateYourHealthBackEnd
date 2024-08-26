package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.services.DoctorService;
import be.intec.CalibrateYourHealth.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public DoctorController(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    //Register new Doctor
    @PostMapping("/registerDoctor")
    public String registerDoctor() {
        //Create new doctor object
        //Set doctor details
        return "registerDoctor";
    }

    //Save doctor to database
    @PostMapping("/saveDoctor")
    public String saveDoctor() {
        //Save doctor to database
        //Return success message??
        //Redirect to login page
        return "redirect:/login";
    }

    //Doctor logs out
    @PostMapping("/logout")
    public String logout() {
        //Doctor logs out
        //Redirect to login page
        return "redirect:/login";
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorByUserName(username);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (doctor.getPassword().equals(password)) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }
}
