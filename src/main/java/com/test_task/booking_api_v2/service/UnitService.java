package com.test_task.booking_api_v2.service;

import com.test_task.booking_api_v2.dto.request.UnitRequestDto;
import com.test_task.booking_api_v2.dto.response.UnitResponseDto;
import com.test_task.booking_api_v2.entity.UnitEntity;
import com.test_task.booking_api_v2.exception.UnitNotFoundException;
import com.test_task.booking_api_v2.repository.unit.UnitRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnitService {

  private static final BigDecimal BOOKING_TAX_DEFAULT_VALUE = BigDecimal.valueOf(0.15);
  private final UnitRepository unitRepository;

  @Transactional(rollbackFor = Exception.class)
  public UnitResponseDto create(UnitRequestDto dto) {
    BigDecimal tax = Objects.isNull(dto.tax()) ? BOOKING_TAX_DEFAULT_VALUE : dto.tax();
    UnitEntity entity = UnitEntity.builder()
        .unitType(dto.unitType())
        .numberOfRooms(dto.numberOfRooms())
        .floor(dto.floor())
        .tax(tax)
        .basePrice(dto.basePrice())
        .totalPrice(dto.basePrice().add(tax.multiply(dto.basePrice())))
        .build();

    this.unitRepository.save(entity);
    return this.convertEntityToResponseDto(entity);
  }

  @Transactional(rollbackFor = Exception.class)
  public UnitResponseDto update(Long id, UnitRequestDto dto) {
    UnitEntity entity = this.getEntity(id);
    entity.setUnitType(dto.unitType());
    entity.setNumberOfRooms(dto.numberOfRooms());
    entity.setFloor(dto.floor());
    entity.setBasePrice(dto.basePrice());
    if (Objects.nonNull(dto.tax())) {
      entity.setTax(dto.tax());
    }
    entity.setTotalPrice(dto.basePrice().add(entity.getTax().multiply(dto.basePrice())));

    this.unitRepository.save(entity);
    return this.convertEntityToResponseDto(entity);
  }

  public void delete(Long id) {
    this.unitRepository.deleteById(id);
  }

  public UnitResponseDto findById(Long id) {
    UnitEntity entity = this.getEntity(id);

    return this.convertEntityToResponseDto(entity);
  }

  public Page<UnitResponseDto> findAll(
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Instant checkIn,
      Instant checkOut,
      Pageable pageable
  ) {
    return this.unitRepository.findAll(minPrice, maxPrice, checkIn, checkOut, pageable);
  }

  public UnitEntity getEntity(Long id) {
    return this.unitRepository.findById(id).orElseThrow(
        () -> new UnitNotFoundException("Unit %s does not exist!".formatted(id)));
  }

  private UnitResponseDto convertEntityToResponseDto(UnitEntity entity) {
    return UnitResponseDto.builder()
        .id(entity.getId())
        .unitType(entity.getUnitType())
        .numberOfRooms(entity.getNumberOfRooms())
        .floor(entity.getFloor())
        .basePrice(entity.getBasePrice())
        .tax(entity.getTax())
        .totalPrice(entity.getTotalPrice())
        .build();
  }
}
