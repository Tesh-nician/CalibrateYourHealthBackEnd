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
    public ResponseEntity<String> registerDoctor(@RequestBody Doctor doctor) {
        //transfer details to the new doctor object
        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName(doctor.getFirstName());
        newDoctor.setLastName(doctor.getLastName());
        newDoctor.setRizivNumber(doctor.getRizivNumber());
        newDoctor.setPassword(doctor.getPassword());

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
