package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    // NOT TESTED

    private final ScheduleService scheduleService;
    private final PetService petService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, PetService petService,
                              EmployeeService employeeService, CustomerService customerService) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = DTOToEntity(scheduleDTO);
        return entityToDTO(scheduleService.save(schedule,
                scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return entityListToDTOList(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getScheduleForPet(petId);
        return entityListToDTOList(scheduleList);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getScheduleForEmployee(employeeId);
        return entityListToDTOList(scheduleList);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getScheduleForCustomer(customerId);
        return entityListToDTOList(scheduleList);
    }

    // conversion methods Entity <-> DTO
    private ScheduleDTO entityToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        // add pet and employee ids
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }

    private Schedule DTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private List<ScheduleDTO> entityListToDTOList(List<Schedule> scheduleList){
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule s: scheduleList){
            scheduleDTOList.add(entityToDTO(s));
        }
        return scheduleDTOList;
    }

}
