package com.betrybe.agrix.controllers.dto;

import java.time.LocalDate;

/**
 * DTO Crop.
 */

public record CropDto(Long id, String name, Double plantedArea, Long farmId,
                      LocalDate plantedDate, LocalDate harvestDate) {

}