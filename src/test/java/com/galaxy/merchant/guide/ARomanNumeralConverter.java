package com.galaxy.merchant.guide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Tests the class that converts an intergalactic phrase into roman numeric phrase
 *
 * @author Gayathri Thiyagarajan
 */
public class ARomanNumeralConverter {

    RomanNumericConverter romanNumericConverter = new RomanNumericConverter();
    @Test
    public void convertsRomanNumeralIntoNumericValueUsingSubtractionNotation() {
        //Given
        String romanSegment = "IV";

        //verify
        Integer expectedNumericValue = 4;

        //when
        try {
            Integer actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            assertEquals(expectedNumericValue, actualNumericValue);
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "IX";

        //verify
        expectedNumericValue = 9;

        //when
        try {
            Integer actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            assertEquals(expectedNumericValue, actualNumericValue);
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XL";

        //verify
        expectedNumericValue = 40;

        //when
        try {
            Integer actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            assertEquals(expectedNumericValue, actualNumericValue);
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XC";

        //verify
        expectedNumericValue = 90;

        //when
        try {
            Integer actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            assertEquals(expectedNumericValue, actualNumericValue);
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "CD";

        //verify
        expectedNumericValue = 400;

        //when
        try {
            Integer actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            assertEquals(expectedNumericValue, actualNumericValue);
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "CM";

        //verify
        expectedNumericValue = 900;

        //when
        try {
            Integer actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            assertEquals(expectedNumericValue, actualNumericValue);
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void convertsRomanNumeralIntoNumericValue() {
        //Given
        HashMap<String, Integer> romanToNumericValueMap = new HashMap<>();
        romanToNumericValueMap.put("I", 1);
        romanToNumericValueMap.put("II", 2);
        romanToNumericValueMap.put("V", 5);
        romanToNumericValueMap.put("VI", 6);
        romanToNumericValueMap.put("X", 10);
        romanToNumericValueMap.put("L", 50);
        romanToNumericValueMap.put("LXII", 62);
        romanToNumericValueMap.put("C", 100);
        romanToNumericValueMap.put("CL", 150);
        romanToNumericValueMap.put("CLX", 160);
        romanToNumericValueMap.put("D", 500);
        romanToNumericValueMap.put("DCLXXX", 680);
        romanToNumericValueMap.put("M", 1000);
        romanToNumericValueMap.put("XXXIX", 39);
        romanToNumericValueMap.put("MMXIV", 2014);
        romanToNumericValueMap.put("MCMXC", 1990);
        romanToNumericValueMap.put("MCMLIV", 1954);
        romanToNumericValueMap.put("MMXVIII", 2018);
        romanToNumericValueMap.put("MDCCLXXVI", 1776);


        //when
        Integer actualNumericValue = 0;
        Integer expectedNumericValue;

        for (String romanSegment : romanToNumericValueMap.keySet()) {
            try {
                actualNumericValue = romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            } catch (InvalidInputFormatException e) {
                fail("Should not have thrown any exception");
            }
            expectedNumericValue = romanToNumericValueMap.get(romanSegment);
            //then
            assertEquals(expectedNumericValue, actualNumericValue);
        }
    }

    @Test
    public void doesNotFailForValidOccurencesOfI() throws InvalidInputFormatException {
        //Given
        String romanSegment = "I";

        //when
        try {
            assertEquals(1, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "II";

        //when
        try {
            assertEquals(2, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "III";

        //when
        try {
            assertEquals(3, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "IV";

        //when
        try {
            assertEquals(4, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsForMoreThan3ConsecutiveIs() throws InvalidInputFormatException {

        //Given
        String romanSegment = "IIII";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals("Input segment is invalid", e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

    }

    @Test
    public void failsIfVIsRepeatedAnywhere() throws InvalidInputFormatException {
        //Given
        String romanSegment = "VV";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }


        //Given
        romanSegment = "VXV";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "LVV";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "IVV";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "DLVVV";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void doesNotFailForValidOccurencesOfV() throws InvalidInputFormatException {
        //Given
        String romanSegment = "V";

        //when
        try {
            assertEquals(5, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "VI";

        //when
        try {
            assertEquals(6, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "IV";

        //when
        try {
            assertEquals(4, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsForMoreThan3ConsecutiveXs() throws InvalidInputFormatException {

        //Given
        String romanSegment = "XXXX";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

    }

    @Test
    public void doesNotFailForValidOccurencesOfX() throws InvalidInputFormatException {
        //Given
        String romanSegment = "X";

        //when
        try {
            assertEquals(10, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XX";

        //when
        try {
            assertEquals(20, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XXX";

        //when
        try {
            assertEquals(30, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "IX";


        //when
        try {
            assertEquals(9, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XI";


        //when
        try {
            assertEquals(11, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XIX";


        //when
        try {
            assertEquals(19, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void doesNotFailForFourOccurancesOfXWithASmallNumberInBetween() throws InvalidInputFormatException {
        //Given
        String romanSegment = "XXXIX";

        //when
        try {
            assertEquals(39, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsIfLIsRepeatedAnywhere() throws InvalidInputFormatException {
        //Given
        String romanSegment = "LLL";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "LXL";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "DLLV";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "DLVLV";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "DCLLXX";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void doesNotFailForValidOccurencesOfL() throws InvalidInputFormatException {
        //Given
        String romanSegment = "L";

        //when
        try {
            assertEquals(50, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "LXV";

        //when
        try {
            assertEquals(65, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XL";

        //when
        try {
            assertEquals(40, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsForMoreThan3ConsecutiveCs() throws InvalidInputFormatException {

        //Given
        String romanSegment = "CCCC";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

    }

    @Test
    public void doesNotFailForValidOccurencesOfC() throws InvalidInputFormatException {
        //Given
        String romanSegment = "C";

        //when
        try {
            assertEquals(100, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "CLX";

        //when
        try {
            assertEquals(160, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "CCL";

        //when
        try {
            assertEquals(250, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void doesNotFailForFourOccurancesOfCWithASmallNumberInBetween() throws InvalidInputFormatException {
        //Given
        String romanSegment = "CCCXC";

        //when
        try {
            assertEquals(390, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsIfDIsRepeatedAnywhere() throws InvalidInputFormatException {
        //Given
        String romanSegment = "DDDD";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "DLD";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "DCLDXX";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void doesNotFailForValidOccurencesOfD() throws InvalidInputFormatException {
        //Given
        String romanSegment = "D";

        //when
        try {
            assertEquals(500, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "DLXV";

        //when
        try {
            assertEquals(565, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsForMoreThan3ConsecutiveMs() throws InvalidInputFormatException {

        //Given
        String romanSegment = "MMMM";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

    }

    @Test
    public void doesNotFailForValidOccurencesOfM() throws InvalidInputFormatException {
        //Given
        String romanSegment = "M";

        //when
        try {
            assertEquals(1000, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "MCLX";

        //when
        try {
            assertEquals(1160, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "MML";

        //when
        try {
            assertEquals(2050, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void doesNotFailForFourOccurancesOfMWithASmallNumberInBetween() throws InvalidInputFormatException {
        //Given
        String romanSegment = "MMMCM";

        //when
        try {
            assertEquals(3900, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void doesNotFailForValidSubtractionOccurenceOfI() throws InvalidInputFormatException {
        //Given
        String romanSegment = "IV";

        //when
        try {
            assertEquals(4, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "IX";

        //when
        try {
            assertEquals(9, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }
    }

    @Test
    public void failsWhenIIsSubtractedFromAnythingOtherThanVOrX() throws InvalidInputFormatException {

        //Given
        String romanSegment = "IL";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "IC";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "ID";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "IM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failsWhenIOccursBetweenVAndX() throws InvalidInputFormatException {

        //Given
        String romanSegment = "VIX";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VIIX";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VIIIX";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void doesNotFailForValidSubtractionOccurenceOfX() throws InvalidInputFormatException {
        //Given
        String romanSegment = "XL";

        //when
        try {
            assertEquals(40, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "XC";

        //when
        try {
            assertEquals(90, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "MXL";

        //when
        try {
            assertEquals(1040, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "MDXL";

        //when
        try {
            assertEquals(1540, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

        //Given
        romanSegment = "MDXC";

        //when
        try {
            assertEquals(1590, romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment).intValue());
        } catch (InvalidInputFormatException e) {
            //then
            fail("There shouldn't be any exception");
        }

    }

    @Test
    public void failsWhenXIsSubtractedFromAnythingOtherThanLOrC() throws InvalidInputFormatException {

        //Given
        String romanSegment = "XD";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "MXD";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "XM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

    }

    @Test
    public void failsWhenVIsSubtractedFromAnything() throws InvalidInputFormatException {

        //Given
        String romanSegment = "VX";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "LVX";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VL";


        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VXL";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "CVL";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VC";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VD";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "VM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failsWhenLIsSubtractedFromAnything() throws InvalidInputFormatException {

        //Given
        String romanSegment = "LC";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "CLD";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "LD";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "MLD";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "LM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "MLM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failsWhenDIsSubtractedFromAnything() throws InvalidInputFormatException {

        //Given
        String romanSegment = "DM";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "MDM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failsForSomeInvalidCombinations() throws InvalidInputFormatException {

        //Given
        String romanSegment = "IXV";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

        //Given
        romanSegment = "IXI";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }
        //Given
        romanSegment = "IXX";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

        //Given
        romanSegment = "IXL";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

        //Given
        romanSegment = "IXC";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

        //Given
        romanSegment = "IXD";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }
        //Given
        romanSegment = "IXM";
        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

        //Given
        romanSegment = "IVI";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

        //Given
        romanSegment = "IIX";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        }

    }

    @Test
    public void failsIfIXCMAreRepeatedMoreThanThreeTimesInARow() throws InvalidInputFormatException {
        //Given
        String romanSegment = "XXXX";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "IIIIL";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "CCCCV";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "MMMMMI";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failsIfIXCMAreRepeatedMoreThanFourTimesInASegment() throws InvalidInputFormatException {
        //Given
        String romanSegment = "XXXIXX";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "IIIVII";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "CCCLCC";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = "MMMCMM";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

    }

    @Test
    public void failsToConvertWhenRomanSegmentIsNull() throws InvalidInputFormatException {
        //Given
        String romanSegment = null;

        //verify
        String expectedErrorMessage = "Input segment is absent";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = StringUtils.EMPTY;

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        romanSegment = StringUtils.SPACE;

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failsToConvertWhenRomanSegmentHasNonRomanCharacters() throws InvalidInputFormatException {
        //Given
        String romanSegment = "I!@#@";

        //verify
        String expectedErrorMessage = "Input segment is invalid";

        //when
        try {
            romanNumericConverter.convertRomanSegmentIntoNumericValue(romanSegment);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

}
