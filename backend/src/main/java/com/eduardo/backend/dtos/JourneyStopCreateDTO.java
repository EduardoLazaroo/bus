package com.eduardo.backend.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JourneyStopCreateDTO {
    @JsonProperty("order")
    @JsonAlias({"seqOrder", "seq_order"})
    private Integer seqOrder;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
