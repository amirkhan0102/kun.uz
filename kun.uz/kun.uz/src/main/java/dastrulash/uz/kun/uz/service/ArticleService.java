package dastrulash.uz.kun.uz.service;

import dastrulash.uz.kun.uz.dto.article.ArticleCreateDTO;
import dastrulash.uz.kun.uz.dto.article.ArticleFullInfoDTO;
import dastrulash.uz.kun.uz.dto.article.ArticleShortInfoDTO;
import dastrulash.uz.kun.uz.entity.*;
import dastrulash.uz.kun.uz.enums.ArticleStatus;
import dastrulash.uz.kun.uz.enums.LanguageEnum;
import dastrulash.uz.kun.uz.exceptions.AppBadException;
import dastrulash.uz.kun.uz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;
    @Autowired
    private ArticleSectionRepository articleSectionRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SectionRepository sectionRepository;

    // 1. CREATE (Moderator)
    @Transactional
    public ArticleShortInfoDTO create(ArticleCreateDTO dto, Integer moderatorId) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setModeratorId(moderatorId);
        entity.setReadTime(dto.getReadTime());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);

        // Category saqlash
        saveCategories(entity.getId(), dto.getCategoryIds());

        // Section saqlash
        saveSections(entity.getId(), dto.getSectionIds());

        return toShortInfo(entity);
    }

    // 2. UPDATE (Moderator)
    @Transactional
    public ArticleShortInfoDTO update(String id, ArticleCreateDTO dto, Integer moderatorId) {
        ArticleEntity entity = articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article not found"));

        if (!entity.getModeratorId().equals(moderatorId)) {
            throw new AppBadException("You can only edit your own articles");
        }

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setReadTime(dto.getReadTime());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);

        // Category yangilash
        articleCategoryRepository.deleteByArticleId(id);
        saveCategories(id, dto.getCategoryIds());

        // Section yangilash
        articleSectionRepository.deleteByArticleId(id);
        saveSections(id, dto.getSectionIds());

        return toShortInfo(entity);
    }

    // 3. DELETE (Moderator)
    public String delete(String id, Integer moderatorId) {
        ArticleEntity entity = articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article not found"));

        if (!entity.getModeratorId().equals(moderatorId)) {
            throw new AppBadException("You can only delete your own articles");
        }

        entity.setVisible(false);
        articleRepository.save(entity);
        return "Article deleted";
    }

    // 4. Change status (Publisher)
    public String changeStatus(String id, ArticleStatus status, Integer publisherId) {
        ArticleEntity entity = articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article not found"));

        entity.setStatus(status);
        entity.setPublisherId(publisherId);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
        }
        articleRepository.save(entity);
        return "Status changed to: " + status;
    }

    // 5. Get Last N by sectionId
    public List<ArticleShortInfoDTO> getBySectionId(Integer sectionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> result = articleRepository.findBySectionId(sectionId, pageable);
        return result.stream().map(this::toShortInfo).collect(Collectors.toList());
    }

    // 6. Get Last 12 except given ids
    public List<ArticleShortInfoDTO> getLast12ExceptIds(List<String> ids, int page) {
        Pageable pageable = PageRequest.of(page, 12);
        Page<ArticleEntity> result = articleRepository.findLast12ExceptIds(ids, pageable);
        return result.stream().map(this::toShortInfo).collect(Collectors.toList());
    }

    // 7. Get Last N by categoryId
    public List<ArticleShortInfoDTO> getByCategoryId(Integer categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> result = articleRepository.findByCategoryId(categoryId, pageable);
        return result.stream().map(this::toShortInfo).collect(Collectors.toList());
    }

    // 8. Get Last N by regionId
    public List<ArticleShortInfoDTO> getByRegionId(Integer regionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> result = articleRepository.findByRegionId(regionId, pageable);
        return result.stream().map(this::toShortInfo).collect(Collectors.toList());
    }

    // 9. Get by id and lang
    public ArticleFullInfoDTO getByIdAndLang(String id, LanguageEnum lang) {
        ArticleEntity entity = articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article not found"));
        return toFullInfo(entity, lang);
    }

    // Helper methods
    private void saveCategories(String articleId, List<Integer> categoryIds) {
        categoryIds.forEach(categoryId -> {
            ArticleCategoryEntity ac = new ArticleCategoryEntity();
            ac.setArticleId(articleId);
            ac.setCategoryId(categoryId);
            articleCategoryRepository.save(ac);
        });
    }

    private void saveSections(String articleId, List<Integer> sectionIds) {
        sectionIds.forEach(sectionId -> {
            ArticleSectionEntity as = new ArticleSectionEntity();
            as.setArticleId(articleId);
            as.setSectionId(sectionId);
            articleSectionRepository.save(as);
        });
    }

    private ArticleShortInfoDTO toShortInfo(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageId(entity.getImageId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    private ArticleFullInfoDTO toFullInfo(ArticleEntity entity, LanguageEnum lang) {
        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setImageId(entity.getImageId());
        dto.setPublishedDate(entity.getPublishedDate());

        // Region
        if (entity.getRegionId() != null) {
            regionRepository.findByIdAndVisibleIsTrue(entity.getRegionId()).ifPresent(region -> {
                ArticleFullInfoDTO.RegionDTO regionDTO = new ArticleFullInfoDTO.RegionDTO();
                regionDTO.setKey(region.getRegionKey());
                regionDTO.setName(switch (lang) {
                    case RU -> region.getNameRu();
                    case EN -> region.getNameEn();
                    default -> region.getNameUz();
                });
                dto.setRegion(regionDTO);
            });
        }

        // Categories
        List<ArticleFullInfoDTO.CategoryDTO> categoryList = articleCategoryRepository
                .findByArticleId(entity.getId())
                .stream()
                .map(ac -> categoryRepository.findByIdAndVisibleIsTrue(ac.getCategoryId())
                        .map(category -> {
                            ArticleFullInfoDTO.CategoryDTO categoryDTO = new ArticleFullInfoDTO.CategoryDTO();
                            categoryDTO.setKey(category.getCategoryKey());
                            categoryDTO.setName(switch (lang) {
                                case RU -> category.getNameRu();
                                case EN -> category.getNameEn();
                                default -> category.getNameUz();
                            });
                            return categoryDTO;
                        }).orElse(null))
                .collect(Collectors.toList());
        dto.setCategoryList(categoryList);

        // Sections
        List<ArticleFullInfoDTO.SectionDTO> sectionList = articleSectionRepository
                .findByArticleId(entity.getId())
                .stream()
                .map(as -> sectionRepository.findByIdAndVisibleIsTrue(as.getSectionId())
                        .map(section -> {
                            ArticleFullInfoDTO.SectionDTO sectionDTO = new ArticleFullInfoDTO.SectionDTO();
                            sectionDTO.setId(section.getId());
                            sectionDTO.setName(switch (lang) {
                                case RU -> section.getNameRu();
                                case EN -> section.getNameEn();
                                default -> section.getNameUz();
                            });
                            return sectionDTO;
                        }).orElse(null))
                .collect(Collectors.toList());
        dto.setSectionList(sectionList);

        return dto;
    }
}