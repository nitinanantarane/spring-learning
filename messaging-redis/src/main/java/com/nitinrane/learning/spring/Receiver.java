package com.nitinrane.learning.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class Receiver {
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);
    private static final AtomicLong counter = new AtomicLong();

    public void receiveMessage(String message) {
        log.info("Receive message: {}", message);
        counter.incrementAndGet();
    }

    public Long getCount() {
        return counter.get();
    }
}
