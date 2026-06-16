package dasturlash.uz.service;

import dasturlash.uz.dto.article.ArticleCreateDTO;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.article.ArticleFullInfoDTO;
import dasturlash.uz.dto.article.ArticleShortInfoDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.mapper.ArticleShortInfo;
import dasturlash.uz.repository.*;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleSectionService articleSectionService;

    @Autowired
    private AttachService attachService;
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleSectionRepository articleSectionRepository;

    @Autowired
    private SectionRepository sectionRepository;


    public ArticleDTO create(ArticleCreateDTO createDTO) {
        ArticleEntity entity = new ArticleEntity(); // entity -> abc
        toEntity(createDTO, entity);
        // Set default values
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setViewCount(0);
        entity.setSharedCount(0L);
        entity.setModeratorId(SpringSecurityUtil.currentProfileId());
        // save
        articleRepository.save(entity);
        // category -> merge
        articleCategoryService.merge(entity.getId(), createDTO.getCategoryList());
        // section -> merge
        articleSectionService.merge(entity.getId(), createDTO.getSectionList());
        // return
        return toDTO(entity);
    }

    public ArticleDTO update(String articleId, ArticleCreateDTO createDTO) {
        ArticleEntity entity = get(articleId);
        toEntity(createDTO, entity);
        // update
        articleRepository.save(entity);
        // category -> merge
        articleCategoryService.merge(entity.getId(), createDTO.getCategoryList());
        // section -> merge
        articleSectionService.merge(entity.getId(), createDTO.getSectionList());
        // return
        return toDTO(entity);
    }

    public String delete(String articleId) {
        int effectedRows = articleRepository.delete(articleId);
        if (effectedRows > 0) {
            return "Article deleted";
        } else {
            return "Something went wrong";
        }
//        ArticleEntity entity = get(articleId);
//        entity.setVisible(Boolean.FALSE);
//        articleRepository.save(entity);
//        return "Article deleted";
    }

    public String changeStatus(String articleId, ArticleStatus status) {
        int effectedRows = articleRepository.changeStatus(articleId, status);
        if (effectedRows > 0) {
            return "Article status change";
        } else {
            return "Something went wrong";
        }
//        ArticleEntity entity = get(articleId);
//        entity.setStatus(status);
//        articleRepository.save(entity);
//        return "Article status changed";
    }

    public List<ArticleDTO> getBySectionId(Integer sectionId, int limit) {
        List<ArticleShortInfo> resultList = articleRepository.getBySectionId(sectionId, limit);
        List<ArticleDTO> responseList = new LinkedList<>();
        resultList.forEach(mapper -> responseList.add(toDTO(mapper)));
        return responseList;
    }


//6 section last 12 except given ids
    public List<ArticleShortInfoDTO> getLast12ExceptIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            ids = List.of("non-existing-id");
        }
        Pageable pageable = PageRequest.of(0, 12);
        List<ArticleEntity> list = articleRepository.findLast12ExceptIds(ids, pageable);
        return list.stream().map(this::toShortInfoDTO).toList();
    }

    // 7 section findbycategoryid
    public Page<ArticleShortInfoDTO> getByCategoryId(Integer categoryId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ArticleEntity> articles=articleRepository.findByCategoryId(categoryId,pageable);
        return articles.map(this::toShortInfoDTO);
    }




    private ArticleShortInfoDTO toShortInfoDTO(ArticleEntity article) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setImageId(article.getImageId());
        dto.setPublishedDate(article.getPublishedDate());
        return dto;
    }

    // 8 get by region Id

    public Page<ArticleShortInfoDTO> getByRegionId(Integer regionId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ArticleEntity> articles=articleRepository.findByRegionId(regionId,pageable);
        return articles.map(this::toShortInfoDTO);
    }


    // 9 section Get by id and Language

    public ArticleFullInfoDTO getByIdAndLang(String id, String lang) {
        ArticleEntity article = articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article not found"));

        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setContent(article.getContent());
        dto.setImageId(article.getImageId());
        dto.setReadTime(article.getReadTime());
        dto.setViewCount(article.getViewCount());
        dto.setSharedCount(article.getSharedCount());
        dto.setPublishedDate(article.getPublishedDate());

        // Region nomi
        regionRepository.findById(article.getRegionId()).ifPresent(region -> {
            switch (lang.toUpperCase()) {
                case "UZ" -> dto.setRegionName(region.getNameUz());
                case "RU" -> dto.setRegionName(region.getNameRu());
                default -> dto.setRegionName(region.getNameEn());
            }
        });

        // Category nomi — getCategoryIdListByArticleId
        List<Integer> categoryIds = articleCategoryRepository.getCategoryIdListByArticleId(article.getId());
        if (!categoryIds.isEmpty()) {
            categoryRepository.findById(categoryIds.get(0)).ifPresent(category -> {
                switch (lang.toUpperCase()) {
                    case "UZ" -> dto.setCategoryName(category.getNameUz());
                    case "RU" -> dto.setCategoryName(category.getNameRu());
                    default -> dto.setCategoryName(category.getNameEn());
                }
            });
        }

        // Section nomi — getCategoryIdListByArticleId
        List<Integer> sectionIds = articleSectionRepository.getCategoryIdListByArticleId(article.getId());
        if (!sectionIds.isEmpty()) {
            sectionRepository.findById(sectionIds.get(0)).ifPresent(section -> {
                switch (lang.toUpperCase()) {
                    case "UZ" -> dto.setSectionName(section.getNameUz());
                    case "RU" -> dto.setSectionName(section.getNameRu());
                    default -> dto.setSectionName(section.getNameEn());
                }
            });
        }

        return dto;
    }


    // 11 section getlast 4 by section id
    public List<ArticleShortInfoDTO> getLast4BySectionId(Integer sectionId, String articleId) {
        Pageable pageable = PageRequest.of(0, 4);
        List<ArticleEntity> list = articleRepository.findLast4BySectionIdExceptId(sectionId, articleId, pageable);
        return list.stream().map(this::toShortInfoDTO).toList();
    }


    private void toEntity(ArticleCreateDTO dto, ArticleEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setReadTime(dto.getReadTime());
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setStatus(entity.getStatus());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    private ArticleDTO toDTO(ArticleShortInfo mapper) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setImage(attachService.openDTO(mapper.getId()));
        dto.setPublishedDate(mapper.getPublishedDate());
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Article not found");
        });
    }

}
