package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminService {

        List<Admin> getAllAdmins();

        Optional<Admin> getAdminById(Long id);

        Admin saveAdmin(Admin admin);

        Admin updateAdminPassword(Admin admin, String password);

        void deleteAdminById(Long id);
}
