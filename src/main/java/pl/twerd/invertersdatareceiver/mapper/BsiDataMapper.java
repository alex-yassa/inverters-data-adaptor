package pl.twerd.invertersdatareceiver.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;
import pl.twerd.invertersdatareceiver.dto.LegacyDeviceDataDto;
import pl.twerd.invertersdatareceiver.dto.ConvertedBsiData;
import pl.twerd.invertersdatareceiver.dto.ConvertedDataEntry;
import pl.twerd.invertersdatareceiver.dto.DataEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BsiDataMapper {

    public ConvertedBsiData mapToConvertedBsiData(LegacyDeviceDataDto legacyDeviceDataDto) {
        ConvertedBsiData convertedData = ConvertedBsiData.builder()
                .serialNumber(legacyDeviceDataDto.getSerialNumber())
                .receivingTime(legacyDeviceDataDto.getTimestamp())
                .collectionTime(legacyDeviceDataDto.getCollectionTimestamp())
                .build();

        populateData(convertedData, legacyDeviceDataDto.getCollectionData());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return convertedData;
    }

    private ConvertedDataEntry convertDataEntry(DataEntry dataEntry) {

        float convertedValue = 0f;
        String errorMessage = "";

        try {
            convertedValue = convertValue(dataEntry.getValue(), dataEntry.getUnit());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        ConvertedDataEntry convertedDataEntry = ConvertedDataEntry.builder()
                .name(dataEntry.getName())
                .value(convertedValue)
                .unit(dataEntry.getUnit())
                .errorMessage(errorMessage)
                .build();
        System.out.println(String.format("name: %s oldValue: %d %s -> %f  errorMessage: %s",
                dataEntry.getName(), dataEntry.getValue(), dataEntry.getUnit(), convertedDataEntry.getValue(), convertedDataEntry.getErrorMessage()));
        return convertedDataEntry;
    }

    private Float convertValue(Integer value, String unit) {
        String[] unitParts = unit.split("\\.");
        String unitPart = unitParts[0];
        int decimalPlaces = Integer.parseInt(unitParts[1].substring(1));
        Float resultValue = value / (float) Math.pow(10, decimalPlaces);
        return resultValue;
    }
    
    private void populateData(ConvertedBsiData data, List<DataEntry> collectionData) {
        
        Map<String, Float> dataEntryMap = collectionData.stream()
                .map(this::convertDataEntry)
                .collect(Collectors.toMap(ConvertedDataEntry::getName, convertedDataEntry -> convertedDataEntry.getValue()));

        dataEntryMap.forEach((k, v) -> System.out.println(k + " -> " + v));
        
        data.setVoltage1Mean(dataEntryMap.get("UL1Mean"));
        data.setVoltage1Max(dataEntryMap.get("UL1Max"));
        data.setVoltage1Min(dataEntryMap.get("UL1Min"));
        data.setVoltage2Mean(dataEntryMap.get("UL2Mean"));
        data.setVoltage2Max(dataEntryMap.get("UL2Max"));
        data.setVoltage2Min(dataEntryMap.get("UL2Min"));
        data.setVoltage3Mean(dataEntryMap.get("UL3Mean"));
        data.setVoltage3Max(dataEntryMap.get("UL3Max"));
        data.setVoltage3Min(dataEntryMap.get("UL3Min"));
        data.setVoltage12Mean(dataEntryMap.get("UL12Mean"));
        data.setVoltage12Max(dataEntryMap.get("UL12Max"));
        data.setVoltage12Min(dataEntryMap.get("UL12Min"));
        data.setVoltage23Mean(dataEntryMap.get("UL23Mean"));
        data.setVoltage23Max(dataEntryMap.get("UL23Max"));
        data.setVoltage23Min(dataEntryMap.get("UL23Min"));
        data.setVoltage31Mean(dataEntryMap.get("UL31Mean"));
        data.setVoltage31Max(dataEntryMap.get("UL31Max"));
        data.setVoltage31Min(dataEntryMap.get("UL31Min"));
        data.setCurrent1Mean(dataEntryMap.get("IL1Mean"));
        data.setCurrent1Max(dataEntryMap.get("IL1Max"));
        data.setCurrent1Min(dataEntryMap.get("IL1Min"));
        data.setCurrent2Mean(dataEntryMap.get("IL2Mean"));
        data.setCurrent2Max(dataEntryMap.get("IL2Max"));
        data.setCurrent2Min(dataEntryMap.get("IL2Min"));
        data.setCurrent3Mean(dataEntryMap.get("IL3Mean"));
        data.setCurrent3Max(dataEntryMap.get("IL3Max"));
        data.setCurrent3Min(dataEntryMap.get("IL3Min"));
        data.setActivePower1Inverter(dataEntryMap.get("PL1Inv"));
        data.setActivePower2Inverter(dataEntryMap.get("PL2Inv"));
        data.setActivePower3Inverter(dataEntryMap.get("PL3Inv"));
        data.setReactivePower1Inverter(dataEntryMap.get("QL1Inv"));
        data.setReactivePower2Inverter(dataEntryMap.get("QL2Inv"));
        data.setReactivePower3Inverter(dataEntryMap.get("QL3Inv"));
        data.setActivePower1EnergyGuard(dataEntryMap.get("PL1EG"));
        data.setActivePower2EnergyGuard(dataEntryMap.get("PL2EG"));
        data.setActivePower3EnergyGuard(dataEntryMap.get("PL3EG"));
        data.setReactivePower1EnergyGuard(dataEntryMap.get("QL1EG"));
        data.setReactivePower2EnergyGuard(dataEntryMap.get("QL2EG"));
        data.setReactivePower3EnergyGuard(dataEntryMap.get("QL3EG"));
    }
}
