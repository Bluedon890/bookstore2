package stevenlan.bookstore1.employees;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long>{

    @Query("SELECT s FROM Employees s WHERE s.account = ?1")
    Optional<Employees> findEmployeesByAccount(String account);
}
