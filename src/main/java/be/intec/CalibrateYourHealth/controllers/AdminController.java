package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.model.Admin;
import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.services.AdminService;
import be.intec.CalibrateYourHealth.services.DoctorService;
import be.intec.CalibrateYourHealth.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final AdminService adminService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(AdminService adminService
            , PatientService patientService
            , DoctorService doctorService
            , BCryptPasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }

    //Register new Admin

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestParam ("username") String username, @RequestParam ("password") String password) {

        //Check that username and password are not null
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }
        //transfer details to the new admin object

        Admin newAdmin = new Admin();
        newAdmin.setUserName(username);
        newAdmin.setPassword(password); //password is encoded in the setter

        //log admin to the console for debugging
        System.out.println("new Admin before save: " + newAdmin);
        //Create new admin object
        adminService.saveAdmin(newAdmin);

        //log admin to the console for debugging
        System.out.println("new Admin after save: " + newAdmin);

        //Set admin details
        return ResponseEntity.ok("Admin registered successfully");
    }


    //Admin logs out
    @PostMapping("/logout")
    public String logout() {
        //Admin logs out
        //Redirect to login page
        return "redirect:/login";
    }

    //Admin resets patient password, only reset password if admin is logged in
    @PostMapping("/resetPatientPassword")
    public String resetPatientPassword() {
        //TODO: Reset patient password
        //Return success message??
        //Redirect to admin dashboard
        return "redirect:/adminDashboard";
    }

    //Admin resets doctor password, only reset password if admin is logged in
    @PostMapping("/resetDoctorPassword")
    public String resetDoctorPassword() {
        //TODO: Reset doctor password
        //Return success message??
        //Redirect to admin dashboard
        return "redirect:/adminDashboard";
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam ("username") String username, @RequestParam ("password") String password) {
        Optional<Admin> adminOpt = adminService.getAdminByUserName(username);

        //print admin to the console for debugging
        System.out.println("Admin: " + adminOpt);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {

                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    //get a list of all Admins
    @GetMapping("/alladmins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins().orElseThrow(() -> new RuntimeException("No admins found"));
        return ResponseEntity.ok(admins);
    }

    //Delete a specific admin
    @DeleteMapping("/deleteadmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable ("id") Long id) {
        adminService.deleteAdminById(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

//Get a list of all patients
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients().orElseThrow(() -> new RuntimeException("No patients found"));
        return ResponseEntity.ok(patients);
    }

    //Get a specific patient by id
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient.orElseThrow(() -> new RuntimeException("Patient not found")));
    }

    //Delete a specific patient by id
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable ("id") Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }
//Get a list of all doctors
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    //Get a specific doctor by id
    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor.orElseThrow(() -> new RuntimeException("Doctor not found")));
    }

    //Delete a specific doctor by id
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable ("id") Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }



}