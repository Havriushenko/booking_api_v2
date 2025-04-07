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
class BookingTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getBookingById_200() throws Exception {
    this.mockMvc.perform(get("/booking/3"))
        .andExpect(status().isOk());
  }

  @Test
  void getBookingByUserId_200() throws Exception {
    this.mockMvc.perform(get("/booking/users?userId=25"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            """
                {
                  "content": [
                    {
                      "id": 8,
                      "unitId": 11,
                      "status": "PENDING",
                      "checkIn": "2026-07-16T21:19:51Z",
                      "checkOut": "2026-05-25T21:19:51Z",
                      "createAt": "2025-04-01T21:19:51Z"
                    },
                    {
                      "id": 7,
                      "unitId": 2,
                      "status": "PENDING",
                      "checkIn": "2026-07-16T18:19:51Z",
                      "checkOut": "2026-07-20T18:19:51Z",
                      "createAt": "2025-04-01T21:19:51Z"
                    }
                  ],
                  "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": {
                      "empty": true,
                      "sorted": false,
                      "unsorted": true
                    },
                    "offset": 0,
                    "paged": true,
                    "unpaged": false
                  },
                  "last": true,
                  "totalElements": 2,
                  "totalPages": 1,
                  "first": true,
                  "size": 10,
                  "number": 0,
                  "sort": {
                    "empty": true,
                    "sorted": false,
                    "unsorted": true
                  },
                  "numberOfElements": 2,
                  "empty": false
                }
                                """
        ));
  }

  @Test
  void getBookingById_404() throws Exception {
    this.mockMvc.perform(get("/booking/200000"))
        .andExpect(status().isNotFound());
  }

  @Test
  void postBooking_200() throws Exception {
    this.mockMvc.perform(post("/booking")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "unitId": 11,
                  "userId": 200,
                  "checkIn": "2025-09-01T09:54:51.102Z",
                  "checkOut": "2025-09-10T09:54:51.102Z"
                }
                """))
        .andExpect(status().isOk());
  }

  @Test
  void putBooking_200() throws Exception {
    this.mockMvc.perform(put("/booking/5")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "unitId": 6,
                  "userId": 55,
                  "checkIn": "2025-09-01T09:54:51.102Z",
                  "checkOut": "2025-09-10T09:54:51.102Z"
                }
                """))
        .andExpect(status().isOk());
  }

  @Test
  void putBooking_400_userIdValidation() throws Exception {
    this.mockMvc.perform(put("/booking/5")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "unitId": 6,
                  "userId": 10000,
                  "checkIn": "2025-09-01T09:54:51.102Z",
                  "checkOut": "2025-09-10T09:54:51.102Z"
                }
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void putBooking_404() throws Exception {
    this.mockMvc.perform(put("/booking/100000")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "unitId": 6,
                  "userId": 55,
                  "checkIn": "2025-09-01T09:54:51.102Z",
                  "checkOut": "2025-09-10T09:54:51.102Z"
                }
                """))
        .andExpect(status().isNotFound());
  }

  @Test
  void putBookingByNotExistUnit_404() throws Exception {
    this.mockMvc.perform(put("/booking/5")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "unitId": 10000000,
                  "userId": 55,
                  "checkIn": "2025-09-01T09:54:51.102Z",
                  "checkOut": "2025-09-10T09:54:51.102Z"
                }
                """))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteBookingById_200() throws Exception {
    this.mockMvc.perform(delete("/booking/6"))
        .andExpect(status().isOk());
  }
}
