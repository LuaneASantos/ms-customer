package com.example.api.service;

import com.example.api.response.ViaCEPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCEPService {

    @Value("${viacep.api.url}")
    private String viaCepApiUrl;

    public ViaCEPResponse getInformationCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/%s/json/", viaCepApiUrl, cep);
        return restTemplate.getForObject(url, ViaCEPResponse.class);
    }

}
