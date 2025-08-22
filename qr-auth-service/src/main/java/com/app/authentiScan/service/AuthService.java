package com.app.authentiScan.service;

import com.app.authentiScan.exception.ResourceNotFoundException;
import com.app.authentiScan.exception.UniqueConstraintViolationException;
import com.app.authentiScan.exception.UserInformException;
import com.app.authentiScan.models.ERole;
import com.app.authentiScan.models.PasswordReset;
import com.app.authentiScan.models.Role;
import com.app.authentiScan.models.User;
import com.app.authentiScan.payload.request.ForgotPasswordRequest;
import com.app.authentiScan.payload.request.SignupRequest;
import com.app.authentiScan.repository.PasswordResetRepository;
import com.app.authentiScan.repository.RoleRepository;
import com.app.authentiScan.repository.UserRepository;
import com.app.authentiScan.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This email is not registered as sign up"));

        String token = UUID.randomUUID().toString();
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setToken(token);
        passwordReset.setUser(user);
        passwordReset.setExpiryDate(LocalDateTime.now().plusHours(1)); // 1hour expiry of random token
        passwordResetRepository.save(passwordReset);

        String resetLink = "http://84.247.147.198:9000/#/reset-password?token=" + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset");
        mailMessage.setText("To reset your password, click the link below:\n" + resetLink);
        mailSender.send(mailMessage);
    }

    @Transactional
    public void resetPassword(String token, String password) {
        PasswordReset resetToken = passwordResetRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new UserInformException("Invalid or expired token");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        passwordResetRepository.delete(resetToken);
    }

    public void registerUser(SignupRequest request) {
        checkIfUserExists(request);
        Set<Role> userRoles = getRolesFromNames(request.getRoles());

        User user = UserMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    private void checkIfUserExists(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UniqueConstraintViolationException("Username already exists.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UniqueConstraintViolationException("Email already exists.");
        }
        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new UniqueConstraintViolationException("Mobile number already exists.");
        }
    }

    private Set<Role> getRolesFromNames(Set<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> roleRepository.findByName(ERole.valueOf(roleName))
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
    }

}
