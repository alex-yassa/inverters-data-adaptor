package pl.twerd.invertersdatareceiver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEntry {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Value")
    private Integer value;
    @JsonProperty("Unit")
    private String unit;
}
