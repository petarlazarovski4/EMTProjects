package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.Item;
import com.marco.scmexc.models.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

        List<Item> findAllByType(Type type);
        List<Item> findAllByMaterial_Id(Long materialID);
        List<Item> findAllByMaterial_IdAndType(Long materialID,Type type);

}
