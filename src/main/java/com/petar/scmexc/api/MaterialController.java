package com.marco.scmexc.api;

import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.requests.AddQuestionRequest;
import com.marco.scmexc.models.requests.MaterialRequest;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.services.MaterialService;
import com.marco.scmexc.web.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("api/materials")
public class MaterialController {

    @Autowired
    private MaterialService service;
    @Autowired
    private MaterialMapper materialMapper;

//    public MaterialController(MaterialService service, MaterialMapper materialMapper) {
//        this.service = service;
//        this.materialMapper = materialMapper;
//    }


    // cisto onaka da probam dali rabote :)
    @GetMapping("/all")
    public List<MaterialResponse> getAllMaterials(){
        return materialMapper.findAll();
    }

    @GetMapping("/paged")
    public Page<Material> getAllMaterialsPaged(@RequestParam(required = false, defaultValue = "", name = "q") String searchQuery,
                                               @RequestParam(required = false) Long course, Pageable pageable, @CurrentUser UserPrincipal userPrincipal) {
        return materialMapper.getAllMaterialsPaged(searchQuery, course, pageable, userPrincipal);
    }

    @GetMapping("/all/approved/{courseId}")
    public List<MaterialResponse> getAllApprovedMaterialsByCourse(@PathVariable Long courseId){
        return materialMapper.findAllApprovedMaterialsByCourseId(courseId);
    }

    @GetMapping("/all/pending/{courseId}")
    public List<MaterialResponse> getAllPendingMaterialsByCourse(@PathVariable Long courseId){
        return materialMapper.findAllPendingMaterialsByCourseId(courseId);
    }

    @GetMapping("/all/{courseId}")
    public List<MaterialResponse> getAllMaterialsByCourse(@PathVariable Long courseId){
        return materialMapper.findAllMaterialsByCourseId(courseId);
    }

    @GetMapping("/all/pending")
    public List<MaterialResponse> getAllPendingMaterials(){
        return materialMapper.findAllPendingMaterials();
    }

    @GetMapping("/{id}")
    public MaterialResponse findById(@PathVariable Long id){
        return materialMapper.findById(id);
    }

    @PostMapping("/create")
    public MaterialResponse createNewMaterial(@RequestBody MaterialRequest request, @CurrentUser UserPrincipal userPrincipal) {
        return materialMapper.save(request, userPrincipal);
    }

    @PostMapping("/{id}/publish")
    public MaterialResponse approveMaterial(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal){
        return materialMapper.publish(id,userPrincipal);
    }

    @PostMapping("/{id}/unpublish")
    public MaterialResponse unpublishMaterial(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        return materialMapper.unpublish(id, userPrincipal);
    }

    @PostMapping("/{id}/addFile")
    public ResponseEntity addFile(@PathVariable Long id, @RequestParam MultipartFile file) {
       String message ;
        try {
             service.addItem(id, file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }

    @PostMapping("/addQuestion")
    public ResponseEntity addQuestion(@RequestBody AddQuestionRequest request){
        service.addQuestion(request);
        return  ResponseEntity.ok().build();
    }

    @PostMapping("can-access/{id}")
    public ResponseEntity<Boolean> canAccessMaterial(@PathVariable Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        boolean canAccess = service.canUserAccessEditMaterial(id, userPrincipal);
                return canAccess ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<String> incUpVotes(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        boolean success = this.service.incUpVotes(id, userPrincipal.getId());
        return success ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Upvoted.");
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<String> incDownVotes(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        boolean success = this.service.incDownVotes(id, userPrincipal.getId());
        return success ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Downvoted.");
    }

}
