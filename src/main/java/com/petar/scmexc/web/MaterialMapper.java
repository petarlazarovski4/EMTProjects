package com.marco.scmexc.web;

import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.security.UserPrincipal;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialMapper {

    List<MaterialResponse> findAll();
    List<MaterialResponse> findAllApprovedMaterialsByCourseId(Long id);
    List<MaterialResponse> findAllPendingMaterialsByCourseId(Long id);
    List<MaterialResponse> findAllMaterialsByCourseId(Long id);
    List<MaterialResponse> findAllPendingMaterials();
    MaterialResponse findById(Long id);
    MaterialResponse save(MaterialRequest materialRequest, UserPrincipal userPrincipal);
    MaterialResponse publish(Long id,UserPrincipal userPrincipal);
    MaterialResponse unpublish(Long id,UserPrincipal userPrincipal);
    Page<Material> getAllMaterialsPaged(String searchQuery, Long course, Pageable pageable, UserPrincipal userPrincipal);

}
