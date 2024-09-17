package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.dto.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.SkillRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill findSkillByName(String name) {
        return this.skillRepository.findByName(name);
    }

    public Skill handleCreateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public Skill findSkillById(Long id) {
        return this.skillRepository.findById(id).orElse(null);
    }

    public Skill handleUpdateSkill(Skill skill) {

        return this.skillRepository.save(skill);
    }

    public ResultPaginationDTO fetchFindAll(Specification<Skill> pageable, Pageable pageableRequest){
        Page<Skill> skills = this.skillRepository.findAll(pageable, pageableRequest);
        ResultPaginationDTO.Meta resultPaginationDTOMeta = new ResultPaginationDTO.Meta();
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();

        resultPaginationDTOMeta.setPage(pageableRequest.getPageNumber() + 1);
        resultPaginationDTOMeta.setPageSize(pageableRequest.getPageSize());
        resultPaginationDTOMeta.setTotal(skills.getTotalElements());
        resultPaginationDTOMeta.setPages(skills.getTotalPages());

        resultPaginationDTO.setMeta(resultPaginationDTOMeta);
        resultPaginationDTO.setResult(skills.getContent());

        return resultPaginationDTO;
    }
}
