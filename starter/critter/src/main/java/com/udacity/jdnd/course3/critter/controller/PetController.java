package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    // NOT TESTED
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDTOToEntity(petDTO);
        petService.save(pet, pet.getCustomer().getId());
        return convertEntityToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getOnePet(petId);
        return convertEntityToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOList = new ArrayList<>();

        for (Pet p : petService.getAllPets()) {
            petDTOList.add(convertEntityToDTO(p));
        }

        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOList = new ArrayList<>();

        for (Pet p : petService.getPetsByCustomer(ownerId)) {
            petDTOList.add(convertEntityToDTO(p));
        }

        return petDTOList;
    }

    // conversion methods Entity <-> DTO
    private static PetDTO convertEntityToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        // ids have different names
        petDTO.setOwnerId(pet.getId());
        return petDTO;
    }

    private static Pet convertDTOToEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        // ids have different names
        pet.setId(petDTO.getOwnerId());
        return pet;
    }

}
