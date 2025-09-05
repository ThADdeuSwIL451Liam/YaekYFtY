// 代码生成时间: 2025-09-06 04:08:05
package com.example.webfetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class WebContentFetcher {

    private final RestTemplate restTemplate;

    public WebContentFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the content of the given URL using Jsoup.
     * @param url the URL to fetch content from
     * @return the fetched HTML document
     * @throws RestClientException if there is an error with the HTTP request
     */
    public Document fetchContent(String url) throws RestClientException {
        try {
            // Using restTemplate to perform a GET request and get the HTML content as a String
            String htmlContent = restTemplate.getForObject(url, String.class);

            // Parsing the HTML content into a Jsoup Document object
            Document document = Jsoup.parse(htmlContent);

            return document;

        } catch (RestClientException e) {
            // Log the exception and rethrow it to be handled by the caller
            throw new RestClientException("Failed to fetch content from URL: " + url, e);
        }
    }
}
