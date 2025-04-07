package com.test_task.booking_api_v2.controller;

import com.test_task.booking_api_v2.dto.request.BookingRequestDto;
import com.test_task.booking_api_v2.dto.response.BookingResponseDto;
import com.test_task.booking_api_v2.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  @Operation(summary = "Endpoint for create booking.")
  public ResponseEntity<BookingResponseDto> create(@Valid @RequestBody BookingRequestDto dto) {
    BookingResponseDto response = this.bookingService.create(dto);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Cacheable(value = "bookingById", key = "#id")
  @Operation(summary = "Endpoint for get booking by id.")
  public ResponseEntity<BookingResponseDto> findById(@PathVariable(value = "id") Long id) {
    BookingResponseDto response = this.bookingService.findById(id);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/users")
  @Cacheable(value = "bookingByUserId", key = "#userId")
  @Operation(summary = "Endpoint for get all booking by user id.")
  public ResponseEntity<Page<BookingResponseDto>> findAllByUserId(
      @RequestParam Long userId,
      @PageableDefault Pageable pageable
  ) {
    Page<BookingResponseDto> response = this.bookingService.findAllByUser(userId, pageable);

    return ResponseEntity.ok(response);
  }

  @Caching(put = {
      @CachePut(value = "bookingById", key = "#id")
  },
      evict = {
          @CacheEvict(value = "bookingByUserId", allEntries = true)
      })
  @PutMapping(value = "/{id}")
  @Operation(summary = "Endpoint for update booking by id.")
  public ResponseEntity<BookingResponseDto> update(
      @PathVariable(value = "id") Long id,
      @RequestBody BookingRequestDto dto
  ) {
    BookingResponseDto response = this.bookingService.update(id, dto);

    return ResponseEntity.ok(response);
  }

  @Caching(
      evict = {
          @CacheEvict(value = "bookingById", key = "#id"),
          @CacheEvict(value = "bookingByUserId", allEntries = true)
      }
  )
  @DeleteMapping("/{id}")
  @Operation(summary = "Endpoint for delete booking by id.")
  public void delete(@PathVariable(value = "id") Long id) {
    this.bookingService.delete(id);
  }
}
