package dastrulash.uz.kun.uz.service;


import dastrulash.uz.kun.uz.dto.CategoryDTO;
import dastrulash.uz.kun.uz.entity.CategoryEntity;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> optional = repository.findByCategoryKey(dto.getCategoryKey());
        if (optional.isPresent()) {
            throw new AppBadException("Category key already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getCategoryKey());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        // response
        dto.setId(Math.toIntExact(entity.getId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO newDto) {// Jahon
        Optional<CategoryEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Category not found");
        }
        Optional<CategoryEntity> keyOptional = repository.findByCategoryKey(newDto.getCategoryKey()); // Jahon
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new AppBadException("CategoryKey present");
        }
        // 1-Jahon,2-Iksodiyot,3-Sport
        CategoryEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setKey(newDto.getCategoryKey());
        repository.save(entity);

        newDto.setId(Math.toIntExact(entity.getId()));
        return newDto;
    }

    public Boolean delete(Integer id) {
        return repository.updateVisibleById(id) == 1;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<CategoryDTO> getAllByLang(LanguageEnum lang) { // uz
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted(); // TODO  get category by lang 1
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(Math.toIntExact(entity.getId()));
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCategoryKey(entity.getKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private CategoryDTO toLangResponseDto(LanguageEnum lang, CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(Math.toIntExact(entity.getId()));
        dto.setCategoryKey(entity.getKey());
        dto.setOrderNumber(entity.getOrderNumber());
        switch (lang) {
            case UZ:
                dto.setName(entity.getNameUz());
                break;
            case RU:
                dto.setName(entity.getNameRu());
                break;
            case EN:
                dto.setName(entity.getNameEn());
                break;
        }
        return dto;
    }

    public CategoryEntity get(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Item not found");
        });
    }
}
