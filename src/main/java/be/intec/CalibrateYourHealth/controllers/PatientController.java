package be.intec.CalibrateYourHealth.controllers;


import be.intec.CalibrateYourHealth.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final BCryptPasswordEncoder newPasswordEncoder;


    @Autowired
    public PatientController(BCryptPasswordEncoder newPasswordEncoder) {
        this.newPasswordEncoder = newPasswordEncoder;
    }





        // Method to create a new patient with a hashed password
        @PostMapping("/registerPatient")
        public String registerPatient(@RequestBody Patient patient) {
            String rawPassword = "your_new_password_here"; // This should be passed in as a parameter
            String hashedPassword = newPasswordEncoder.encode(rawPassword);
            patient.setPassword(hashedPassword);
            // Save patient to the database
            return "registerPatient";
        }






    @PostMapping("/savePatient")
    public String savePatient() {
        //Save patient to database
        //Return success message??
        //Redirect to login page

        return "redirect:/loginPatient";
    }


    //Patient logs in
    @PostMapping("/loginPatient")
    public String loginPatient() {
        //Patient logs in
        //Redirect to patient dashboard
        return "PatientDashboard";
    }

    //Patient dashboard that shows all blood pressure measurements
    @GetMapping("/patientDashboard")
    public String patientDashboard() {

        // post average blood pressure for last month
        // post average weight for last year
        // post average weightmeasurement for last month
        // post average weightomeasurements for last year
        // post average neuromeasurements for last month
        // post average neuromeasurements for last year
        // posts list of all blood pressure measurements
        // post list of weight measurements
        // post list of all neuromeasurements
        //posts list of all the patient's doctors
        return "patientDashboard";

    }

    //Patient adds new blood pressure measurement
    @GetMapping("/addBloodPressure")
    public String addBloodPressure() {
        //Create new blood pressure object
        //Set blood pressure details
        return "addBloodPressure";
    }

    @PostMapping("/saveBloodPressure")
    public String saveBloodPressure() {
        //Save blood pressure to database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }

    //Patient deletes blood pressure measurement
    @GetMapping("/deleteBloodPressure")
    public String deleteBloodPressure() {
        //Delete blood pressure from database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }

    //Patient updates blood pressure measurement
    @GetMapping("/updateBloodPressure")
    public String updateBloodPressure() {
        //Update blood pressure in database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }


    //Patient adds new weight measurement
    @GetMapping("/addWeight")
    public String addWeight() {
        //Create new weight object
        //Set weight details
        return "addWeight";
    }

    @PostMapping("/saveWeight")
    public String saveWeight() {
        //Save weight to database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }


    //Patient deletes weight measurement
    @GetMapping("/deleteWeight")
    public String deleteWeight() {
        //Delete weight from database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }

    //Patient updates weight measurement
    @GetMapping("/updateWeight")
    public String updateWeight() {
        //Update weight in database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }

    //Patient adds new neuro measurement
    @GetMapping("/addNeuro")
    public String addNeuro() {
        //Create new neuro object
        //Set neuro details
        return "addNeuro";
    }

    @PostMapping("/saveNeuro")
    public String saveNeuro() {
        //Save neuro to database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }

    //Patient deletes neuro measurement
    @GetMapping("/deleteNeuro")
    public String deleteNeuro() {
        //Delete neuro from database
        //Return success message??
        //Redirect to patient dashboard

        return "redirect:/patientDashboard";
    }

    //Patient logs out
    @PostMapping("/logoutPatient")
    public String logoutPatient() {
        //Patient logs out
        //Redirect to login page
        return "redirect:/login";
    }

    //TODO Patients adds new doctor
    //Patients goes to page addDoctorToPatient
    //Patient enters doctor family name and gets list of doctors whose family begins the given string


}
