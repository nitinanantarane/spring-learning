package com.nitinrane.learning.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/random")
    public QuoteResponse random() {
        Quote quote = new Quote();
        quote.setId(1L);
        quote.setQuote("This is sample quote");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setStatus("SUCCESS");
        quoteResponse.setValue(quote);

        return quoteResponse;
    }

    @GetMapping("/byId/{id}")
    public QuoteResponse byId(@PathVariable("id") Long id) {

        if (id == 12) {
            return null;
        }

        Quote quote = new Quote();
        quote.setId(1L);
        quote.setQuote("This is sample quote by id");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setStatus("SUCCESS");
        quoteResponse.setValue(quote);

        return quoteResponse;
    }
}
