package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.dto.SectionCreateDTO;
import dastrulash.uz.kun.uz.dto.SectionDTO;
import dastrulash.uz.kun.uz.dto.SectionInfoDTO;
import dastrulash.uz.kun.uz.entity.SectionEntity;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    // 1. Create (ADMIN)
    public SectionInfoDTO create(SectionCreateDTO dto) {
        if (sectionRepository.existsByKey(dto.getKey())) {
            throw new AppBadException("Section key already exists");
        }
        SectionEntity entity = new SectionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        entity.setImageId(dto.getImageId());
        sectionRepository.save(entity);
        return toInfoDTO(entity);
    }

    // 2. Update (ADMIN)
    public SectionInfoDTO update(Long id, SectionCreateDTO dto) {
        SectionEntity entity = getById(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        entity.setImageId(dto.getImageId());
        sectionRepository.save(entity);
        return toInfoDTO(entity);
    }

    // 3. Delete (ADMIN)
    public String delete(Long id) {
        SectionEntity entity = getById(id);
        entity.setVisible(false);
        sectionRepository.save(entity);
        return "Section deleted";
    }

    // 4. Get List (ADMIN)
    public List<SectionInfoDTO> getList() {
        return sectionRepository.findAllByVisibleTrueOrderByOrderNumberAsc()
                .stream()
                .map(this::toInfoDTO)
                .collect(Collectors.toList());
    }

    // 5. Get By Lang (PUBLIC)
    public List<SectionDTO> getByLang(LanguageEnum lang) {
        return sectionRepository.findAllByVisibleTrueOrderByOrderNumberAsc()
                .stream()
                .map(entity -> toDTO(entity, lang))
                .collect(Collectors.toList());
    }

    // Helper methods
    private SectionEntity getById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Section not found"));
    }

    private SectionInfoDTO toInfoDTO(SectionEntity entity) {
        SectionInfoDTO dto = new SectionInfoDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setImageId(entity.getImageId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private SectionDTO toDTO(SectionEntity entity, LanguageEnum lang) {
        SectionDTO dto = new SectionDTO();
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