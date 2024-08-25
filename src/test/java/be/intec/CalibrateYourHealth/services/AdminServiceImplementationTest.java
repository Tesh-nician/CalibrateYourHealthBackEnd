package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Admin;
import be.intec.CalibrateYourHealth.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceImplementationTest {


@Mock
AdminRepository adminRepository;

@InjectMocks
AdminServiceImplementation adminService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void saveAdmin() {
        //Arrange
        Admin admin = new Admin("username", "MyPassWord1!@#");




        //then
        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    void updateAdminPasswordReturnsNewPassword() {

        //Arrange

        String oldPassword = "MyPassWord1";
        Admin admin = new Admin("username1", oldPassword);
        String newPassword = "MyPassWord2";

        adminService.updateAdminPassword(admin,newPassword);

        //then
        assertEquals(newPassword, admin.getPassword());
    }

    @Test
    void deleteAdminById() {


        //add a new admin
        adminService.saveAdmin(new Admin("username", "MyPassWord1!@#"));
        //when
        adminService.deleteAdminById(1L);

        //then
        verify(adminService).deleteAdminById(1L);

    }
}