package com.thinkitdevit.mongotripsspring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "trips")
public class Trip {

    @Id
    private ObjectId id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
    private Integer availableSeats;
    private Integer bookedSeats;
    private String description;

}
