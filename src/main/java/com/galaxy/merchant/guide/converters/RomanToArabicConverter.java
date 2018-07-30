package com.galaxy.merchant.guide.converters;

import static com.galaxy.merchant.guide.domain.RomanNumerals.C;
import static com.galaxy.merchant.guide.domain.RomanNumerals.D;
import static com.galaxy.merchant.guide.domain.RomanNumerals.I;
import static com.galaxy.merchant.guide.domain.RomanNumerals.L;
import static com.galaxy.merchant.guide.domain.RomanNumerals.M;
import static com.galaxy.merchant.guide.domain.RomanNumerals.V;
import static com.galaxy.merchant.guide.domain.RomanNumerals.X;
import static com.galaxy.merchant.guide.domain.RomanNumerals.Z;
import static com.galaxy.merchant.guide.domain.RomanNumerals.valueOf;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.galaxy.merchant.guide.domain.RomanNumerals;
import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Converter to convert a roman numeric into arabic numeric.
 *
 * @author Gayathri Thiyagarajan
 */
public class RomanToArabicConverter {

    private static String MORE_THAN_3_IN_A_ROW_I = "(I{4,}+)";
    private static String MORE_THAN_3_IN_A_ROW_X = "(X{4,}+)";
    private static String MORE_THAN_3_IN_A_ROW_C = "(C{4,}+)";
    private static String MORE_THAN_3_IN_A_ROW_M = "(M{4,}+)";

    private static String I_CANNOT_BE_SUBTRACTED_FROM_L_C_D_M = "I[LCDM]";
    private static String X_CANNOT_BE_SUBTRACTED_FROM_D_M = "X[DM]";
    private static String V_CANNOT_BE_SUBTRACTED_FROM_ANYTHING = "V[XLCDM]";
    private static String L_CANNOT_BE_SUBTRACTED_FROM_ANYTHING = "L[CDM]";
    private static String D_CANNOT_BE_SUBTRACTED_FROM_ANYTHING = "D[M]";

    private static String AN_ODD_BALL = "(^(?!III)I[IVX][IVXLCDM])|(^VI*X[IVXLCDM]*)";

    /**
     * Converts a roman segment into its numeric value.
     *
     * @param romanSegment valid roman segment
     * @return equivalent numeric value
     * @throws InvalidInputFormatException if the roman segment does not satisfy roman numeric rules
     */
    public Integer convertRomanSegmentIntoNumericValue(String romanSegment) throws InvalidInputFormatException {

        checkForValidRomanSegment(romanSegment);
        verifyRepetitionRulesOnTheSegment(romanSegment);
        verifySubtractionRulesOnTheSegment(romanSegment);
        verifyTheSegmentIsNotAnOddBall(romanSegment);

        Character[] romanLettersFromTheSegment = ArrayUtils.toObject(romanSegment.toCharArray());
        return computeNumericValueOfARomanSegment(romanLettersFromTheSegment);

    }

    //Catch all other combinations that can't be caught by the rules
    private void verifyTheSegmentIsNotAnOddBall(String romanSegment) throws InvalidInputFormatException {
        //Odd ball pattern
        if(Pattern.compile(AN_ODD_BALL).matcher(romanSegment).find())
            throw new InvalidInputFormatException("Input segment is invalid");
    }

    /**
     * Checks that the roman segment is not null, blank or empty
     * @param romanSegment Roman numeric segment
     * @throws InvalidInputFormatException  if the roman segment is null, blank or empty
     */
    private void checkForValidRomanSegment(String romanSegment) throws InvalidInputFormatException {
        if(!isNoneBlank(romanSegment))
            throw new InvalidInputFormatException("Input segment is absent");

        Character[] romanLettersFromTheSegment = ArrayUtils.toObject(romanSegment.toCharArray());

        try {
            Arrays.stream(romanLettersFromTheSegment).forEach(r -> valueOf(r.toString()).numericValue());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputFormatException("Input segment is invalid");
        }
    }

