package com.test_task.booking_api_v2.entity;

import java.util.Arrays;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum UnitType {

  HOME("home"),
  FLAT("flat"),
  APARTMENT("apartment");

  UnitType(String code) {
    this.code = code;
  }

  private String code;

  public static UnitType getUnitTypeByCode(String code) {
    return Arrays.stream(UnitType.values())
        .filter(unitType -> StringUtils.equalsIgnoreCase(unitType.getCode(), code))
        .findFirst()
        .orElse(null);
  }
}
