package stevenlan.bookstore1.employees;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

@Service
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    
    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }



    public List<Employees> getEmployees(){
		
        return employeesRepository.findAll(); 
	}

    public void addNewEmployees(Employees employees){
        Optional<Employees> employeesByAccount = employeesRepository.findEmployeesByAccount(employees.getAccount());
        if(employeesByAccount.isPresent()){
            throw new IllegalStateException("account taken");
        }
        employeesRepository.save(employees);
    }

    public void deleteEmployees(Long employeesId){
        boolean exists = employeesRepository.existsById(employeesId);
        if(!exists){
            throw new IllegalStateException("this id does not exists");
        }
        employeesRepository.deleteById(employeesId);
    }

    @Transactional
    public void updateEmployees(
        Long employeesId, String name, String account, String password, String email, String phoneNumber){
            Employees employees = employeesRepository.findById(employeesId)
            .orElseThrow(() -> new IllegalStateException("this id does not exists"));

            if(name != null && name.length() > 0 && !Objects.equals(employees.getName(), name)){
                employees.setName(name);
            }
            if(password != null && password.length() > 0 && !Objects.equals(employees.getPassword(), password)){
                employees.setPassword(password);
            }
            if(email != null && email.length() > 0 && !Objects.equals(employees.getEmail(), email)){
                employees.setEmail(email);
            }
            if(phoneNumber != null && phoneNumber.length() > 0 && !Objects.equals(employees.getPhoneNumber(), phoneNumber)){
                employees.setPhoneNumber(phoneNumber);
            }
            if(account != null && account.length() > 0 && !Objects.equals(employees.getAccount(), account)){
                employees.setAccount(account);
            }
        }
}
