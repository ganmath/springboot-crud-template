package com.example.customer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer_id_sequences")
public class CustomerIdSequence {
    @Id
    private String id; // Format: YYYYMMDD
    private long seq;

    public CustomerIdSequence() {}
    public CustomerIdSequence(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public long getSeq() { return seq; }
    public void setSeq(long seq) { this.seq = seq; }
}