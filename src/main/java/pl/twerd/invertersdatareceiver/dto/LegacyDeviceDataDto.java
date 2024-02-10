package pl.twerd.invertersdatareceiver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegacyDeviceDataDto {

    @JsonProperty("sn")
    private String serialNumber;
    @JsonProperty("stamp_time")
    private Long timestamp;
    @JsonProperty("collection_time")
    private Long collectionTimestamp;
    private String device;
    @JsonProperty("collection_data")
    private List<DataEntry> collectionData;
    @JsonProperty("stamp_hash")
    private String hashStamp;
}
