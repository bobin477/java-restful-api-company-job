package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.dto.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skill")
    @ApiMessage("create skill")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {
        Skill currentSkill = this.skillService.findSkillByName(skill.getName());

        if (currentSkill != null) {
            throw new IdInvalidException("Ky nang " + skill.getName() + " da ton tai");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    @PutMapping("/skill")
    @ApiMessage("update skill")
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {
        Skill currentSkill = this.skillService.findSkillById(skill.getId());
        if (currentSkill == null) {
            throw new IdInvalidException("id " + skill.getId() + " khong ton tai");
        }

        Skill skillByName = this.skillService.findSkillByName(skill.getName());

        if (skillByName != null) {
            throw new IdInvalidException("Ky nang " + skill.getName() + " da ton tai");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleUpdateSkill(skill));
    }

    @GetMapping("/skill")
    @ApiMessage("Fetch all skill")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(
            @Filter Specification<Skill> specification, Pageable pageable
    ) {
        return ResponseEntity.ok().body(this.skillService.fetchFindAll(specification, pageable));
    }


}
