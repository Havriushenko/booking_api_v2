package com.test_task.booking_api_v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.test_task.booking_api_v2.BookingApplication;
import com.test_task.booking_api_v2.dto.request.UnitRequestDto;
import com.test_task.booking_api_v2.dto.response.UnitResponseDto;
import com.test_task.booking_api_v2.entity.UnitType;
import com.test_task.booking_api_v2.exception.UnitNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@Tag("integration")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = BookingApplication.class)
class UnitServiceTest {

  @Autowired
  private UnitService unitService;

  @Test
  void when_findUnitById_expect_successfulFind() {
    assertThat(this.unitService.findById(3L))
        .isNotNull()
        .extracting(UnitResponseDto::id)
        .isEqualTo(3L);
  }

  @Test
  void when_findUnitById_expect_throwExceptionNotFound() {
    assertThatThrownBy(() -> this.unitService.findById(-1L))
        .isNotNull()
        .isInstanceOf(UnitNotFoundException.class);
  }

  @Test
  void when_findAll_expect_successfulFindEntities() {
    Pageable pageable = PageRequest.of(0, 10);
    assertThat(this.unitService.findAll(null, null, null, null, pageable))
        .isNotNull()
        .isNotEmpty()
        .hasSize(10);

  }

  @Test
  void when_createNewEntity_expect_successfulSave() {
    UnitRequestDto dto = this.buildTestRequestDto();

    assertThat(this.unitService.create(dto)).isNotNull()
        .extracting(UnitResponseDto::id)
        .isNotNull();
  }

  @Test
  void when_updateEntity_expect_successfulUpdate() {
    Long id = 6L;
    UnitRequestDto dto = this.buildTestRequestDto();

    UnitResponseDto get = unitService.findById(id);

    assertThat(this.unitService.update(id, dto))
        .isNotNull()
        .isNotEqualTo(get);
  }

  @Test
  void when_deleteById_expect_successfulDelete() {
    Long id = 8L;

    this.unitService.delete(id);

    assertThatThrownBy(() -> this.unitService.findById(id))
        .isNotNull()
        .isInstanceOf(UnitNotFoundException.class);

  }

  private UnitRequestDto buildTestRequestDto() {
    return UnitRequestDto.builder()
        .unitType(UnitType.FLAT)
        .numberOfRooms(1)
        .floor(7)
        .tax(BigDecimal.valueOf(0.15))
        .basePrice(BigDecimal.valueOf(400))
        .build();
  }
}
