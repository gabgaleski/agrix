package com.betrybe.agrix.services;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.CropRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */

@Service
public class CropService {
  private CropRepository cropRepository;
  private FertilizerService fertilizerService;

  @Autowired
  public CropService(CropRepository cropRepository, FertilizerService fertilizerService) {
    this.cropRepository = cropRepository;
    this.fertilizerService = fertilizerService;
  }

  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  public Crop getCropById(Long id) {
    return cropRepository.findById(id).orElse(null);
  }

  /**
   * Query Crop service.
   */

  public List<Crop> getByQuery(LocalDate start, LocalDate end) {
    List<Crop> allCrops = cropRepository.findAll();
    List<Crop> cropsFiltred = allCrops.stream()
        .filter(crop -> crop.getHarvestDate().isAfter(start)
            && crop.getHarvestDate().isBefore(end))
        .toList();

    return cropsFiltred;
  }

  /**
   * Post Crop/Fertilizer service.
   */

  public String fertilizeCrop(Long id, Long idFertilizer) {
    Optional<Crop> crop = cropRepository.findById(id);
    Optional<Fertilizer> fertilizer = fertilizerService.getFertilizerById(idFertilizer);
    if (crop.isEmpty()) {
      return "Plantação não encontrada!";
    }
    if (fertilizer.isEmpty()) {
      return "Fertilizante não encontrado!";
    }
    crop.get().getFertilizers().add(fertilizer.get());
    cropRepository.save(crop.get());
    return "Fertilizante e plantação associados com sucesso!";
  }

}