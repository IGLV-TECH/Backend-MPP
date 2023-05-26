package mpp.kotlin.backend.repository

import domain.Employee
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository: CrudRepository<Employee, Int> {
//    @Query("SELECT e FROM Employee e ORDER BY e.id ASC")
//    fun findAllEmployees(pageable: Pageable): List<Employee>
}