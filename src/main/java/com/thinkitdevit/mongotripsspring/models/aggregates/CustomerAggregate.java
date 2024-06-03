package com.thinkitdevit.mongotripsspring.models.aggregates;

import com.thinkitdevit.mongotripsspring.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@ToString
public class CustomerAggregate {

    private Customer customer;

    private List<BookingAggregate> bookings;


    @Data
    @AllArgsConstructor
    @Builder
    @ToString
    public static class BookingAggregate{

        private ObjectId id;
        private LocalDateTime date;
        private String status;

        private LocalDate startDate;
        private LocalDate endDate;
        private Double price;
        private String destination;
        private String description;

    }

}
