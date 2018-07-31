package com.galaxy.merchant.guide.domain;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.galaxy.merchant.guide.constants.InterGalacticAppConstants;
import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import com.galaxy.merchant.guide.exceptions.NoInputProvidedException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for InterGalactic Interpreter
 *
 * @author Gayathri Thiyagarajan
 */
public class AnInterGalacticInterpreter {

    private InterGalacticInterpreter interGalacticInterpreter = new InterGalacticInterpreter();
    private HashMap<String, String> interGalacticToRomanConversionMap = new HashMap<>();
    private HashMap<String, Double> creditsPerEarthMaterial = new HashMap<>();
    private List<String> bucketOfNotesOnTransactions = new ArrayList<>();
    private List<String> bucketOfNotesOnInterGalacticNumerals = new ArrayList<>();
    private String[] linesOfText = {"glob is I",
                                    "prok is V",
                                    "pish is X",
                                    "tegj is L",
                                    "glob glob Silver is 34 Credits",
                                    "glob prok Gold is 57800 Credits",
                                    "pish pish Iron is 3910 Credits",
                                    "how much is pish tegj glob glob ?",
                                    "how many Credits is glob prok Silver ?",
                                    "how many Credits is glob prok Gold ?",
                                    "how many Credits is glob prok Iron ?",
                                    "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
    };


    @Before
    public void initialiseEntities() {

        bucketOfNotesOnInterGalacticNumerals.add("glob is I");
        bucketOfNotesOnInterGalacticNumerals.add("prok is V");
        bucketOfNotesOnInterGalacticNumerals.add("pish is X");
        bucketOfNotesOnInterGalacticNumerals.add("tegj is L");

        interGalacticToRomanConversionMap.put("glob", "I");
        interGalacticToRomanConversionMap.put("prok", "V");
        interGalacticToRomanConversionMap.put("pish", "X");
        interGalacticToRomanConversionMap.put("tegj", "L");

        bucketOfNotesOnTransactions.add("glob glob Silver is 34 credits");
        bucketOfNotesOnTransactions.add("glob prok Gold is 57800 credits");
        bucketOfNotesOnTransactions.add("pish pish Iron is 3910 credits");

        creditsPerEarthMaterial.put("silver", 17d);
        creditsPerEarthMaterial.put("gold", 14450d);
        creditsPerEarthMaterial.put("iron", 195.5d);


    }

