package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // save employee
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    // get one employee
    public Employee getOneEmployee(Long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    // set availability
    public void setAvailability(Set<DayOfWeek> days, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);
    }

    // find employees for service
    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        // get all employees that are available for the date
        List<Employee> employees = employeeRepository.findAllByDaysAvailable(date.getDayOfWeek());
        // filter employees by removing those who do not have the appropriate skills
        for (Employee e: employees) {
            if (!e.getSkills().containsAll(skills)) {
                employees.remove(e);
            }
        }
        return employees;
    }
}
