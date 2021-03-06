package com.galaxy.merchant.guide.parsers;

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
public class AnTransactionNotesParser {

    private TransactionNotesParser transactionNotesParser;

    private String[] earthMaterialTransactions = {"glob glob silver is 34 credits"
                                                , "glob prok gold is 57800 credits"
                                                ,"pish pish iron is 3910 credits"};
    @Before
    public void initialiseEarthMaterialTransactionParser() {
        HashMap<String, String> interGalacticUnitsFromInput = new HashMap<>();
        interGalacticUnitsFromInput.put("glob", "I");
        interGalacticUnitsFromInput.put("prok", "V");
        interGalacticUnitsFromInput.put("pish", "X");
        interGalacticUnitsFromInput.put("tegj", "L");

        transactionNotesParser = new TransactionNotesParser(interGalacticUnitsFromInput);
    }

    @Test
    public void parsesEarthMaterialTransactions() throws InvalidInputFormatException {
        //Given

        //When
        transactionNotesParser.parseNotes(asList(earthMaterialTransactions));

        //Then
        HashMap<String, Double> transactionMap = transactionNotesParser.getCreditsPerEarthMaterial();

        assertNotNull(transactionMap);
        assertEquals(3, transactionMap.size());
    }

    @Test
    public void failsToParseEarthMaterialTransactionIfFormatIncorrect() throws InvalidInputFormatException {
        //Given
        earthMaterialTransactions = new String[] {"Quick Brown fox is 0 credits"
                , "Hello world"};

        //When
        transactionNotesParser.parseNotes(asList(earthMaterialTransactions));

        //Then
        HashMap<String, Double> transactionMap = transactionNotesParser.getCreditsPerEarthMaterial();

        //Then
        assertNotNull(transactionMap);
        assertEquals(0, transactionMap.size());
    }

    @Test
    public void computesCreditsForEarthMaterial() throws InvalidInputFormatException {
        //Given

        //When
        transactionNotesParser.parseNotes(asList(earthMaterialTransactions));

        //Then
        HashMap<String, Double> transactionMap = transactionNotesParser.getCreditsPerEarthMaterial();

        //Then

        assertEquals(Double.valueOf(17), transactionMap.get("silver"));
        assertEquals(Double.valueOf(14450), transactionMap.get("gold"));
        assertEquals(Double.valueOf(195.5), transactionMap.get("iron"));
    }
}
