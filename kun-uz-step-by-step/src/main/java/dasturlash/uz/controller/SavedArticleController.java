package dasturlash.uz.controller;

import dasturlash.uz.dto.article.ArticleShortInfoDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.service.SavedArticleService;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saved-article")
public class SavedArticleController {

    @Autowired
    private SavedArticleService savedArticleService;



    // SAVE / UNSAVE
    @PostMapping("/{articleId}")
    public ResponseEntity<String> save(@PathVariable String articleId) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(savedArticleService.save(articleId, profileId));
    }

    // GET SAVED ARTICLES
    @GetMapping
    public ResponseEntity<Page<ArticleShortInfoDTO>> getSavedArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(savedArticleService.getSavedArticles(profileId, page, size));
    }


}