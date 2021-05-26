package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    // save customer
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    // get all customers
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    // get owner by pet
    public Customer getCustomerByPetId(Long petId) {
        Pet pet = petRepository.getOne(petId);
        return pet.getCustomer();
    }

}
