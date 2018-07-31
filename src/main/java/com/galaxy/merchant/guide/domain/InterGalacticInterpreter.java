package com.galaxy.merchant.guide.domain;

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
import com.galaxy.merchant.guide.parsers.InterGalacticNumeralNotesParser;
import com.galaxy.merchant.guide.parsers.TransactionNotesParser;

/**
 * InterGalacticInterpreter interprets the lines of notes to extract the relevant conversion map
 * and interprets queries to answer them
 */
public class InterGalacticInterpreter {

    private static final String HOW_MUCH_QUESTION_START = "how much";
    private static final String HOW_MANY_QUESTION_START = "how many";

    private List<String> bucketOfNotesOnInterGalacticNumerals = new ArrayList<>();
    private List<String> bucketOfNotesOnTransactions = new ArrayList<>();
    private List<String> bucketOfQueries = new LinkedList<>();

    private HashMap<String, String> interGalacticToRomanConversionMap;
    private HashMap<String, Double> creditsPerEarthMaterial;
    private LinkedHashMap<String, String> queriesAndTheirAnswers = new LinkedHashMap<>();

    private static String INTER_GALACTIC_UNIT_NOTES_PATTERN = "^\\w+\\sis\\s[IVXLCDM]$";
    private static String TRANSACTION_NOTES_PATTERN = "^(\\w).*\\sis\\s\\d+\\s[cC]redits$";
    private static String QUERY_PATTERN = "^how\\s(much|many)\\s\\w.*?$";


    /**
     * Interprets lines of text and answers queries in the line of text
     *
     * @param linesOfText Lines of input text as read from the file
     * @throws NoInputProvidedException    when there are no notes to interpret from the file
     * @throws InvalidInputFormatException from the conversion downstream if any of conversion map or
     *                                     transactions or queries don't conform to the pattern
     */
    public HashMap<String, String> interpret(String[] linesOfText) throws NoInputProvidedException, InvalidInputFormatException {


        if (linesOfText.length == 0) {
            throw new NoInputProvidedException("No inputs provided to interpret");
        }

        // Parse each line and put it in respective buckets
        classifyLinesOfTextFromNotes(linesOfText);

        //Without conversion map we cannot proceed
        if (bucketOfNotesOnInterGalacticNumerals.size() == 0) {
            throw new NoInputProvidedException("No notes with conversion mapping found; cannot proceed further");
        }

        //Without previous transaction notes we cannot calculate the credits per material
        if (bucketOfNotesOnTransactions.size() == 0) {
            throw new NoInputProvidedException("No notes with previous transactions found; cannot proceed further");
        }

        interpretInterGalacticToRomanConversionMap(bucketOfNotesOnInterGalacticNumerals);
        interpretNumberOfCreditsPerEarthMaterial(interGalacticToRomanConversionMap, bucketOfNotesOnTransactions);
        interpretAnswersToQueries(bucketOfQueries, interGalacticToRomanConversionMap, creditsPerEarthMaterial);

        return queriesAndTheirAnswers;
    }

    /**
     * Parses lines of notes and puts them in respective buckets
     *
     * @param linesOfText Input Lines of text
     */
    void classifyLinesOfTextFromNotes(String[] linesOfText) {

        bucketOfNotesOnInterGalacticNumerals = stream(linesOfText)
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

    /**
     * Parse the galactic conversion notes bucket and get the galactic to roman conversion map e.g. Glob = I
     */
    void interpretInterGalacticToRomanConversionMap(List<String> bucketOfNotesOnInterGalacticNumerals) {

        InterGalacticNumeralNotesParser interGalacticNumeralNotesParser = new InterGalacticNumeralNotesParser();
        interGalacticNumeralNotesParser.parseNotes(bucketOfNotesOnInterGalacticNumerals);
        this.interGalacticToRomanConversionMap = interGalacticNumeralNotesParser.getInterGalacticToRomanConversionMap();

    }


    /**
     * Parse the earth transaction bucket and get the number of credits per Earth Material (value stored as double but rounded off later)
     e.g Silver=17 (credits)
     *
     * @param interGalacticToRomanConversionMap Conversion factors
     * @param bucketOfNotesOnTransactions collection of transaction notes
     */
    void interpretNumberOfCreditsPerEarthMaterial(HashMap<String, String> interGalacticToRomanConversionMap,
                                                  List<String> bucketOfNotesOnTransactions)
            throws InvalidInputFormatException {
        TransactionNotesParser transactionNotesParser = new TransactionNotesParser(interGalacticToRomanConversionMap);
        transactionNotesParser.parseNotes(bucketOfNotesOnTransactions);
        this.creditsPerEarthMaterial = transactionNotesParser.getCreditsPerEarthMaterial();
    }

    /**
     * Assumption - Format of query - "how much ..." for query on Intergalactic Amount & "how many .." for transactional queries
     * Parse the query bucket, calculate and frame the answer for each query e.g. how much is pish tegj glob glob ? = pish tegj glob glob is 42
     * Any exceptions in parsing is treated as invalid query and queries of different format than assumed is treated as invalid query as well
     *
     * @param bucketOfQueries collection of queries
     * @param interGalacticToRomanConversionMap Conversion factors
     * @param creditsPerEarthMaterial Number of credits per quantity of earth material
     */
    private void interpretAnswersToQueries(List<String> bucketOfQueries,
                                                                    HashMap<String, String> interGalacticToRomanConversionMap,
                                                                    HashMap<String, Double> creditsPerEarthMaterial) {

        LinkedHashMap<String, String> queriesAndTheirAnswers = new LinkedHashMap<>();

        if (creditsPerEarthMaterial.size() != 0) {
            QueryResponder queryResponder = new QueryResponder.QueryResponderBuilder()
                    .setCreditsForEarthMaterials(creditsPerEarthMaterial)
                    .setInterGalacticToRomanConversionMap(interGalacticToRomanConversionMap).createQueryResponder();

            String answerToQuery;
            for (String query : bucketOfQueries) {
                if (query.toLowerCase().startsWith(HOW_MUCH_QUESTION_START)) {
                    try {
                        answerToQuery = queryResponder.answerQueryOnInterGalacticQuantity(query.toLowerCase());
                    } catch (InvalidQueryException e) {
                        answerToQuery = e.getErrorMessage();
                    }
                    queriesAndTheirAnswers.put(query, answerToQuery);
                }
                if (query.toLowerCase().startsWith(HOW_MANY_QUESTION_START)) {
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
        if (queriesAndTheirAnswers.size() == 0) {
            queriesAndTheirAnswers.put(PLACEHOLDER_FOR_UNPARSEABLE_QUESTIONS, DEFAULT_ANSWER);
        }
        this.queriesAndTheirAnswers = queriesAndTheirAnswers;
    }

    List<String> getBucketOfNotesOnInterGalacticNumerals() {
        return bucketOfNotesOnInterGalacticNumerals;
    }

    List<String> getBucketOfNotesOnTransactions() {
        return bucketOfNotesOnTransactions;
    }

    List<String> getBucketOfQueries() {
        return bucketOfQueries;
    }

    HashMap<String, String> getInterGalacticToRomanConversionMap() {
        return interGalacticToRomanConversionMap;
    }

    HashMap<String, Double> getCreditsPerEarthMaterial() {
        return creditsPerEarthMaterial;
    }

    LinkedHashMap<String, String> getQueriesAndTheirAnswers() {
        return queriesAndTheirAnswers;
    }
}
