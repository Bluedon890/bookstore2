package stevenlan.bookstore1.employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/employees")
public class EmployeesController {

    private final EmployeesService employeesService;
    
    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping
	public List<Employees> getEmployees(){
		return employeesService.getEmployees();
		
	}

    @PostMapping
    public void registerNewEmployees(
        @RequestBody Employees employees){
        employeesService.addNewEmployees(employees);
    }

    @DeleteMapping(path = "{employeesId}")
    public void deleteEmployees(
        @PathVariable("employeesId") Long employeesId){
        employeesService.deleteEmployees(employeesId);
    }

    @PutMapping(path = "{employeesId}")
    public void updateEmployees(
        @PathVariable("employeesId") Long employeesId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String account,
        @RequestParam(required = false) String password,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phoneNumber){
            employeesService.updateEmployees(employeesId,name,account,password,email,phoneNumber);


    }

}
