package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.dto.CategoryDTO;
import dastrulash.uz.kun.uz.dto.CategoryInfoDTO;
import dastrulash.uz.kun.uz.entity.CategoryEntity;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.topnews.dto.category.CategoryCreateDTO;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 1. Create (ADMIN)
    public CategoryInfoDTO create(CategoryCreateDTO dto) {
        if (categoryRepository.existsByKey(dto.getKey())) {
            throw new AppBadException("Category key already exists");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        categoryRepository.save(entity);
        return toInfoDTO(entity);
    }

    // 2. Update (ADMIN)
    public CategoryInfoDTO update(Long id, CategoryCreateDTO dto) {
        CategoryEntity entity = getById(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        categoryRepository.save(entity);
        return toInfoDTO(entity);
    }

    // 3. Delete (ADMIN)
    public String delete(Long id) {
        CategoryEntity entity = getById(id);
        entity.setVisible(false);
        categoryRepository.save(entity);
        return "Category deleted";
    }

    // 4. Get List (ADMIN)
    public List<CategoryInfoDTO> getList() {
        return categoryRepository.findAllByVisibleTrueOrderByOrderNumberAsc()
                .stream()
                .map(this::toInfoDTO)
                .collect(Collectors.toList());
    }

    // 5. Get By Lang (PUBLIC)
    public List<CategoryDTO> getByLang(LanguageEnum lang) {
        return categoryRepository.findAllByVisibleTrueOrderByOrderNumberAsc()
                .stream()
                .map(entity -> toDTO(entity, lang))
                .collect(Collectors.toList());
    }

    // Helper methods
    private CategoryEntity getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Category not found"));
    }

    private CategoryInfoDTO toInfoDTO(CategoryEntity entity) {
        CategoryInfoDTO dto = new CategoryInfoDTO();
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

    private CategoryDTO toDTO(CategoryEntity entity, LanguageEnum lang) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setKey(entity.getKey());
        switch (lang) {
            case ru -> dto.setName(entity.getNameRu());
            case en -> dto.setName(entity.getNameEn());
            default -> dto.setName(entity.getNameUz());
        }
        return dto;
    }
}