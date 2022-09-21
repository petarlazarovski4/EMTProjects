package com.marco.scmexc.web.impl;

import com.marco.scmexc.models.domain.Item;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.domain.Type;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.ItemResponse;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.services.MaterialService;
import com.marco.scmexc.web.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialMapperImpl implements MaterialMapper {

    @Autowired
    private MaterialService materialService;

    @Override
    public List<MaterialResponse> findAll() {
        return materialService.findAll()
                .stream()
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllApprovedMaterialsByCourseId(Long id) {
        return materialService.getAllMaterialsByCourse(id)
                .stream()
                .filter(Material::isPublished)
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllPendingMaterialsByCourseId(Long id) {
        return materialService.getAllMaterialsByCourse(id)
                .stream()
                .filter(x -> !x.isPublished())
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllMaterialsByCourseId(Long id) {
        return materialService.getAllMaterialsByCourse(id)
                .stream()
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialResponse> findAllPendingMaterials() {
        return materialService.findAll()
                .stream()
                .filter(x -> !x.isPublished())
                .map(this::mapMaterialToMaterialResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MaterialResponse findById(Long id) {
        return mapMaterialToMaterialResponse(materialService.findById(id));
    }

    @Override
    public MaterialResponse save(MaterialRequest materialRequest, UserPrincipal userPrincipal) {
        Material material = materialService.save(materialRequest, userPrincipal);
        return mapMaterialToMaterialResponse(material);
    }

    @Override
    public MaterialResponse publish(Long id, UserPrincipal userPrincipal) {
        return mapMaterialToMaterialResponse(materialService.publish(id,userPrincipal));
    }

    @Override
    public MaterialResponse unpublish(Long id, UserPrincipal userPrincipal) {
        return mapMaterialToMaterialResponse(materialService.unpublish(id,userPrincipal));
    }

    @Override
    public Page<Material> getAllMaterialsPaged(String searchQuery, Long course, Pageable pageable, UserPrincipal userPrincipal) {
        return materialService.getAllMaterialsPaged(pageable, searchQuery, course, userPrincipal);
    }


    private MaterialResponse mapMaterialToMaterialResponse(Material material){
        return MaterialResponse.of(material.getId(),material.getTitle(),material.getCreatedBy() != null ? material.getCreatedBy().getUsername() : null,
                material.getDateCreated(), material.isPublished(), material.getDescription(),material.getApprovedBy() != null ?  material.getApprovedBy().getUsername() : null,
                material.getUpVotes(), material.getDownVotes(), mapMaterialItemsToResponse(material.getItems()));
    }

    private List<ItemResponse> mapMaterialItemsToResponse(List<Item> items) {
        if(items == null) {
            return List.of();
        }
        return items.stream().map(item -> {
            if(item.getType() == Type.QUESTION) {
                return  ItemResponse.of(null,null,Type.QUESTION, ZonedDateTime.now(),item.getQuestion().getDescription(), item.getId(), item.getQuestion().getId());
            }
            else {
                String url = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/files/getFile/")
                        .path(item.getSmxFile().getId().toString())
                        .toUriString();

                return ItemResponse.of(item.getSmxFile().getFileName(),url,item.getType(),ZonedDateTime.now(),null, item.getId(), null);
            }
        }).collect(Collectors.toList());
    }

}
