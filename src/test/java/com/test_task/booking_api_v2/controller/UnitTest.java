package com.test_task.booking_api_v2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.test_task.booking_api_v2.BookingApplication;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Tag("integration")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = BookingApplication.class)
class UnitTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getUnit_200_byId() throws Exception {
    this.mockMvc.perform(get("/units/4"))
        .andExpect(status().isOk());
  }

  @Test
  void getUnits_200() throws Exception {
    this.mockMvc.perform(get("/units"))
        .andExpect(status().isOk());
  }

  @Test
  void getUnits_200_filterByPrice() throws Exception {
    this.mockMvc.perform(get("/units?minPrice=700&maxPrice=2000"))
        .andExpect(status().isOk());
  }

  @Test
  void getUnit_404_byId() throws Exception {
    this.mockMvc.perform(get("/units/100000"))
        .andExpect(status().isNotFound());
  }

  @Test
  void postUnits_200() throws Exception {
    this.mockMvc.perform(
            post("/units")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "unitType": "HOME",
                      "numberOfRooms": 3,
                      "tax": 0.15,
                      "basePrice": 1000
                    }
                    """))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("""
            {
              "unitType": "HOME",
              "numberOfRooms": 3,
              "floor": null,
              "basePrice": 1000,
              "tax": 0.15,
              "totalPrice": 1150
            }
            """));
  }

  @Test
  void putUnit_200() throws Exception {
    this.mockMvc.perform(
            put("/units/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "unitType": "HOME",
                      "numberOfRooms": 3,
                      "tax": 0.15,
                      "basePrice": 1000
                    }
                    """))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("""
            {
              "unitType": "HOME",
              "numberOfRooms": 3,
              "floor": null,
              "basePrice": 1000,
              "tax": 0.15,
              "totalPrice": 1150
            }
            """));
  }

  @Test
  void putUnit_404() throws Exception {
    this.mockMvc.perform(
            put("/units/10000000000")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "unitType": "HOME",
                      "numberOfRooms": 3,
                      "tax": 0.15,
                      "basePrice": 1000
                    }
                    """))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteUnit_200() throws Exception {
    this.mockMvc.perform(
            delete("/units/10"))
        .andExpect(status().isOk());
  }
}
