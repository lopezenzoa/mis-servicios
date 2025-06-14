package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.CustomerDTO;

public class CustomerValidator {
    // private static CustomerRepository customerRepo;

   // public static boolean checkValidEmail(String email, CustomerRepository customerRepo) {
  //      return customerRepo.findAll()
    //            .stream()
 //               .anyMatch(c -> c.getEmail().equals(email));
    //}
   public static boolean isEmailAvailable(String email, CustomerRepository repo) {
       return !repo.existsByEmail(email);
   }

    public static boolean isPhoneAvailable(String phone, CustomerRepository repo) {
        return !repo.existsByPhoneNumber(phone);
    }




    /* public static boolean checkValidity(CustomerDTO dto, CustomerRepository repo) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && !checkValidPhone(dto.getPhoneNumber(), repo) // debe estar disponible
                && !dto.getAddress().isEmpty()
                && !checkValidEmail(dto.getEmail(), repo)       // debe estar disponible
                && !dto.getUsername().isEmpty()
                && !dto.getPassword().isEmpty();
    }*/
    public static boolean checkValidity(CustomerDTO dto, CustomerRepository repo) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && isPhoneAvailable(dto.getPhoneNumber(), repo)
                && !dto.getAddress().isEmpty()
                && isEmailAvailable(dto.getEmail(), repo)
                && !dto.getUsername().isEmpty()
                && !dto.getPassword().isEmpty();
    }

}
