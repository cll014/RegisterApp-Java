package edu.uark.registerapp.models.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.models.entities.EmployeeEntity;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, UUID> {
	boolean existsByIsActive(boolean isActive);
	boolean existsByEmployeeId(int employeeId);
	EmployeeEntity findById(UUID id);
	EmployeeEntity findByEmployeeId(int employeeId);
}