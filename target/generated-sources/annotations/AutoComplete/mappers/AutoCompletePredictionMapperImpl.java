package AutoComplete.mappers;

import AutoComplete.dto.google.Prediction;
import AutoComplete.dto.google.StructuredFormatting;
import AutoComplete.dto.response.AutoCompletePrediction;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-12-06T17:09:02+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
public class AutoCompletePredictionMapperImpl extends AutoCompletePredictionMapper {

    @Override
    public AutoCompletePrediction map(Prediction prediction) {
        if ( prediction == null ) {
            return null;
        }

        AutoCompletePrediction autoCompletePrediction = new AutoCompletePrediction();

        autoCompletePrediction.setName( prediction.getDescription() );
        autoCompletePrediction.setMainText( predictionStructuredFormattingMainText( prediction ) );
        autoCompletePrediction.setSecondaryText( predictionStructuredFormattingSecondaryText( prediction ) );
        autoCompletePrediction.setPlaceId( prediction.getPlaceId() );

        autoCompletePrediction.setMatches( prediction.getMatchedSubstrings().get(0).getLength() );

        return autoCompletePrediction;
    }

    private String predictionStructuredFormattingMainText(Prediction prediction) {
        if ( prediction == null ) {
            return null;
        }
        StructuredFormatting structuredFormatting = prediction.getStructuredFormatting();
        if ( structuredFormatting == null ) {
            return null;
        }
        String mainText = structuredFormatting.getMainText();
        if ( mainText == null ) {
            return null;
        }
        return mainText;
    }

    private String predictionStructuredFormattingSecondaryText(Prediction prediction) {
        if ( prediction == null ) {
            return null;
        }
        StructuredFormatting structuredFormatting = prediction.getStructuredFormatting();
        if ( structuredFormatting == null ) {
            return null;
        }
        String secondaryText = structuredFormatting.getSecondaryText();
        if ( secondaryText == null ) {
            return null;
        }
        return secondaryText;
    }
}
