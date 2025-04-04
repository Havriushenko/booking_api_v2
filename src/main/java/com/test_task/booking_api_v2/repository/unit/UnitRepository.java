package com.test_task.booking_api_v2.repository.unit;

import com.test_task.booking_api_v2.entity.UnitEntity;
import com.test_task.booking_api_v2.repository.unit.jdbc.UnitJdbcRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long>, UnitJdbcRepository {

}
