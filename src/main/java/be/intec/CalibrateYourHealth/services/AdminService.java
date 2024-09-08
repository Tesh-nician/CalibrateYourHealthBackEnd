package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminService {

        Optional <List<Admin>> getAllAdmins();

        Optional<Admin> getAdminById(Long id);

        Optional<Admin> getAdminByUserName(String userName); //used for login

        Admin saveAdmin(Admin admin);

        Admin updateAdminPassword(Admin admin, String password);

        void deleteAdminById(Long id);
}
