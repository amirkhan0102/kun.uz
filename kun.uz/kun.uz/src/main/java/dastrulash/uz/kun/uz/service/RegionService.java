package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.dto.RegionCreateDTO;
import dastrulash.uz.kun.uz.dto.RegionDTO;
import dastrulash.uz.kun.uz.dto.RegionInfoDTO;
import dastrulash.uz.kun.uz.entity.RegionEntity;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    // 1. Create (ADMIN)
    public RegionInfoDTO create(RegionCreateDTO dto) {
        if (regionRepository.existsByKey(dto.getKey())) {
            throw new AppBadException("Region key already exists");
        }
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        regionRepository.save(entity);
        return toInfoDTO(entity);
    }

    // 2. Update (ADMIN)
    public RegionInfoDTO update(Long id, RegionCreateDTO dto) {
        RegionEntity entity = getById(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        regionRepository.save(entity);
        return toInfoDTO(entity);
    }

    // 3. Delete (ADMIN)
    public String delete(Long id) {
        RegionEntity entity = getById(id);
        entity.setVisible(false);
        regionRepository.save(entity);
        return "Region deleted";
    }

    // 4. Get List (ADMIN)
    public List<RegionInfoDTO> getList() {
        return regionRepository.findAllByVisibleTrueOrderByCreatedDateDesc()
                .stream()
                .map(this::toInfoDTO)
                .collect(Collectors.toList());
    }

    // 5. Get By Lang (PUBLIC)
    public List<RegionDTO> getByLang(LanguageEnum lang) {
        return regionRepository.findAllByVisibleTrueOrderByCreatedDateDesc()
                .stream()
                .map(entity -> toDTO(entity, lang))
                .collect(Collectors.toList());
    }

    // Helper methods
    private RegionEntity getById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Region not found"));
    }

    private RegionInfoDTO toInfoDTO(RegionEntity entity) {
        RegionInfoDTO dto = new RegionInfoDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private RegionDTO toDTO(RegionEntity entity, LanguageEnum lang) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        switch (lang) {
            case ru -> dto.setName(entity.getNameRu());
            case en -> dto.setName(entity.getNameEn());
            default -> dto.setName(entity.getNameUz());
        }
        return dto;
    }
}