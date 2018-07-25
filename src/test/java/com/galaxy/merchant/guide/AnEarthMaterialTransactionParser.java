package com.galaxy.merchant.guide;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import org.junit.Before;
import org.junit.Test;

/**
 * This class
 *
 * @author Gayathri Thiyagarajan
 */
public class AnEarthMaterialTransactionParser {

    private EarthMaterialTransactionParser earthMaterialTransactionParser;

    private String[] earthMaterialTransactions = {"glob glob Silver is 34 Credits"
                                                , "glob prok Gold is 57800 Credits"
                                                ,"pish pish Iron is 3910 Credits"};
                       //todo handle exceptions and exception tests better
    @Before
    public void initialiseEarthMaterialTransactionParser() {
        HashMap<String, String> interGalacticUnitsFromInput = new HashMap<>();
        interGalacticUnitsFromInput.put("glob", "I");
        interGalacticUnitsFromInput.put("prok", "V");
        interGalacticUnitsFromInput.put("pish", "X");
        interGalacticUnitsFromInput.put("tegj", "L");
        earthMaterialTransactionParser = new EarthMaterialTransactionParser(interGalacticUnitsFromInput);
    }

    @Test
    public void parsesEarthMaterialTransactions() throws InvalidInputFormatException {
        //Given

        //When
        HashMap<String, Double> transactionMap = earthMaterialTransactionParser.parseNotes(asList(earthMaterialTransactions));

        //Then
        assertNotNull(transactionMap);
        assertEquals(3, transactionMap.size());
    }

    @Test
    public void failsToParseEarthMaterialTransactionIfFormatIncorrect() throws InvalidInputFormatException {
        //Given
        earthMaterialTransactions = new String[] {"Quick Brown fox is 0 credits"
                , "Hello world"};

        //When
        HashMap<String, Double> transactionMap = earthMaterialTransactionParser.parseNotes(asList(earthMaterialTransactions));

        //Then
        assertNotNull(transactionMap);
        assertEquals(0, transactionMap.size());
    }

    @Test
    public void computesCreditsForEarthMaterial() throws InvalidInputFormatException {
        //Given

        //When
        HashMap<String, Double> transactionMap = earthMaterialTransactionParser.parseNotes(asList(earthMaterialTransactions));

        //Then

        assertEquals(Double.valueOf(17), transactionMap.get("Silver"));
        assertEquals(Double.valueOf(14450), transactionMap.get("Gold"));
        assertEquals(Double.valueOf(195.5), transactionMap.get("Iron"));
    }

}
