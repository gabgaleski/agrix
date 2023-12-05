package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.ebytr.staff.security.Role;

/**
 * DTO Crop.
 */
public record PersonDto(Long id, String username, Role role) {

}
