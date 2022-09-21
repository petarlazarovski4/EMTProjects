package com.marco.scmexc.api;

import com.marco.scmexc.models.domain.Item;
import com.marco.scmexc.models.domain.Type;
import com.marco.scmexc.models.requests.ItemRequest;
import com.marco.scmexc.models.response.ItemResponse;
import com.marco.scmexc.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/items")
public class ItemController {

    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/material/{materialID}")
    public List<ItemResponse> getItemsByMaterial(@PathVariable Long materialID){
        List<ItemResponse> items = this.itemService.getItemsByMaterial(materialID)
                .stream().map(item -> {
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
        return items;
    }

    @DeleteMapping("/{itemID}/delete")
    public ResponseEntity deleteItem(@PathVariable Long itemID) {
        this.itemService.deleteItemByID(itemID);
        return ResponseEntity.ok().build();

    }
    // post ili put ??
    @PostMapping("/edit")
    public ItemResponse editItem(@RequestBody ItemRequest request) {
        Item item = this.itemService.editItem(request);
        if(item.getType()==Type.QUESTION) {
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
    }

    @GetMapping("/{itemID}")
    public ItemResponse getItem(@PathVariable Long itemID) {
        Item item = this.itemService.getItemByID(itemID);
        if(item.getType()==Type.QUESTION) {
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

    }
}
