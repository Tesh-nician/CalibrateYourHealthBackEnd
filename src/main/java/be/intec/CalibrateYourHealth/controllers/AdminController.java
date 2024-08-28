package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.model.Admin;
import be.intec.CalibrateYourHealth.model.Doctor;
import be.intec.CalibrateYourHealth.model.Patient;
import be.intec.CalibrateYourHealth.services.AdminService;
import be.intec.CalibrateYourHealth.services.DoctorService;
import be.intec.CalibrateYourHealth.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public AdminController(AdminService adminService, PatientService patientService, DoctorService doctorService) {
        this.adminService = adminService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    //Register new Admin
    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        //transfer details to the new admin object

        if (admin.getUserName() == null || admin.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }
        Admin newAdmin = new Admin();
        newAdmin.setUserName(admin.getUserName());
        newAdmin.setPassword(admin.getPassword());

        //Create new admin object
        adminService.saveAdmin(newAdmin);

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
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Admin> adminOpt = adminService.getAdminByUserName(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
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
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
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
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

}