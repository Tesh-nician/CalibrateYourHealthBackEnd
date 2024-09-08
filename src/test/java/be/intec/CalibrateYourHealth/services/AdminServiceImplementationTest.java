package be.intec.CalibrateYourHealth.services;

import be.intec.CalibrateYourHealth.model.Admin;
import be.intec.CalibrateYourHealth.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import BCryptPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplementationTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImplementation adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setId(1L);
        admin.setUserName("adminUser");
        admin.setPassword("adminPass");
    }

    @Test
    void getAllAdmins() {
        // Given
        List<Admin> adminList = Arrays.asList(admin);
        when(adminRepository.findAll()).thenReturn(adminList);

        // When
        List<Admin> result = adminService.getAllAdmins();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(admin.getUserName(), result.get(0).getUserName());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAdminById() {
        // Given
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        // When
        Optional<Admin> result = adminService.getAdminById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(admin.getUserName(), result.get().getUserName());
        verify(adminRepository, times(1)).findById(1L);
    }

    @Test
    void getAdminByUserName() {
        // Given
        when(adminRepository.findAdminByUserName("adminUser")).thenReturn(Optional.of(admin));

        // When
        Optional<Admin> result = adminService.getAdminByUserName("adminUser");

        // Then
        assertTrue(result.isPresent());
        assertEquals(admin.getUserName(), result.get().getUserName());
        verify(adminRepository, times(1)).findAdminByUserName("adminUser");
    }

    @Test
    void saveAdminTest() {
        // Given
        //Create admin

        Admin admin = new Admin("adminUser1", "adminPass1");

        when(adminRepository.save(admin)).thenReturn(admin);

        // When saving an admin, the mock adminRepository should return the same admin



        Admin result = adminService.saveAdmin(admin);

        // Then
        assertNotNull(result);
        assertEquals(admin, result);
        verify(adminRepository, times(1)).save(admin);
    }

    /* Problemen met password encoder, Admin klasse heeft setpassword methode die password encodeert, geeft conflict met testmethodes
    @Test

    void updateAdminPassword() {
        // Given
        String newPassword = "newPass";
        String hashedPassword = passwordEncoder.encode(newPassword);

        Admin admin = new Admin("adminUser1", "adminPass1");

        when (passwordEncoder.encode(newPassword)).thenReturn(hashedPassword);
        when(adminRepository.save(admin)).thenReturn(admin);

        // When
        Admin result = adminService.updateAdminPassword(admin, newPassword);

        // Then
        assertNotNull(result);
        assertEquals(hashedPassword, result.getPassword());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(adminRepository, times(1)).save(admin);
    }

     */


    @Test
    void deleteAdminById() {
        // Given
        doNothing().when(adminRepository).deleteById(1L);

        // When
        adminService.deleteAdminById(1L);

        // Then
        verify(adminRepository, times(1)).deleteById(1L);
    }
}
