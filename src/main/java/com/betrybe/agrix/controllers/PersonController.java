package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.PersonDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de persons.
 */

@RestController
@RequestMapping("/persons")
public class PersonController {
  private PersonService personService;

  /**
   * Constructor de persons.
   */

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Cria uma nova person.
   */
  @PostMapping
  public ResponseEntity<PersonDto> createPerson(@RequestBody Person person) {
    Person createdPerson = personService.create(person);

    PersonDto personDto = new PersonDto(createdPerson.getId(), createdPerson.getUsername(),
        createdPerson.getRole());

    return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
  }

}
