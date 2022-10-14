package com.thiagosol.moviesbattle.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class SetupMocks {


    public static void configGetMock(WireMockServer mockService, String url, HttpStatus httpStatus, String pathPayload) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlMatching(url))
            .willReturn(WireMock.aResponse()
                .withStatus(httpStatus.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    copyToString(
                        SetupMocks.class.getClassLoader().getResourceAsStream(pathPayload),
                        defaultCharset()))));
    }

    public static void configGetMockWithReplaceResponse(WireMockServer mockService, String url, HttpStatus httpStatus, String pathPayload, HashMap<String, String> mapReplace) throws IOException {
        var response = copyToString(
                SetupMocks.class.getClassLoader().getResourceAsStream(pathPayload),
                defaultCharset());

        for (var key : mapReplace.keySet()){
            response = response.replace("{{" + key + "}}", mapReplace.get(key));
        }

        mockService.stubFor(WireMock.get(WireMock.urlMatching(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(httpStatus.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(response)));
    }

}