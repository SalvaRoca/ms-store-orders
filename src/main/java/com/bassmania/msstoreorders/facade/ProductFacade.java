package com.bassmania.msstoreorders.facade;

import com.bassmania.msstoreorders.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductFacade {
    @Value("${getProduct.url}")
    private String getProductUrl;

    private final RestTemplate restTemplate;

    public Product getProduct(String productRef) {

        try {
            String url = String.format(getProductUrl, productRef);
            log.info("Getting product with productRef {}. Request to {}", productRef, url);
            return restTemplate.getForObject(url, Product.class);
        } catch (HttpClientErrorException e) {
            log.error("Client Error: {}, Product with productRef {}", e.getStatusCode(), productRef);
            return null;
        } catch (HttpServerErrorException e) {
            log.error("Server Error: {}, Product with productRef {}", e.getStatusCode(), productRef);
            return null;
        } catch (Exception e) {
            log.error("Error: {}, Product with productRef {}", e.getMessage(), productRef);
            return null;
        }
    }
}
