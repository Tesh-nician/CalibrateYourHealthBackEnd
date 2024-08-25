package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Admin;
import be.intec.CalibrateYourHealth.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImplementation implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImplementation(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional <Admin> getAdminByUserName(String userName) {
        return adminRepository.findAdminByUserName(userName); //used for login
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdminPassword(Admin admin, String password) {
        admin.setPassword(password);
        return adminRepository.save(admin);


    }

    @Override
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }
}
