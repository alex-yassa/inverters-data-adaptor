package pl.twerd.invertersdatareceiver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedBsiData {

    private String serialNumber;
    private Long receivingTime;
    private Long collectionTime;

    private Float voltage1Mean;
    private Float voltage1Max;
    private Float voltage1Min;
    private Float voltage2Mean;
    private Float voltage2Max;
    private Float voltage2Min;
    private Float voltage3Mean;
    private Float voltage3Max;
    private Float voltage3Min;
    private Float voltage12Mean;
    private Float voltage12Max;
    private Float voltage12Min;
    private Float voltage23Mean;
    private Float voltage23Max;
    private Float voltage23Min;
    private Float voltage31Mean;
    private Float voltage31Max;
    private Float voltage31Min;
    private Float current1Mean;
    private Float current1Max;
    private Float current1Min;
    private Float current2Mean;
    private Float current2Max;
    private Float current2Min;
    private Float current3Mean;
    private Float current3Max;
    private Float current3Min;
    private Float activePower1Inverter;
    private Float activePower2Inverter;
    private Float activePower3Inverter;
    private Float reactivePower1Inverter;
    private Float reactivePower2Inverter;
    private Float reactivePower3Inverter;
    private Float activePower1EnergyGuard;
    private Float activePower2EnergyGuard;
    private Float activePower3EnergyGuard;
    private Float reactivePower1EnergyGuard;
    private Float reactivePower2EnergyGuard;
    private Float reactivePower3EnergyGuard;
}
