package com.campany.service;

import com.campany.dto.ManagerDTO;
import com.campany.entity.Manager;
import com.campany.mapper.ManagerMapper;
import com.campany.repository.ManagerRepository;

import java.util.List;

public class ManagerService implements CrudService<ManagerDTO> {
    private final ManagerRepository managerRepository;
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
    public ManagerDTO create(ManagerDTO item) throws ClassNotFoundException {
        Manager manager = ManagerMapper.toEntity(item);
        managerRepository.save(manager);
        return ManagerMapper.toDTO(manager);
    }
    @Override
    public ManagerDTO update(Integer id, ManagerDTO item) throws ClassNotFoundException {
        item.setId(id);
        Manager manager = ManagerMapper.toEntity(item);
        managerRepository.update(manager);
        return ManagerMapper.toDTO(manager);
    }
    @Override
    public void deleteById(Integer id) throws ClassNotFoundException {
        managerRepository.deleteById(id);
    }
}
