package be.intec.CalibrateYourHealth.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Admin {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "Username must be at least 3 characters long and contain only letters and numbers")
    private String userName;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{1,6}$",
            message = "Your password must contain at least one capital letter, one number, one lowercase letter and must be at least 8 characters long.")
    @Column(length = 60)
    private String password;

    public Admin() {
    }

    public Admin(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(getId(), admin.getId()) && Objects.equals(getUserName(), admin.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName());
    }

    public void setPassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    this.password = encoder.encode(password);
    }

}
