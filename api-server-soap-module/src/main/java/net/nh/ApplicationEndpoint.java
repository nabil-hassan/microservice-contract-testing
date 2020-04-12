package net.nh;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ApplicationEndpoint {

    public static final String NAMESPACE_URI = "http://net.nh/contract-first-soap-service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public String getCountry(@RequestPayload net.nh.request.GetCountryRequest request) {
        net.nh.request.GetCountryResponse response = new net.nh.request.GetCountryResponse();
        return "UK";
    }

}
