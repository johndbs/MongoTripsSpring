package com.thinkitdevit.mongotripsspring.mapper;

import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Mapper<T> {

     public abstract Document modelToDocument(T model);

    public abstract T documentToModel(Document document);

    protected LocalDate getLocalDate(Document document, String fieldName){
        return document.getDate(fieldName).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    protected LocalDateTime getLocalDateTime(Document document, String fieldName){
        return document.getDate(fieldName).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
