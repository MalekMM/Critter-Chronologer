package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet save(Pet pet, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        pet.setCustomer(customer);
        // add pet to customer's pet list
        List<Pet> pets = customer.getPets();
        // create a new pet list if the list is empty
        if (pets.isEmpty()) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
        customer.setPets(pets);
        // save and return
        customerRepository.save(customer);
        return petRepository.save(pet);
    }

    public Pet getOnePet(Long id) {
        return petRepository.getOne(id);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByCustomer(Long customerId){
        return petRepository.findPetsByCustomer(customerId);
    }
}
