package be.intec.CalibrateYourHealth.controllers;

import be.intec.CalibrateYourHealth.services.AdminServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final BCryptPasswordEncoder newPasswordEncoder;
    private String password;
    private final AdminServiceImplementation adminServiceImplementation;

    @Autowired
    public AdminController(BCryptPasswordEncoder newPasswordEncoder, AdminServiceImplementation adminServiceImplementation) {
        this.newPasswordEncoder = newPasswordEncoder;
        this.adminServiceImplementation = adminServiceImplementation;
    }


    //Register new Admin

    @GetMapping("/registerAdmin")
    public String registerAdmin() {
        //Create new admin object
        //Set admin details
    return "registerAdmin";
    }

    @PostMapping("/saveAdmin")
    public String saveAdmin() {
        //Save admin to database
        //Return success message??
        //Redirect to login page

        return "redirect:/login";
    }

    //Admin logs in

    @PostMapping("/loginAdmin")
    public String loginAdmin() {
        //Admin logs in
        //Redirect to admin dashboard
        return "AdminDashboard";
    }

    //Admin dashboard that shows all patients and doctors

    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        //posts lists of all patients and doctors
        return "adminDashboard";
    }

    //Admin logs out
    @GetMapping("/logout")
    public String logout() {
        //Admin logs out
        //Redirect to login page
        return "redirect:/login";
    }

//Admin cannot update patient password, only reset password
    @PostMapping("/resetPatientPassword")
    public String resetPatientPassword() {
        // Admin clicks on patient to reset password
        //Admin resets patient password
        //Redirect to admin dashboard
        return "adminDashboard";
    }










    //Password encoding and matching

    public BCryptPasswordEncoder getNewPasswordEncoder() {
        return newPasswordEncoder;
    }

    public String encodePassword(String password) {
        return newPasswordEncoder.encode(password);
    }

    public boolean matchesPassword(String password, String encodedPassword) {
        return newPasswordEncoder.matches(password, encodedPassword);
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String getPassword() {
        return password;
    }












}

