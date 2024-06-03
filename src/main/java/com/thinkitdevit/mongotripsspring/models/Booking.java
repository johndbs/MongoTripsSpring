package com.thinkitdevit.mongotripsspring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bookings")
public class Booking {

    @Id
    private ObjectId id;
    private ObjectId customerId;
    private ObjectId tripId;
    private LocalDateTime date;
    private Status status;


    public enum Status {
        PENDING("PENDING"),
        CONFIRMED("CONFIRMED"),
        CANCELLED("CANCELLED");

        private String name;


        private static final Map<String, Status> mappedByName =  Arrays.stream(Status.values())
                        .collect(Collectors.toMap(Status::getName, Function.identity()));


        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Status resolve(String label){
            return mappedByName.get(label);
        }

    }

}