    /**
     * Verifies the following rules of a valid roman numeric
     *  <ul>
     *      <li>The symbols"D", "L", and "V" can never be repeated</li>
     *      <li>The symbols"I", "X", "C", and "M" can be repeated three times in succession, but no more</li>
     *      <li>They may appear four times if the third and fourth are separated by a smaller value</li>
     *  </ul>
     * @param romanSegment Roman numeric Segment
     * @throws InvalidInputFormatException if the roman numeric violates any of the rules of repetition
     */
    private void verifyRepetitionRulesOnTheSegment(String romanSegment) throws InvalidInputFormatException {
        //"D", "L", and "V" can never be repeated.
        if (countMatches(romanSegment, D.name()) > 1 ||
                countMatches(romanSegment, L.name()) > 1 ||
                countMatches(romanSegment, V.name()) > 1) {
            throw new InvalidInputFormatException("Input segment is invalid");
        }

        //"D", "L", and "V" can never be repeated.
        if (countMatches(romanSegment, I.name()) > 4 ||
                countMatches(romanSegment, X.name()) > 4 ||
                countMatches(romanSegment, C.name()) > 4 ||
                countMatches(romanSegment, M.name()) > 4) {
            throw new InvalidInputFormatException("Input segment is invalid");
        }

        //"I", "X", "C", and "M" can be repeated three times in succession, but no more.
        if(Pattern.compile(MORE_THAN_3_IN_A_ROW_I).matcher(romanSegment).find() ||
                Pattern.compile(MORE_THAN_3_IN_A_ROW_X).matcher(romanSegment).find() ||
                Pattern.compile(MORE_THAN_3_IN_A_ROW_C).matcher(romanSegment).find() ||
                Pattern.compile(MORE_THAN_3_IN_A_ROW_M).matcher(romanSegment).find())
            throw new InvalidInputFormatException("Input segment is invalid");

    }

    /**
     * Verifies the following subtraction rules of a valid roman numeric
     *  <ul>
     *      <li>"I" can be subtracted from "V" and "X" only.  </li>
     *      <li>"X" can be subtracted from "L" and "C" only.</li>
     *      <li>"C" can be subtracted from "D" and "M" only. </li>
     *      <li>"V", "L", and "D" can never be subtracted. </li>
     *  </ul>
     * @param romanSegment
     * @throws InvalidInputFormatException if the roman numeric violates any of the rules of repetition
     */
    private void verifySubtractionRulesOnTheSegment(String romanSegment) throws InvalidInputFormatException {

        if(Pattern.compile(I_CANNOT_BE_SUBTRACTED_FROM_L_C_D_M).matcher(romanSegment).find() ||
                Pattern.compile(X_CANNOT_BE_SUBTRACTED_FROM_D_M).matcher(romanSegment).find() ||
                Pattern.compile(V_CANNOT_BE_SUBTRACTED_FROM_ANYTHING).matcher(romanSegment).find() ||
                Pattern.compile(L_CANNOT_BE_SUBTRACTED_FROM_ANYTHING).matcher(romanSegment).find() ||
                Pattern.compile(D_CANNOT_BE_SUBTRACTED_FROM_ANYTHING).matcher(romanSegment).find()
                )
            throw new InvalidInputFormatException("Input segment is invalid");
    }

    /**
     * Computes the numeric value from the roman letters and its numerical equivalent by applying the addition/subtraction rules.
     *
     * @param romanLettersFromTheSegment Roman segment split into roman characters
     * @return numericEquivalent of the Roman numeric segment
     */
    private Integer computeNumericValueOfARomanSegment(Character[] romanLettersFromTheSegment) {
        //Init the sum to zero as there is no zero in roman numeric
        Integer numericValue = 0;
        RomanNumerals letterAtCurrentPos;
        RomanNumerals letterAtNextPos = Z;

        Integer valueToAdd;

        int j;

        //Iterate through the segment two chars at a time  - to compare and subtract if need be
        for(int i =0; i < romanLettersFromTheSegment.length; i += j) {
            letterAtCurrentPos = valueOf(romanLettersFromTheSegment[i].toString());

            if((i + 1) < romanLettersFromTheSegment.length)
                letterAtNextPos = valueOf(romanLettersFromTheSegment[i + 1].toString());

            //If letter at i is less than letter at i + 1, then subtract before adding to the cumulative value
            if(letterAtCurrentPos.compare(letterAtNextPos)) {
                valueToAdd = letterAtNextPos.numericValue() - letterAtCurrentPos.numericValue();
                j = 2;       //skip the next character as it has already been subtracted
            } else {
                valueToAdd = letterAtCurrentPos.numericValue();
                j = 1;
            }

            numericValue += valueToAdd;
        }
        return numericValue;
    }

}
