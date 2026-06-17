package dasturlash.uz.repository;

import dasturlash.uz.entity.tag.TagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


public interface TagRepository extends CrudRepository<TagEntity,Integer> {

    @Query("FROM TagEntity WHERE visible=true ORDER BY createdDate DESC ")
    List<TagEntity> findAllVisible();

    Optional<TagEntity> findByIdAndVisibleTrue(Integer id);

    Optional<TagEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE TagEntity SET visible=false WHERE id =?1")
    void deleteTagById(int id);


}
