package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.dto.ProfileCreateDTO;
import dastrulash.uz.kun.uz.dto.ProfileInfoDTO;
import dastrulash.uz.kun.uz.dto.ProfileUpdateDTO;
import dastrulash.uz.kun.uz.entity.ProfileEntity;
import dastrulash.uz.kun.uz.entity.ProfileRoleEntity;
import dastrulash.uz.kun.uz.enums.ProfileRoleEnum;
import dastrulash.uz.kun.uz.enums.ProfileStatusEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.ProfileRepository;
import dastrulash.uz.kun.uz.repository.ProfileRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileRoleRepository profileRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 1. Create Profile (ADMIN)
    public ProfileInfoDTO create(ProfileCreateDTO dto) {
        if (profileRepository.existsByUsername(dto.getUsername())) {
            throw new AppBadException("Username already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : ProfileStatusEnum.ACTIVE);
        profileRepository.save(entity);

        // Rollarni saqlash
        saveRoles(entity, dto.getRoleList());

        return toInfoDTO(entity);
    }

    // 2. Get By Id (ADMIN)
    public ProfileInfoDTO getById(Long id) {
        return toInfoDTO(getEntityById(id));
    }

    // 3. Update Profile (ADMIN)
    public ProfileInfoDTO update(Long id, ProfileUpdateDTO dto) {
        ProfileEntity entity = getEntityById(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);

        // Rollarni yangilash
        profileRoleRepository.deleteByProfileId(id);
        saveRoles(entity, dto.getRoleList());

        return toInfoDTO(entity);
    }

    // 4. Delete (ADMIN)
    public String delete(Long id) {
        ProfileEntity entity = getEntityById(id);
        entity.setVisible(false);
        profileRepository.save(entity);
        return "Profile deleted";
    }

    // 5. Get List (ADMIN)
    public List<ProfileInfoDTO> getList() {
        return profileRepository.findAll()
                .stream()
                .filter(e -> e.getVisible())
                .map(this::toInfoDTO)
                .collect(Collectors.toList());
    }

    // Helper methods
    private void saveRoles(ProfileEntity entity, List<ProfileRoleEnum> roleList) {
        if (roleList == null || roleList.isEmpty()) return;
        roleList.forEach(role -> {
            ProfileRoleEntity roleEntity = new ProfileRoleEntity();
            roleEntity.setProfile(entity);
            roleEntity.setRoles(role);
            profileRoleRepository.save(roleEntity);
        });
    }

    private ProfileEntity getEntityById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Profile not found"));
    }

    private ProfileInfoDTO toInfoDTO(ProfileEntity entity) {
        ProfileInfoDTO dto = new ProfileInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setStatus(entity.getStatus());
        dto.setPhotoId(entity.getPhotoId());
        dto.setCreatedDate(entity.getCreatedDate());

        // Rollarni olish
        List<ProfileRoleEnum> roles = profileRoleRepository
                .findByProfileId(entity.getId())
                .stream()
                .map(ProfileRoleEntity::getRoles)
                .collect(Collectors.toList());
        dto.setRoleList(roles);

        return dto;
    }
}