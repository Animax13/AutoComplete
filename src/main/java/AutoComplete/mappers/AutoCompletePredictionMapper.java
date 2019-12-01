package AutoComplete.mappers;

import AutoComplete.dto.google.Prediction;
import AutoComplete.dto.response.AutoCompletePrediction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = Override.class)
public abstract class AutoCompletePredictionMapper {

    @Mapping(source = "description", target = "name")
    @Mapping(target = "matches", expression = "java(prediction.getMatchedSubstrings().get(0).getLength())")
    @Mapping(source = "structuredFormatting.mainText", target = "mainText")
    @Mapping(source = "structuredFormatting.secondaryText", target = "secondaryText")
    public abstract AutoCompletePrediction map (final Prediction prediction);
}
