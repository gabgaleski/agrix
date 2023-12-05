package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Farm controller.
 */

@RestController
@RequestMapping("/farms")
public class FarmController {
  private final FarmService farmService;

  /**
   * Instantiates a new Farm controller.
   *
   * @param farmService the farm service
   */

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  @PostMapping
  public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
    Farm newFarm = farmService.insertFarm(farm);
    return ResponseEntity.status(HttpStatus.CREATED).body(newFarm);
  }

  @GetMapping
  public ResponseEntity<List<Farm>> getAllFarms() {
    List<Farm> farmsList = farmService.getAllFarms();
    return ResponseEntity.ok(farmsList);
  }

  /**
   * Gets farm by id.
   */

  @GetMapping("/{id}")
  public ResponseEntity<?> getFarmById(@PathVariable Long id) {
    Optional<Farm> farm = farmService.getFarmById(id);
    if (farm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }
    return ResponseEntity.ok(farm);
  }

  /**
   * Rota Post.
   */

  @PostMapping("/{farmId}/crops")
  public ResponseEntity<?> createCrop(@PathVariable Long farmId, @RequestBody Crop crop) {
    Optional<Farm> farm = farmService.getFarmById(farmId);
    if (farm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }
    crop.setFarm(farm.get());
    Crop newCrop = farmService.insertCrop(crop);
    CropDto cropDto = new CropDto(newCrop.getId(), newCrop.getName(), newCrop.getPlantedArea(),
        newCrop.getFarm().getId(), newCrop.getPlantedDate(), newCrop.getHarvestDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(cropDto);
  }

  /**
   * Rota Get.
   */

  @GetMapping("/{farmId}/crops")
  public ResponseEntity<?> getCrops(@PathVariable Long farmId) {
    Optional<Farm> farm = farmService.getFarmById(farmId);
    if (farm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }
    List<Crop> crops = farm.get().getCrops();
    List<CropDto> dtoList = new ArrayList<>();
    for (Crop crop : crops) {
      CropDto newCrop = new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
          crop.getFarm().getId(), crop.getPlantedDate(), crop.getHarvestDate());
      dtoList.add(newCrop);
    }
    return ResponseEntity.ok(dtoList);
  }

}
