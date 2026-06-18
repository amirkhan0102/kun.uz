package dasturlash.uz.service;


import dasturlash.uz.dto.CommentCreateDTO;
import dasturlash.uz.dto.CommentResponseDTO;
import dasturlash.uz.entity.CommentEntity;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    // create commment
    public CommentResponseDTO create(CommentCreateDTO commentCreateDTO, Integer profileId) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setArticleId(commentCreateDTO.getArticleId());
        commentEntity.setProfileId(profileId);
        commentEntity.setContent(commentCreateDTO.getContent());
        commentEntity.setReplyId(commentCreateDTO.getReplyId());
        commentRepository.save(commentEntity);
        return toDTO(commentEntity);
    }


    // update comment
    public CommentResponseDTO update(Integer id, CommentCreateDTO dto, Integer profileId) {
        CommentEntity entity = commentRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Comment not found"));
        if (!entity.getProfileId().equals(profileId)) {
            throw new AppBadException("Comment profile id mismatch");
        }

        entity.setContent(dto.getContent());
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);
        return toDTO(entity);

    }


    // delete
    public String delete(Integer id, Integer profileId) {
        CommentEntity entity = commentRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Comment not found"));

        if (!entity.getProfileId().equals(profileId)) {
            throw new AppBadException("Comment profile id mismatch");
        }
        commentRepository.deleteComment(id);
        return "Comment succesfully  deleted";
    }


    // gett by article id

    public Page<CommentResponseDTO> getByArticleId(String articleId, int page, int size) {
        Page<CommentEntity> comments = commentRepository.findByArticleId(
                articleId, PageRequest.of(page, size));

        return comments.map(this::toDTO);


    }


    private CommentResponseDTO toDTO(CommentEntity commentEntity) {
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(commentEntity.getId());
        commentResponseDTO.setArticleId(commentEntity.getArticleId());
        commentResponseDTO.setProfileId(commentEntity.getProfileId());
        commentResponseDTO.setContent(commentEntity.getContent());
        commentResponseDTO.setReplyId(commentEntity.getReplyId());
        commentResponseDTO.setCreatedDate(commentEntity.getCreatedDate());
        return commentResponseDTO;
    }


}
