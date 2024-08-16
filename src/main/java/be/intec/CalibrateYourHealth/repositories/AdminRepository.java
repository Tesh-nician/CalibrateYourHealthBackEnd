package be.intec.CalibrateYourHealth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.intec.CalibrateYourHealth.model.Admin;

import java.util.List;
import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminById(Long id); //for doctor and admin

    List<Admin> findAll(); //for admin

    Admin add(Admin admin); // for admin

    void deleteAdminById(Long id);// for admin

    Admin updateAdminPassword(Admin admin, String newPassword); //for admin:if admin forgets password, reset password.

}
