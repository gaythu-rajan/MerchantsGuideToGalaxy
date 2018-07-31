package com.galaxy.merchant.guide.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import com.galaxy.merchant.guide.constants.InterGalacticAppConstants;
import com.galaxy.merchant.guide.exceptions.InvalidQueryException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for QueryResponder
 *
 * @author Gayathri Thiyagarajan
 */

public class AQueryResponder {

    private static HashMap<String, String> interGalacticUnitsFromInput = new HashMap<>();
    private static HashMap<String, Double> earthMaterialTransactionsFromInput = new HashMap<>();

    private QueryResponder queryResponder;

    @BeforeClass
    public static void initialiseQueryResponderMaps() {

        interGalacticUnitsFromInput.put("glob", "I");
        interGalacticUnitsFromInput.put("prok", "V");
        interGalacticUnitsFromInput.put("pish", "X");
        interGalacticUnitsFromInput.put("tegj", "L");

        earthMaterialTransactionsFromInput.put("silver", 17d);
        earthMaterialTransactionsFromInput.put("gold", 14450d);
        earthMaterialTransactionsFromInput.put("iron", 195.5d);

    }

    @Before
    public void successfullyInitialises() {
        queryResponder
                = new QueryResponder.QueryResponderBuilder()
                    .setInterGalacticToRomanConversionMap(interGalacticUnitsFromInput)
                    .setCreditsForEarthMaterials(earthMaterialTransactionsFromInput).createQueryResponder();
    }

    @Test
    public void constructsQueryResponderSuccessfully() {
        QueryResponder queryResponder = new QueryResponder.QueryResponderBuilder()
                .createQueryResponder();

        assertNotNull(queryResponder.getCreditsForEarthMaterials());
        assertNotNull(queryResponder.getInterGalacticToRomanConversionMap());
    }

    @Test
    public void calculatesNumericValue() {
        //Given
        String interGalacticQuantity = "pish tegj glob glob";

        //verify
        Integer expectedAnswer = 42;

        //when
        Integer answer = 0;
        try {
            answer = queryResponder.calculateNumericValue(interGalacticQuantity);
        } catch (InvalidQueryException e) {
            fail("Should not have thrown exception");
        }
        //then
        assertEquals(expectedAnswer, answer);

        //Given
        interGalacticQuantity = "glob prok";

        //verify
        expectedAnswer = 4;

        //when
        answer = 0;

        try {
            answer = queryResponder.calculateNumericValue(interGalacticQuantity);
        } catch (InvalidQueryException e) {
            fail("Should not have thrown exception");
        }
        //then
        assertEquals(expectedAnswer, answer);
    }

    @Test
    public void answersHowMuchAnInterGalacticAmountIsInNumericalValue() {
        //Given
        String query = "how much is pish tegj glob glob ?";

        //verify
        String expectedAnswer = "pish tegj glob glob is 42";

        //when
        String answer = null;
        try {
            answer = queryResponder.answerQueryOnInterGalacticQuantity(query);
        } catch (InvalidQueryException e) {
            fail("Should not have thrown exception");
        }

        //then
        assertEquals(expectedAnswer, answer);

        query = "how much is glob prok ?";

        //verify
        expectedAnswer = "glob prok is 4";

        //when
        try {
            answer = queryResponder.answerQueryOnInterGalacticQuantity(query);
        } catch (InvalidQueryException e) {
            fail("Should not have thrown exception");
        }

        //then
        assertEquals(expectedAnswer, answer);
    }

    @Test
    public void cannotAnswerIncorrectlyFormedInterGalacticAmountQuery() {
        //Given
        String query = "how much is wood could a woodchuck chuck if a woodchuck could chuck wood ?";

        //when
        try {
            queryResponder.answerQueryOnInterGalacticQuantity(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void cannotAnswerRandomSentencesInPlaceOfInterGalacticAmountQuery() {
        //Given
        String query = "The quick brown fox jumps over the lazy dog";

        //when
        try {
            queryResponder.answerQueryOnInterGalacticQuantity(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void cannotAnswerSilencesInPlaceOfInterGalacticAmountQuery() {
        //Given
        String query = StringUtils.EMPTY;

        //when
        try {
            queryResponder.answerQueryOnInterGalacticQuantity(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }

        //Given
        query = StringUtils.SPACE;

        //when
        try {
            queryResponder.answerQueryOnInterGalacticQuantity(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }

        //Given
        query = null;

        //when
        try {
            queryResponder.answerQueryOnInterGalacticQuantity(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void calculatesNumberOfCreditsOfATransaction() {
        //Given
        String transaction = "glob prok silver";

        //verify
        Integer expectedAnswer = 68;

        //when
        Integer answer = queryResponder.calculateNumberOfCredits(transaction);

        //then
        assertEquals(expectedAnswer, answer);

        //Given
        transaction = "glob prok gold";

        //verify
        expectedAnswer = 57800;

        //when
        answer = queryResponder.calculateNumberOfCredits(transaction);

        //then
        assertEquals(expectedAnswer, answer);

        //Given
        transaction = "glob prok iron";

        //verify
        expectedAnswer = 782;

        //when
        answer = queryResponder.calculateNumberOfCredits(transaction);

        //then
        assertEquals(expectedAnswer, answer);

    }

    @Test
    public void answersHowManyCreditsForATransaction() {
        //Given
        String query = "how many credits is glob prok silver ?";

        //verify
        String expectedAnswer = "glob prok silver is 68 credits";

        //when
        String answer = null;
        try {
            answer = queryResponder.answerQueryOnCreditsOfATransaction(query);
        } catch (InvalidQueryException e) {
            e.printStackTrace();
            fail("Should not have thrown exception");
        }

        //then
        assertEquals(expectedAnswer, answer);

        query = "how many credits is glob prok gold ?";

        //verify
        expectedAnswer = "glob prok gold is 57800 credits";

        //when
        try {
            answer = queryResponder.answerQueryOnCreditsOfATransaction(query);
        } catch (InvalidQueryException e) {
            fail("Should not have thrown exception");
        }

        //then
        assertEquals(expectedAnswer, answer);

        query = "how many credits is glob prok iron ?";

        //verify
        expectedAnswer = "glob prok iron is 782 credits";

        //when
        try {
            answer = queryResponder.answerQueryOnCreditsOfATransaction(query);
        } catch (InvalidQueryException e) {
            fail("Should not have thrown exception");
        }

        //then
        assertEquals(expectedAnswer, answer);
    }

    @Test
    public void cannotAnswerAQueryWithUnknownEarthMaterial() {
        //Given
        String query = "how many credits is glob prok fish ?";

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void cannotAnswerAQueryWithUnknownGalacticUnit() {
        //Given
        String query = "how many credits is boog prok iron ?";

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void cannotAnswerAnyUnknownQuery() {
        //Given
        String query = "how many credits is wood could a woodchuck chuck if a woodchuck could chuck wood ?";

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void cannotAnswerRandomSentencesInPlaceOfTransactionQuery() {
        //Given
        String query = "The quick brown fox jumps over the lazy dog";

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }

    @Test
    public void cannotAnswerSilencesInPlaceOfTransactionQuery() {
        //Given
        String query = StringUtils.EMPTY;

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }

        //Given
        query = StringUtils.SPACE;

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }

        //Given
        query = null;

        //when
        try {
            queryResponder.answerQueryOnCreditsOfATransaction(query);
            fail("Should have thrown exception");
        } catch (InvalidQueryException e) {
            assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, e.getErrorMessage());
        }
    }
}
