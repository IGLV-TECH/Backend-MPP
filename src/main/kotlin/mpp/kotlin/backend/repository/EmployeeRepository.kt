package mpp.kotlin.backend.repository

import domain.Employee
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository: CrudRepository<Employee, Int> {}