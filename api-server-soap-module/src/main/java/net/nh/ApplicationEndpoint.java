package net.nh;

import nh.net.api_soap_server.Country;
import nh.net.api_soap_server.GetCountryRequest;
import nh.net.api_soap_server.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ApplicationEndpoint {

    public static final String NAMESPACE_URI = "http://net.nh/api-soap-server";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        Country country = new Country();
        country.setCapital("London");
        country.setName("England");
        country.setPopulation(60_000_000L);

        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(country);

        return response;
    }

}