    @Test
    public void canTakeAnInputOfLines() {
        //Given: An InterGalacticInterpreter instance

        //when     //then
        try {
            interGalacticInterpreter.interpret(linesOfText);
        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void cannotInterpretWhenThereAreNoLinesOfNotes() {
        //Given
        String[] linesOfText = {};

        //when
        try {
            interGalacticInterpreter.interpret(linesOfText);

            //then
            fail("It should have thrown exception!");
        } catch (NoInputProvidedException e) {
            assertEquals("No inputs provided to interpret", e.getErrorMessage());
        } catch (InvalidInputFormatException e) {
            fail("Not expecting this exception");
        }

    }

    @Test
    public void cannotInterpretWhenThereAreNoUnitConversionNotes() {
        //Given
        String[] linesOfText = {"glob glob Silver is 34 Credits",
                                "glob prok Gold is 57800 Credits",
                                "pish pish Iron is 3910 Credits",
                                "how much is pish tegj glob glob ?",
                                "how many Credits is glob prok Silver ?",
                                "how many Credits is glob prok Gold ?",
                                "how many Credits is glob prok Iron ?",
                                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"};

        //when
        try {
            interGalacticInterpreter.interpret(linesOfText);

            //then
            fail("It should have thrown exception!");
        } catch (NoInputProvidedException e) {
            assertEquals("No notes with conversion mapping found; cannot proceed further", e.getErrorMessage());
        } catch (InvalidInputFormatException e) {
            fail("Not expecting this exception");
        }

    }

    @Test
    public void cannotInterpretWhenThereAreNoTransactionNotes() {
        //Given
        String[] linesOfText = {"glob is I",
                                "prok is V",
                                "how much is pish tegj glob glob ?",
                                "how many Credits is glob prok Silver ?",
                                "how many Credits is glob prok Gold ?",
                                "how many Credits is glob prok Iron ?",
                                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"};

        //when
        try {
            interGalacticInterpreter.interpret(linesOfText);

            //then
            fail("It should have thrown exception!");
        } catch (NoInputProvidedException e) {
            assertEquals("No notes with previous transactions found; cannot proceed further", e.getErrorMessage());
        } catch (InvalidInputFormatException e) {
            fail("Not expecting this exception");
        }

    }

    @Test
    public void computesInterGalacticUnitConversionMapFromNotes() {
        //Given: NotesOnInterGalacticNumerals

        //when
        try {
            interGalacticInterpreter.interpretInterGalacticToRomanConversionMap(bucketOfNotesOnInterGalacticNumerals);
            HashMap<String, String> expectedInterGalacticToRomanConversionMap
                    = interGalacticInterpreter.getInterGalacticToRomanConversionMap();

            //then
            assertNotNull(expectedInterGalacticToRomanConversionMap);

            assertEquals(4, expectedInterGalacticToRomanConversionMap.size());

            assertEquals("I", expectedInterGalacticToRomanConversionMap.get("glob"));
            assertEquals("V", expectedInterGalacticToRomanConversionMap.get("prok"));
            assertEquals("X", expectedInterGalacticToRomanConversionMap.get("pish"));
            assertEquals("L", expectedInterGalacticToRomanConversionMap.get("tegj"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void computesEarthMaterialCreditsBasedTransactionsFromNotes() {
        //Given interGalacticToRomanConversionMap & NotesOnTransactions

        //when
        try {
            interGalacticInterpreter.interpretNumberOfCreditsPerEarthMaterial(interGalacticToRomanConversionMap, bucketOfNotesOnTransactions);

            HashMap<String, Double> expectedCreditsPerEarthMaterial
                    = interGalacticInterpreter.getCreditsPerEarthMaterial();
            //then
            assertNotNull(expectedCreditsPerEarthMaterial);

            assertEquals(3, expectedCreditsPerEarthMaterial.size());

            assertEquals(Double.valueOf(17), expectedCreditsPerEarthMaterial.get("Silver"));
            assertEquals(Double.valueOf(14450), expectedCreditsPerEarthMaterial.get("Gold"));
            assertEquals(Double.valueOf(195.5), expectedCreditsPerEarthMaterial.get("Iron"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void answersQueriesOnValueOfInterGalacticQuantity() {
        //Given
        String[] linesOfText = {"glob is I",
                                "prok is V",
                                "pish is X",
                                "tegj is L",
                                "glob glob Silver is 34 Credits",
                                "glob prok Gold is 57800 Credits",
                                "pish pish Iron is 3910 Credits",
                                "how much is pish tegj glob glob ?",
                                "how much is glob prok ?",
                                "how much is pish pish ?",
                                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
                                };

        //when
        try {
            HashMap<String, String> QAndA = interGalacticInterpreter.interpret(linesOfText);

            //then
            assertNotNull(QAndA);

            assertEquals(4, QAndA.size());

            assertEquals("pish tegj glob glob is 42", QAndA.get("how much is pish tegj glob glob ?"));
            assertEquals("glob prok is 4", QAndA.get("how much is glob prok ?"));
            assertEquals("pish pish is 20", QAndA.get("how much is pish pish ?"));
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, QAndA.get("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void answersQueriesOnNumberOfCredits() {
        //Given
        String[] linesOfText = {"glob is I",
                                "prok is V",
                                "pish is X",
                                "tegj is L",
                                "glob glob Silver is 34 Credits",
                                "glob prok Gold is 57800 Credits",
                                "pish pish Iron is 3910 Credits",
                                "how many Credits is glob prok Silver ?",
                                "how many Credits is glob prok Gold ?",
                                "how many Credits is glob prok Iron ?",
                                "how many blah is blah dee blah?"
                                };

        //when
        try {
            HashMap<String, String> QAndA = interGalacticInterpreter.interpret(linesOfText);

            //then
            assertNotNull(QAndA);

            assertEquals(4, QAndA.size());

            assertEquals("glob prok silver is 68 credits", QAndA.get("how many Credits is glob prok Silver ?"));
            assertEquals("glob prok gold is 57800 credits", QAndA.get("how many Credits is glob prok Gold ?"));
            assertEquals("glob prok iron is 782 credits", QAndA.get("how many Credits is glob prok Iron ?"));
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, QAndA.get("how many blah is blah dee blah?"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void fullMontyInput() {
        //Given linesOfText

        //when
        try {
            HashMap<String, String> QAndA  = interGalacticInterpreter.interpret(linesOfText);

            //then
            assertNotNull(QAndA);

            assertEquals(5, QAndA.size());

            assertEquals("pish tegj glob glob is 42", QAndA.get("how much is pish tegj glob glob ?"));
            assertEquals("glob prok silver is 68 credits", QAndA.get("how many Credits is glob prok Silver ?"));
            assertEquals("glob prok gold is 57800 credits", QAndA.get("how many Credits is glob prok Gold ?"));
            assertEquals("glob prok iron is 782 credits", QAndA.get("how many Credits is glob prok Iron ?"));
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, QAndA.get("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void fullMontyInputWithMixedCaps() {
        //Given
        String[] linesOfText = {"GLOB is I",
                "PROK is V",
                "PISH is X",
                "TEGJ is L",
                "glob GLOB SILVER is 34 Credits",
                "GLOB prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits",
                "how much is pish tegj glob glob ?",
                "how many Credits is glob prok Silver ?",
                "how many Credits is glob prok Gold ?",
                "how many Credits is glob prok Iron ?",
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
        };

        //when
        try {
            HashMap<String, String> QAndA = interGalacticInterpreter.interpret(linesOfText);

            //then
            assertNotNull(QAndA);

            assertEquals(5, QAndA.size());

            assertEquals("pish tegj glob glob is 42", QAndA.get("how much is pish tegj glob glob ?"));
            assertEquals("glob prok silver is 68 credits", QAndA.get("how many Credits is glob prok Silver ?"));
            assertEquals("glob prok gold is 57800 credits", QAndA.get("how many Credits is glob prok Gold ?"));
            assertEquals("glob prok iron is 782 credits", QAndA.get("how many Credits is glob prok Iron ?"));
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, QAndA.get("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void putsInterGalacticUnitNotesIntoRightBucket() {
        //Given linesOfText

        //when
        try {
            interGalacticInterpreter.interpret(linesOfText);

            //then
            assertNotNull(interGalacticInterpreter.getBucketOfNotesOnInterGalacticNumerals());
            assertEquals(4, interGalacticInterpreter.getBucketOfNotesOnInterGalacticNumerals().size());

            assertEquals(true, asList(linesOfText).contains("glob is I"));
            assertEquals(true, asList(linesOfText).contains("prok is V"));
            assertEquals(true, asList(linesOfText).contains("pish is X"));
            assertEquals(true, asList(linesOfText).contains("tegj is L"));
        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void putsAllQueriesIntoOneBucket() {
        //Given linesOfText

        //when
        try {
            interGalacticInterpreter.classifyLinesOfTextFromNotes(linesOfText);

            //then
            assertNotNull(interGalacticInterpreter.getBucketOfQueries());
            assertEquals(5, interGalacticInterpreter.getBucketOfQueries().size());

            assertEquals(true, asList(linesOfText).contains("how much is pish tegj glob glob ?"));
            assertEquals(true, asList(linesOfText).contains("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));
            assertEquals(true, asList(linesOfText).contains("how many Credits is glob prok Silver ?"));
            assertEquals(true, asList(linesOfText).contains("how many Credits is glob prok Gold ?"));
            assertEquals(true, asList(linesOfText).contains("how many Credits is glob prok Iron ?"));
        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void hasParsedAllLinesIntoOneOfTheBuckets() {
        //Given linesOfText

        //when
        try {
            interGalacticInterpreter.classifyLinesOfTextFromNotes(linesOfText);

            //then
            assertEquals(linesOfText.length, interGalacticInterpreter.getBucketOfNotesOnInterGalacticNumerals().size()
                                        +  interGalacticInterpreter.getBucketOfNotesOnTransactions().size()
                                        +  interGalacticInterpreter.getBucketOfQueries().size());

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }
}
