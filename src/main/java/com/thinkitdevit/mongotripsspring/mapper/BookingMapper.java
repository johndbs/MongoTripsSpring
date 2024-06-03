package com.thinkitdevit.mongotripsspring.mapper;


import com.thinkitdevit.mongotripsspring.models.Booking;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookingMapper extends Mapper<Booking> {


    @Override
    public Document modelToDocument(Booking model) {
        Document doc = new Document();

        Optional.ofNullable(model.getId())
                .ifPresent(id -> doc.append("_id", id));

        doc.append("customerId", model.getCustomerId());
        doc.append("tripId", model.getTripId());
        doc.append("date", model.getDate());
        doc.append("status", model.getStatus().getName());

        return doc;
    }

    @Override
    public Booking documentToModel(Document document) {
        return Booking.builder()
                .id(document.getObjectId("_id"))
                .customerId(document.getObjectId("customerId"))
                .tripId(document.getObjectId("tripId"))
                .date(getLocalDateTime(document, "date"))
                .status(Booking.Status.resolve(document.getString("status")))
                .build();
    }
}
