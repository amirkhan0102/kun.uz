package dasturlash.uz.service;

import dasturlash.uz.dto.CommentLikeDTO;
import dasturlash.uz.entity.CommentLikeEntity;
import dasturlash.uz.enums.LikeStatus;
import dasturlash.uz.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public String like(CommentLikeDTO dto, Integer profileId) {
        Optional<CommentLikeEntity> optional = commentLikeRepository
                .findByCommentIdAndProfileId(dto.getCommentId(), profileId);

        if (optional.isPresent()) {
            CommentLikeEntity entity = optional.get();
            if (entity.getStatus().equals(dto.getStatus())) {
                commentLikeRepository.delete(entity);
                return "Reaksiya qaytarib olindi";
            }
            // LIKE - DISLIKE yoki otherway
            entity.setStatus(dto.getStatus());
            entity.setUpdatedDate(LocalDateTime.now());
            commentLikeRepository.save(entity);

            return "Reaksiya o'zgartirildi ";
        }

        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setProfileId(profileId);
        entity.setStatus(dto.getStatus());
        entity.setUpdatedDate(LocalDateTime.now());
        commentLikeRepository.save(entity);
        return "Emotsiya bildirildi";
    }

    public Long getLikeCount(Integer commentId) {
        return commentLikeRepository.countByCommentIdAndStatus(commentId, LikeStatus.LIKE);
    }

    public Long getDislikeCount(Integer commentId) {
        return commentLikeRepository.countByCommentIdAndStatus(commentId, LikeStatus.DISLIKE);
    }
}