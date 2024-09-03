package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.services.DoctorService;
import be.intec.CalibrateYourHealth.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
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
    @PostMapping("/registerDoctor")
    public ResponseEntity<String> registerDoctor(@RequestBody Doctor doctor) {
        //transfer details to the new doctor object
            //username = first 3 letters of firstname and first 3 letters of lastname
        String newUsername = doctor.getFirstName().substring(0, 3) + doctor.getLastName().substring(0, 3);
        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName(doctor.getFirstName());
        newDoctor.setLastName(doctor.getLastName());
        newDoctor.setRizivNumber(doctor.getRizivNumber());
        newDoctor.setPassword(doctor.getPassword());
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
    public ResponseEntity<String> login(@RequestParam ("username") String username, @RequestParam ("password") String password) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorByUserName(username);
        //log doctoropt to console for debugging
        //System.out.println("DoctorOpt: " + doctorOpt);


        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();

            System.out.println("Doctor exists"); //debugging

            //Print doctor to console for debugging
            System.out.println("Doctor: " + doctor);
            //Print doctor's password to console for debugging
            System.out.println("\n"+"Doctor's password: " + doctor.getPassword());
            //print received password to console for debugging
            System.out.println("\n"+password+ " Received password: ");
            //Print encoded received password to console for debugging
            System.out.println("\n"+passwordEncoder.encode(password)+" Encoded received password: " );
            //Print encoded doctor password to console for debugging
            System.out.println("\n"+doctor.getPassword()+" Encoded doctor password: " );





            if (passwordEncoder.matches(password, doctor.getPassword())) {
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
