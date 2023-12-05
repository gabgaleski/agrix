package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import com.betrybe.agrix.services.FertilizerService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint Crops.
 */

@RestController
@RequestMapping("/crops")
public class CropController {
  private final CropService cropService;
  private final FertilizerService fertilizerService;

  /**
   * Constructor.
   */

  @Autowired
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Metodo Get.
   */

  @GetMapping
  public ResponseEntity<List<CropDto>> getAllCrops() {
    List<Crop> cropsList = cropService.getAllCrops();
    List<CropDto> dtoList = new ArrayList<>();
    for (Crop crop : cropsList) {
      CropDto newCrop = new CropDto(crop.getId(), crop.getName(),
          crop.getPlantedArea(), crop.getFarm().getId(),
          crop.getPlantedDate(), crop.getHarvestDate());
      dtoList.add(newCrop);
    }
    return ResponseEntity.ok(dtoList);
  }

  /**
   * Query Get.
   */

  @GetMapping("/search")
  public ResponseEntity<?> getByQuery(@RequestParam String start, @RequestParam String end) {
    LocalDate startDate = LocalDate.parse(start);
    LocalDate endDate = LocalDate.parse(end);
    List<Crop> cropsList = cropService.getByQuery(startDate, endDate);
    List<CropDto> dtoList = new ArrayList<>();
    for (Crop crop : cropsList) {
      CropDto newCrop = new CropDto(crop.getId(), crop.getName(),
          crop.getPlantedArea(), crop.getFarm().getId(),
          crop.getPlantedDate(), crop.getHarvestDate());
      dtoList.add(newCrop);
    }
    return ResponseEntity.status(HttpStatus.OK).body(dtoList);

  }

  /**
   * Query Get.
   */

  @GetMapping("{id}/fertilizers")
  public ResponseEntity<?> getFertilizersByCropId(@PathVariable Long id) {
    Crop crop = cropService.getCropById(id);
    if (crop == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }
    List<Fertilizer> fertilizers = crop.getFertilizers();
    return ResponseEntity.status(HttpStatus.OK).body(fertilizers);
  }

  /**
   * Metodo Get para crops/id.
   */

  @GetMapping("/{id}")
  public ResponseEntity<?> getCropById(@PathVariable Long id) {
    Crop crop = cropService.getCropById(id);
    if (crop == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }

    CropDto newCrop = new CropDto(crop.getId(), crop.getName(),
        crop.getPlantedArea(), crop.getFarm().getId(),
        crop.getPlantedDate(), crop.getHarvestDate());
    return ResponseEntity.ok(newCrop);
  }

  /**
   * Post Crop/Fertilizer controller.
   */

  @PostMapping("/{id}/fertilizers/{fertilizerId}")
  public ResponseEntity<?> fertilizeCrop(@PathVariable Long id, @PathVariable Long fertilizerId) {
    String result = cropService.fertilizeCrop(id, fertilizerId);
    if (result.equals("Plantação não encontrada!")) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
    if (result.equals("Fertilizante não encontrado!")) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }


}