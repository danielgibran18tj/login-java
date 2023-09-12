package com.login.loginjava.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolOpcionesService {

    private final BranchCrupRepository branchCrupRepository;

    public BranchService(BranchCrupRepository branchCrupRepository) {
        this.branchCrupRepository = branchCrupRepository;
    }


    public List<Branch> getAll(){
        return branchCrupRepository
                .findAll()
                .stream()
                .map(BranchMapper.INSTANCE::toBranch)
                .toList();
    }

    public Branch getId(Integer idBranch) throws ApplicationException {
        return branchCrupRepository
                .findById(idBranch)
                .map(BranchMapper.INSTANCE::toBranch)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_BRANCH", "branch does not exist"));
    }

    public Branch save(Branch branch) throws ApplicationException {
        if (branch.getId() == null){
            return BranchMapper.INSTANCE.toBranch(branchCrupRepository.save(BranchMapper.INSTANCE.toBranchEntity(branch)));
        }
        throw new ApplicationException(HttpStatus.FOUND, "FOUND_BRANCH", "idB" +
                "ranch is not null");
    }

    public Branch update(Branch branch) throws ApplicationException {
        if (exists(branch.getId())){
            var branchEntity1 = branchCrupRepository.findById(branch.getId()).get();
            branchEntity1.setNombre(branch.getName());
            branchEntity1.setDireccion(branch.getAddress());
            branchEntity1.setNumeroCelular(branch.getPhoneNumber());
            branchEntity1.setEmail(branch.getEmail());
            branchCrupRepository.save(branchEntity1);
            return branch;
        }else {
            throw new ApplicationException( "NOT_FOUND_BRANCH", "branch does not exist");
        }
    }


    public Boolean delete(int idBranch) throws ApplicationException {
        if (exists(idBranch)){
            branchCrupRepository.deleteById(idBranch);
            return true;
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_BRANCH", "branch does not exist");
    }


    public boolean exists(int idBranch){
        return this.branchCrupRepository.existsById(idBranch);
    }


}
