package com.bekzataitymov.WebMVCConfig;

import com.bekzataitymov.ExceptionHandler.CityNotFoundException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("Handling error: " + response.getStatusCode());
        if (response.getStatusCode().is4xxClientError()) {
            throw new CityNotFoundException("City not found");
        }if(response.getStatusCode().is5xxServerError()){
            throw new CityNotFoundException("Server error");
        }
    }
}
