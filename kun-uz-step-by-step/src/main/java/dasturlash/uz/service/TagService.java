package dasturlash.uz.service;

import dasturlash.uz.dto.tag.TagCreateDTO;
import dasturlash.uz.dto.tag.TagResponseDTO;
import dasturlash.uz.entity.tag.TagEntity;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.TagRepository;
import org.aspectj.apache.bcel.generic.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {


    @Autowired
    private TagRepository tagRepository;


    // create tag
    public TagResponseDTO create(TagCreateDTO dto){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(dto.getName());
        tagRepository.save(tagEntity);
        return toDTO(tagEntity);

    }


    // update

    public TagResponseDTO update(Integer id, TagCreateDTO dto){
        TagEntity entity= tagRepository.findByIdAndVisibleTrue(id).
                orElseThrow(()->new RuntimeException("Tag not found"));
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return toDTO(entity);
    }



    // delete

    public String delete(Integer id) {
        tagRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Tag not found"));
        tagRepository.deleteTagById(id);
        return "Tag deleted";
    }


    // get list

    public List<TagResponseDTO> getAll(){
        return tagRepository.findAllVisible()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // helper metod
    private TagResponseDTO toDTO(TagEntity tagEntity){
        TagResponseDTO dto = new TagResponseDTO();
        dto.setId(tagEntity.getId());
        dto.setName(tagEntity.getName());
        dto.setCreatedDate(tagEntity.getCreatedDate());
        return dto;
    }


}
