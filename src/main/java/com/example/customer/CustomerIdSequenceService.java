package com.example.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerIdSequenceService {

    @Autowired
    private MongoOperations mongoOperations;

    public long getNextSequence() {
        try {
            String today = getTodayString();
            Query query = new Query(Criteria.where("_id").is(today));
            Update update = new Update().inc("seq", 1);
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
            CustomerIdSequence counter = mongoOperations.findAndModify(query, update, options, CustomerIdSequence.class);
            return counter.getSeq();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate next sequence", e);
        }
    }

    public String getTodayString() {
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}