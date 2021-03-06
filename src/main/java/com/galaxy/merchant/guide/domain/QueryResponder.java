package com.galaxy.merchant.guide.domain;

import static com.galaxy.merchant.guide.constants.InterGalacticAppConstants.EARTH_MATERIALS_PART;
import static org.apache.commons.lang3.StringUtils.replacePattern;
import static org.apache.commons.lang3.StringUtils.substringBetween;
import static org.apache.commons.lang3.StringUtils.trim;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.galaxy.merchant.guide.constants.InterGalacticAppConstants;
import com.galaxy.merchant.guide.converters.InterGalacticToRomanConverter;
import com.galaxy.merchant.guide.converters.RomanToArabicConverter;
import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import com.galaxy.merchant.guide.exceptions.InvalidQueryException;
import org.apache.commons.lang3.StringUtils;

/**
 * This class constructs response to queries passed to it by InterGalacticInterpreter
 *
 * @author Gayathri Thiyagarajan
 */
class QueryResponder {

    private String PATTERN_OF_TRANSACTION_PART ;

    //Map of earth material transactions e.g. Silver-17f
    private HashMap<String, Double> creditsForEarthMaterials = new HashMap<>();

    //Map of interGalactic numeral and their equivalent roman numeral e.g. glob-I
    private HashMap<String, String> interGalacticToRomanConversionMap = new HashMap<>();

    private RomanToArabicConverter romanToArabicConverter;

    private InterGalacticToRomanConverter interGalacticToRomanConverter;

    private static final String QUESTION_MARK = "?";

    private QueryResponder(HashMap<String, Double> creditsForEarthMaterials, HashMap<String, String> interGalacticToRomanConversionMap) {
        this.creditsForEarthMaterials = creditsForEarthMaterials;
        this.interGalacticToRomanConversionMap = interGalacticToRomanConversionMap;

        interGalacticToRomanConverter = new InterGalacticToRomanConverter(interGalacticToRomanConversionMap);
        romanToArabicConverter = new RomanToArabicConverter();

        Set<String> interGalacticUnits = interGalacticToRomanConversionMap.keySet();
        PATTERN_OF_TRANSACTION_PART = interGalacticUnits.stream().map(e -> e).collect(Collectors.joining("|"));

    }

    /**
     * Answers query on how much an inter galactic amount is in equivalent earth amount
     *
     * @param query E.g. how much is pish tegj glob glob ?
     * @return galacticAmount as numeral in answer format "pish tegj glob glob is 42"
     * @throws InvalidQueryException If Query is unrecognised
     */
    String answerQueryOnInterGalacticQuantity(String query) throws InvalidQueryException {

        String HOW_MUCH_QUERY_MASK = "how much is";
        String galacticAmount = trim(substringBetween(query, HOW_MUCH_QUERY_MASK, QUESTION_MARK));
        Integer numericValue;

        numericValue = calculateNumericValue(galacticAmount);
        return galacticAmount + " is " + numericValue;
    }

    /**
     * Answers query on how many credits is a particular transaction
     *
     * @param query E.g. how many Credits is glob prok Silver ?
     * @return numberOfCredits for the transaction in answer format  "glob prok Silver is 68 Credits"
     * @throws InvalidQueryException If Query is unrecognised
     */
    String answerQueryOnCreditsOfATransaction(String query) throws InvalidQueryException {

        String HOW_MANY_CREDITS_QUERY_MASK = "how many credits is";

        String transactionInTheQuery = trim(substringBetween(query, HOW_MANY_CREDITS_QUERY_MASK, QUESTION_MARK));
        Integer numberOfCredits = calculateNumberOfCredits(transactionInTheQuery);

        if(numberOfCredits == 0f) {
            throw new InvalidQueryException(InterGalacticAppConstants.DEFAULT_ANSWER);
        }

        return transactionInTheQuery + " is " + numberOfCredits + " credits";
    }

    /**
     * Given the galactic amount calculates the numerical value
     *
     * @param galacticQuantity galactic quantity or quantity
     * @return numeric value
     * @throws InvalidQueryException
     */
    Integer calculateNumericValue(String galacticQuantity) throws InvalidQueryException {
        Integer numericValue;
        try {
            String romanEquivalentOfGalacticAmount = interGalacticToRomanConverter.convertInterGalacticPhraseIntoRomanSegment(galacticQuantity);
            numericValue = romanToArabicConverter.convertRomanSegmentIntoNumericValue(romanEquivalentOfGalacticAmount);
        } catch (InvalidInputFormatException e) {
            throw new InvalidQueryException(InterGalacticAppConstants.DEFAULT_ANSWER);
        }
        return numericValue;
    }

    /**
     * Given the transaction part of a query, this method calculates the number of credits
     *
     * @param transactionInTheQuery Transaction
     * @return Number of credits
     */
    Integer calculateNumberOfCredits(String transactionInTheQuery) {
        //Get the numerical value of the intergalactic transaction
        Integer numberOfCredits = 0;

        try {
            //Extract the transaction part and the material in the transaction
            if (StringUtils.isNotEmpty(transactionInTheQuery)) {
                String transactionPart = trim(replacePattern(transactionInTheQuery, EARTH_MATERIALS_PART, StringUtils.EMPTY));
                String earthMaterial = trim(replacePattern(transactionInTheQuery, PATTERN_OF_TRANSACTION_PART, StringUtils.EMPTY));

                String romanEquivalentOfTheTransaction = interGalacticToRomanConverter.convertInterGalacticPhraseIntoRomanSegment(transactionPart);
                Integer quantityOfEarthMaterial = romanToArabicConverter.convertRomanSegmentIntoNumericValue(romanEquivalentOfTheTransaction);

                if (creditsForEarthMaterials.containsKey(earthMaterial)) {
                    numberOfCredits = (int) Math.round(creditsForEarthMaterials.get(earthMaterial) * quantityOfEarthMaterial);
                }
            }
        } catch (InvalidInputFormatException e) {
            //I have no idea what you are talking about
        }
        return numberOfCredits;
    }

    HashMap<String, Double> getCreditsForEarthMaterials() {
        return creditsForEarthMaterials;
    }

    HashMap<String, String> getInterGalacticToRomanConversionMap() {
        return interGalacticToRomanConversionMap;
    }

    static class QueryResponderBuilder {

        private HashMap<String, Double> creditsForEarthMaterials = new HashMap<>();
        private HashMap<String, String> interGalacticToRomanConversionMap = new HashMap<>();

        QueryResponder.QueryResponderBuilder setCreditsForEarthMaterials(HashMap<String, Double> creditsForEarthMaterials) {
            this.creditsForEarthMaterials = creditsForEarthMaterials;
            return this;
        }

        QueryResponder.QueryResponderBuilder setInterGalacticToRomanConversionMap(HashMap<String, String> interGalacticToRomanConversionMap) {
            this.interGalacticToRomanConversionMap = interGalacticToRomanConversionMap;
            return this;
        }

        QueryResponder createQueryResponder() {
            return new QueryResponder(creditsForEarthMaterials, interGalacticToRomanConversionMap);
        }
    }
}
