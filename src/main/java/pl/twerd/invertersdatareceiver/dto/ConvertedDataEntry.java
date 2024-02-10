package pl.twerd.invertersdatareceiver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvertedDataEntry {

    private String name;
    private Float value;
    private String unit;
    private String errorMessage;
}
