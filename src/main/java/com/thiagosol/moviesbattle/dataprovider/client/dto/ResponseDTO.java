package com.thiagosol.moviesbattle.dataprovider.client.dto;

import java.util.List;

public class ResponseDTO<T>{
    private Integer page;
    private Integer entries;
    private String next;
    List<T> results;

    public List<T> getResults() {
        return results;
    }
}