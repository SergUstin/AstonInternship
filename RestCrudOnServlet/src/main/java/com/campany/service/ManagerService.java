package com.campany.service;

import com.campany.dto.ManagerDTO;
import com.campany.entity.Manager;
import com.campany.mapper.ManagerMapper;
import com.campany.repository.ManagerRepository;

import java.util.List;

public class ManagerService implements CrudService<ManagerDTO> {

    private ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public ManagerDTO getById(Integer id) {
        Manager manager = managerRepository.findById(id);
        return ManagerMapper.toDTO(manager);
    }

    @Override
    public List<ManagerDTO> getAll() {
        List<Manager> managers = managerRepository.findAll();
        return ManagerMapper.toDTOList(managers);
    }

    @Override
    public void create(ManagerDTO item) {
        managerRepository.create(ManagerMapper.toEntity(item));
    }

    @Override
    public void update(ManagerDTO item) {
        managerRepository.update(ManagerMapper.toEntity(item));
    }

    @Override
    public void deleteById(Integer id) {
        managerRepository.deleteById(id);
    }
}
