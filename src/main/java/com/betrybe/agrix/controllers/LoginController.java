package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.AuthenticationDto;
import com.betrybe.agrix.controllers.dto.ResponseDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.services.TokenService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller da rota de login.
 */

@RestController
@RequestMapping("/auth/login")
public class LoginController {

  private AuthenticationManager authenticationManager;
  private PersonService personService;
  private TokenService tokenService;

  /**
   * Constructor.
   */
  @Autowired
  public LoginController(AuthenticationManager authenticationManager,
      PersonService personService, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.personService = personService;
    this.tokenService = tokenService;
  }

  /**
   * Autentica o login.
   */

  @PostMapping
  public ResponseEntity<ResponseDto> login(@RequestBody AuthenticationDto authenticationDto) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(authenticationDto.username(),
            authenticationDto.password());

    Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    Person person = (Person) auth.getPrincipal();

    String token = tokenService.generateToken(person);

    ResponseDto response = new ResponseDto(token);

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }
}
