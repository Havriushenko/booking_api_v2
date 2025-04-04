package com.test_task.booking_api_v2.controller;

import com.test_task.booking_api_v2.dto.request.UnitRequestDto;
import com.test_task.booking_api_v2.dto.response.UnitResponseDto;
import com.test_task.booking_api_v2.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/units", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UnitController {

  private final UnitService unitService;

  @PostMapping
  @CacheEvict(value = "units", allEntries = true)
  @Operation(summary = "Endpoint for create new unit.")
  public ResponseEntity<UnitResponseDto> create(@RequestBody UnitRequestDto dto) {
    UnitResponseDto response = this.unitService.create(dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping(value = "/{id}")
  @Caching(put = {
      @CachePut(value = "unitById", key = "#id")
  },
      evict = {
          @CacheEvict(value = "units", allEntries = true)
      })
  @Operation(summary = "Endpoint for update unit.")
  public ResponseEntity<UnitResponseDto> update(@PathVariable(value = "id") Long id, @RequestBody UnitRequestDto dto) {
    UnitResponseDto response = this.unitService.update(id, dto);

    return ResponseEntity.ok(response);
  }

  @Caching(evict = {
      @CacheEvict(value = "unitById", key = "#id"),
      @CacheEvict(value = "units", allEntries = true)
  })
  @DeleteMapping(value = "/{id}")
  @Operation(summary = "Endpoint for delete unit by id.")
  public void delete(@PathVariable(value = "id") Long id) {
    this.unitService.delete(id);
  }

  @GetMapping("/{id}")
  @Cacheable(value = "unitById", key = "#id")
  @Operation(summary = "Endpoint for get unit by id.")
  public ResponseEntity<UnitResponseDto> getById(@PathVariable(value = "id") Long id) {
    UnitResponseDto response = this.unitService.findById(id);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Cacheable(value = "units")
  @Operation(summary = "Endpoint for get all units")
  public ResponseEntity<Page<UnitResponseDto>> findAll(
      @RequestParam(required = false) BigDecimal minPrice,
      @RequestParam(required = false) BigDecimal maxPrice,
      @RequestParam(required = false) Instant checkIn,
      @RequestParam(required = false) Instant checkOut,
      @PageableDefault Pageable pageable
  ) {
    Page<UnitResponseDto> response = this.unitService.findAll(minPrice, maxPrice, checkIn, checkOut, pageable);

    return ResponseEntity.ok(response);
  }
}
