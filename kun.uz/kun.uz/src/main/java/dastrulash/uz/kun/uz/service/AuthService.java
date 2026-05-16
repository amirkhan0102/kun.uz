package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.config.JwtUtil;
import dastrulash.uz.kun.uz.dto.auth.LoginDTO;
import dastrulash.uz.kun.uz.dto.auth.LoginResponseDTO;
import dastrulash.uz.kun.uz.dto.auth.RegistrationDTO;
import dastrulash.uz.kun.uz.entity.ProfileEntity;
import dastrulash.uz.kun.uz.entity.ProfileRoleEntity;
import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.ProfileRepository;
import dastrulash.uz.kun.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${server.url}")
    private String serverUrl;

    // 1. Registration
    public String register(RegistrationDTO dto) {
        // Username mavjudmi?
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (optional.isPresent()) {
            throw new AppBadException("Username already exists");
        }

        // Profile saqlash
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setStatus(ProfileStatusEnum.NOT_ACTIVE);
        entity.setVisible(true);
        profileRepository.save(entity);

        // Role saqlash
        ProfileRoleEntity roleEntity = new ProfileRoleEntity();
        roleEntity.setProfile(entity);
        roleEntity.setRoles(ProfileRoleEnum.ROLE_USER);
        profileRoleRepository.save(roleEntity);

        // Verification token yaratish
        String token = jwtUtil.generateToken(entity.getUsername(), entity.getId());
        String verificationLink = serverUrl + "/api/v1/auth/verify?token=" + token;

        // Email yuborish
        emailService.sendVerificationEmail(entity.getUsername(), verificationLink);

        return "Verification email sent to: " + entity.getUsername();
    }

    // 2. Verification
    public String verify(String token) {
        if (!jwtUtil.isValid(token)) {
            throw new AppBadException("Token invalid or expired");
        }
        String username = jwtUtil.getUsername(token);
        ProfileEntity entity = profileRepository.findByUsernameAndVisibleIsTrue(username)
                .orElseThrow(() -> new AppBadException("Profile not found"));

        if (entity.getStatus().equals(ProfileStatusEnum.ACTIVE)) {
            throw new AppBadException("Already verified");
        }

        entity.setStatus(ProfileStatusEnum.ACTIVE);
        profileRepository.save(entity);
        return "Email verified successfully!";
    }

    // 3. Login
    public LoginResponseDTO login(LoginDTO dto) {
        ProfileEntity entity = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername())
                .orElseThrow(() -> new AppBadException("Username or password wrong"));

        // Password tekshirish
        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new AppBadException("Username or password wrong");
        }

        // Status tekshirish
        if (!entity.getStatus().equals(ProfileStatusEnum.ACTIVE)) {
            throw new AppBadException("Account not active. Please verify your email.");
        }

        // Token yaratish
        String token = jwtUtil.generateToken(entity.getUsername(), entity.getId());

        // Response yaratish
        LoginResponseDTO response = new LoginResponseDTO();
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setUsername(entity.getUsername());
        response.setToken(token);

        // Rollarni olish
        List<ProfileRoleEnum> roles = profileRoleRepository.getRoleListByProfileId(entity.getId());
        response.setRoleList(roles);

        // Photo (agar mavjud bo'lsa)
        if (entity.getPhotoId() != null) {
            LoginResponseDTO.PhotoDTO photo = new LoginResponseDTO.PhotoDTO();
            photo.setId(entity.getPhotoId());
            photo.setUrl("/api/v1/attach/" + entity.getPhotoId());
            response.setPhoto(photo);
        }

        return response;
    }

    // 4. Resend verification email
    public String resend(String email) {
        ProfileEntity entity = profileRepository.findByUsernameAndVisibleIsTrue(email)
                .orElseThrow(() -> new AppBadException("Profile not found"));

        if (entity.getStatus().equals(ProfileStatusEnum.ACTIVE)) {
            throw new AppBadException("Already verified");
        }

        // Yangi token yaratish
        String token = jwtUtil.generateToken(entity.getUsername(), entity.getId());
        String verificationLink = serverUrl + "/api/v1/auth/verify?token=" + token;

        // Email yuborish (limit tekshiriladi EmailService ichida)
        emailService.sendVerificationEmail(entity.getUsername(), verificationLink);

        return "Verification email resent to: " + email;
    }
}