package dasturlash.uz.controller;


import dasturlash.uz.dto.CommentCreateDTO;
import dasturlash.uz.dto.CommentResponseDTO;
import dasturlash.uz.service.CommentService;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;



    // CREATE
    @PostMapping
    public ResponseEntity<CommentResponseDTO> create(@RequestBody CommentCreateDTO dto) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(commentService.create(dto, profileId));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(@PathVariable Integer id,
                                                     @RequestBody CommentCreateDTO dto) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(commentService.update(id, dto, profileId));
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(commentService.delete(id, profileId));
    }

    // GET BY ARTICLE ID
    @GetMapping("/{articleId}")
    public ResponseEntity<Page<CommentResponseDTO>> getByArticleId(
            @PathVariable String articleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.getByArticleId(articleId, page, size));
    }


}
