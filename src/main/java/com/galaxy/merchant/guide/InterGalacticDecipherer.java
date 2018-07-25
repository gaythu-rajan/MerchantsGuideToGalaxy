package com.galaxy.merchant.guide;

import static com.galaxy.merchant.guide.constants.InterGalacticAppConstants.DEFAULT_ANSWER;
import static com.galaxy.merchant.guide.constants.InterGalacticAppConstants.PLACEHOLDER_FOR_UNPARSEABLE_QUESTIONS;
import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import com.galaxy.merchant.guide.exceptions.InvalidQueryException;
import com.galaxy.merchant.guide.exceptions.NoInputProvidedException;

/**
 * InterGalacticDecipherer deciphers the lines of notes to extract the relevant conversion units
 * and turns queries into answers
 *
 */
class InterGalacticDecipherer {

    private static final String HOW_MUCH_QUESTION_START = "how much";
    private static final String HOW_MANY_QUESTION_START = "how many";

    private List<String> bucketOfNotesOnInterGalacticConversionUnits = new ArrayList<>();
    private List<String> bucketOfNotesOnTransactions = new ArrayList<>();
    private List<String> bucketOfQueries = new LinkedList<>();

    private HashMap<String, String> interGalacticConversionUnits;
    private HashMap<String, Double> creditsPerEarthMaterial;
    private LinkedHashMap<String, String> queriesAndTheirAnswers = new LinkedHashMap<>();

    private static String INTER_GALACTIC_UNIT_NOTES_PATTERN = "^\\w+\\sis\\s[IVXLCDM]$";
    private static String TRANSACTION_NOTES_PATTERN = "^(\\w).*\\sis\\s\\d+\\s[cC]redits$";
    private static String QUERY_PATTERN = "^how\\s(much|many)\\s\\w.*?$";


    /**
     * Deciphers lines of text and answers queries in the line of text
     * @param linesOfText Lines of input text as read from the file
     * @throws NoInputProvidedException when there are no notes to decipher from the file
     * @throws InvalidInputFormatException from the conversion downstream if any of units
     *                                      or transactions or queries don't conform to the pattern
     */
    void decipher(String[] linesOfText) throws NoInputProvidedException, InvalidInputFormatException {

        if(linesOfText.length == 0) {
            throw new NoInputProvidedException("No inputs provided to decipher");
        }

        // Parse each line and put it in respective buckets
        parseLinesOfTextFromNotes(linesOfText);

        //Without conversion units we cannot proceed
        if (bucketOfNotesOnInterGalacticConversionUnits.size() == 0) {
            throw new NoInputProvidedException("No notes with conversion units found; cannot proceed further");
        }

        //Without previous transaction notes we cannot calculate the credits per material
        if (bucketOfNotesOnTransactions.size() == 0) {
            throw new NoInputProvidedException("No notes with previous transactions found; cannot proceed further");
        }

        // parse the galactic units conversion bucket and get the galactic conversion units e.g. Glob = I
        InterGalacticUnitConversionNotesParser interGalacticUnitConversionNotesParser = new InterGalacticUnitConversionNotesParser();
        interGalacticConversionUnits = interGalacticUnitConversionNotesParser.parseNotes(bucketOfNotesOnInterGalacticConversionUnits);

        // parse the earth transaction bucket and get the number of credits per Earth Material (value stored as double but rounded off later)
        //e.g Silver=17 (credits)
        TransactionNotesParser transactionNotesParser = new TransactionNotesParser(interGalacticConversionUnits);
        creditsPerEarthMaterial = transactionNotesParser.parseNotes(bucketOfNotesOnTransactions);

        //Assumption - Format of query - "how much ..." for query on Intergalactic Amount & "how many .." for transactional queries
        //Parse the query bucket, calculate and frame the answer for each query e.g. how much is pish tegj glob glob ? = pish tegj glob glob is 42
        //Any exceptions in parsing is treated as invalid query and queries of different format than assumed is treated as invalid query as well
        if(creditsPerEarthMaterial.size() != 0) {
            QueryResponder queryResponder = new QueryResponder.QueryResponderBuilder()
                    .setEarthMaterialForSale(creditsPerEarthMaterial)
                    .setInterGalacticUnits(interGalacticConversionUnits).createQueryResponder();

            String answerToQuery;
            for(String query : bucketOfQueries) {
                if(query.toLowerCase().startsWith(HOW_MUCH_QUESTION_START)) {
                    try {
                        answerToQuery = queryResponder.answerQueryOnIntergalacticAmount(query.toLowerCase());
                    } catch (InvalidQueryException e) {
                        answerToQuery = e.getErrorMessage();
                    }
                    queriesAndTheirAnswers.put(query, answerToQuery);
                }
                if(query.toLowerCase().startsWith(HOW_MANY_QUESTION_START)) {
                    try {
                        answerToQuery = queryResponder.answerQueryOnCreditsOfATransaction(query.toLowerCase());
                    } catch (InvalidQueryException e) {
                        answerToQuery = e.getErrorMessage();
                    }
                    queriesAndTheirAnswers.put(query, answerToQuery);
                }
            }
        }

        //If the input was not parseable for some reason and there were no answers, then return default answer
        if(queriesAndTheirAnswers.size() == 0) {
            queriesAndTheirAnswers.put(PLACEHOLDER_FOR_UNPARSEABLE_QUESTIONS, DEFAULT_ANSWER);
        }
    }

    /**
     * Parses lines of notes and puts them in respective buckets
     * @param linesOfText Input Lines of text
     */
    private void parseLinesOfTextFromNotes(String[] linesOfText) {

        bucketOfNotesOnInterGalacticConversionUnits = stream(linesOfText)
                                                .filter(lineOfText -> lineOfText.matches(INTER_GALACTIC_UNIT_NOTES_PATTERN))
                                                .collect(Collectors.toList());

        bucketOfNotesOnTransactions = stream(linesOfText)
                                                .filter(lineOfText -> lineOfText.matches(TRANSACTION_NOTES_PATTERN))
                                                .map(String::toLowerCase)
                                                .collect(Collectors.toList());

        bucketOfQueries = stream(linesOfText)
                                                .filter(lineOfText -> lineOfText.matches(QUERY_PATTERN))
                                                .collect(Collectors.toList());
    }

    List<String> getBucketOfNotesOnInterGalacticConversionUnits() {
        return bucketOfNotesOnInterGalacticConversionUnits;
    }

    List<String> getBucketOfNotesOnTransactions() {
        return bucketOfNotesOnTransactions;
    }

    List<String> getBucketOfQueries() {
        return bucketOfQueries;
    }

    HashMap<String, String> getInterGalacticConversionUnits() {
        return interGalacticConversionUnits;
    }

    HashMap<String, Double> getCreditsPerEarthMaterial() {
        return creditsPerEarthMaterial;
    }

    HashMap<String, String> getQueriesAndTheirAnswers() {
        return queriesAndTheirAnswers;
    }
}
