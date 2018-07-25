package com.galaxy.merchant.guide;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import com.galaxy.merchant.guide.constants.InterGalacticAppConstants;
import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import com.galaxy.merchant.guide.exceptions.NoInputProvidedException;
import org.junit.Test;

/**
 * Test for InterGalactic Decipherer
 *
 * @author Gayathri Thiyagarajan
 */
public class AnInterGalacticDecipherer {

    InterGalacticDecipherer interGalacticDecipherer = new InterGalacticDecipherer();

    @Test
    public void canTakeAnInputOfLines() {
        //Given
        InterGalacticDecipherer interGalacticDecipherer = new InterGalacticDecipherer();
        String[] linesOfText = {"glob is I",
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

        //when     //then
        try {
            interGalacticDecipherer.decipher(linesOfText);
        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void cannotDecipherWhenThereAreNoLinesOfNotes() {
        //Given
        String[] linesOfText = {};

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            fail("It should have thrown exception!");
        } catch (NoInputProvidedException e) {
            assertEquals("No inputs provided to decipher", e.getErrorMessage());
        } catch (InvalidInputFormatException e) {
            fail("Not expecting this exception");
        }

    }

    @Test
    public void cannotDecipherWhenThereAreNoUnitConversionNotes() {
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
            interGalacticDecipherer.decipher(linesOfText);

            //then
            fail("It should have thrown exception!");
        } catch (NoInputProvidedException e) {
            assertEquals("No notes with conversion units found; cannot proceed further", e.getErrorMessage());
        } catch (InvalidInputFormatException e) {
            fail("Not expecting this exception");
        }

    }

    @Test
    public void cannotDecipherWhenThereAreNoTransactionNotes() {
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
            interGalacticDecipherer.decipher(linesOfText);

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
        //Given
        String[] linesOfText = {"glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getInterGalacticConversionUnits());

            assertEquals(4, interGalacticDecipherer.getInterGalacticConversionUnits().size());

            assertEquals("I", interGalacticDecipherer.getInterGalacticConversionUnits().get("glob"));
            assertEquals("V", interGalacticDecipherer.getInterGalacticConversionUnits().get("prok"));
            assertEquals("X", interGalacticDecipherer.getInterGalacticConversionUnits().get("pish"));
            assertEquals("L", interGalacticDecipherer.getInterGalacticConversionUnits().get("tegj"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void computesEarthMaterialCreditsBasedTransactionsFromNotes() {
        //Given
        String[] linesOfText = {"glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getCreditsPerEarthMaterial());

            assertEquals(3, interGalacticDecipherer.getCreditsPerEarthMaterial().size());

            assertEquals(Double.valueOf(17), interGalacticDecipherer.getCreditsPerEarthMaterial().get("silver"));
            assertEquals(Double.valueOf(14450), interGalacticDecipherer.getCreditsPerEarthMaterial().get("gold"));
            assertEquals(Double.valueOf(195.5), interGalacticDecipherer.getCreditsPerEarthMaterial().get("iron"));

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
            interGalacticDecipherer.decipher(linesOfText);

            //then
            HashMap<String, String> QAndA = interGalacticDecipherer.getQueriesAndTheirAnswers();

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
            interGalacticDecipherer.decipher(linesOfText);

            //then
            HashMap<String, String> QAndA = interGalacticDecipherer.getQueriesAndTheirAnswers();

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
        //Given
        String[] linesOfText = {"glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            HashMap<String, String> QAndA = interGalacticDecipherer.getQueriesAndTheirAnswers();

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
            interGalacticDecipherer.decipher(linesOfText);

            //then
            HashMap<String, String> QAndA = interGalacticDecipherer.getQueriesAndTheirAnswers();

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
        //Given
        String[] linesOfText = {"glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getBucketOfNotesOnInterGalacticConversionUnits());
            assertEquals(4, interGalacticDecipherer.getBucketOfNotesOnInterGalacticConversionUnits().size());

            assertEquals(true, asList(linesOfText).contains("glob is I"));
            assertEquals(true, asList(linesOfText).contains("prok is V"));
            assertEquals(true, asList(linesOfText).contains("pish is X"));
            assertEquals(true, asList(linesOfText).contains("tegj is L"));
        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void putsTransactionNotesIntoRightBucket() {
        //Given
        String[] linesOfText = {"glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getBucketOfNotesOnTransactions());
            assertEquals(3, interGalacticDecipherer.getBucketOfNotesOnTransactions().size());

            assertEquals(true, asList(linesOfText).contains("glob glob Silver is 34 Credits"));
            assertEquals(true, asList(linesOfText).contains("glob prok Gold is 57800 Credits"));
            assertEquals(true, asList(linesOfText).contains("pish pish Iron is 3910 Credits"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void putsHowMuchQueriesIntoRightBucket() {
        //Given
        String[] linesOfText = {"glob is I",
                                "prok is V",
                                "pish is X",
                                "tegj is L",
                                "glob glob Silver is 34 Credits",
                                "glob prok Gold is 57800 Credits",
                                "pish pish Iron is 3910 Credits",
                                "how much is pish tegj glob glob ?",
                                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
                                };

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getBucketOfQueries());
            assertEquals(2, interGalacticDecipherer.getBucketOfQueries().size());

            assertEquals(true, asList(linesOfText).contains("how much is pish tegj glob glob ?"));
            assertEquals(true, asList(linesOfText).contains("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void putsHowManyQueriesIntoRightBucket() {
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
                                };

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getBucketOfQueries());
            assertEquals(3, interGalacticDecipherer.getBucketOfQueries().size());

            assertEquals(true, asList(linesOfText).contains("how many Credits is glob prok Silver ?"));
            assertEquals(true, asList(linesOfText).contains("how many Credits is glob prok Gold ?"));
            assertEquals(true, asList(linesOfText).contains("how many Credits is glob prok Iron ?"));
        } catch (Exception e) {
            fail("It should have worked!");
        }
    }

    @Test
    public void putsAllQueriesIntoOneBucket() {
        //Given
        String[] linesOfText = {"glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertNotNull(interGalacticDecipherer.getBucketOfQueries());
            assertEquals(5, interGalacticDecipherer.getBucketOfQueries().size());

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
        //Given
        String[] linesOfText = {
                                "glob is I",
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

        //when
        try {
            interGalacticDecipherer.decipher(linesOfText);

            //then
            assertEquals(linesOfText.length, interGalacticDecipherer.getBucketOfNotesOnInterGalacticConversionUnits().size()
                                        +  interGalacticDecipherer.getBucketOfNotesOnTransactions().size()
                                        +  interGalacticDecipherer.getBucketOfQueries().size());

        } catch (Exception e) {
            fail("It should have worked!");
        }
    }
}
