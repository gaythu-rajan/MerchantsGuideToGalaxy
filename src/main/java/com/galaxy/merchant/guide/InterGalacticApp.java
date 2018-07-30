package com.galaxy.merchant.guide;

import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.galaxy.merchant.guide.domain.InterGalacticInterpreter;
import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import com.galaxy.merchant.guide.exceptions.NoInputProvidedException;

/**
 * Main class that reads notes from a file and interprets it
 *
 * @author Gayathri Thiyagarajan
 */
public class InterGalacticApp {

    private static Predicate<String[]> fileContainingNotesSpecified() {
        return args -> (args != null && args.length != 0 && isNotEmpty(args[0]));
    }

    private static Predicate<String> textFileSpecified() {
        return fileName -> (endsWith(fileName.toLowerCase(), ".txt"));
    }

    public static void main(String[] args) {

        if(fileContainingNotesSpecified().test(args)) {
            String fileName = args[0];
            String[] linesOfText;

            if (textFileSpecified().test(fileName)) {
                try {
                    Stream<String> stream = Files.lines(Paths.get(fileName));

                    linesOfText = stream.toArray(String[]::new);

                    InterGalacticInterpreter interGalacticInterpreter = new InterGalacticInterpreter();
                    HashMap<String, String> queriesAndAnswers = interGalacticInterpreter.interpret(linesOfText);

                    if(queriesAndAnswers != null && queriesAndAnswers.size() != 0) {
                        queriesAndAnswers.values().forEach(System.out::println); ;
                    }

                } catch (IOException e) {
                    System.err.println("Error reading notes from the file " + e.getMessage());
                } catch (NoInputProvidedException e) {
                    System.err.println(e.getErrorMessage());
                } catch (InvalidInputFormatException e) {
                    System.err.println("Error when interpreting notes from the file. Error is " + e.getErrorMessage());
                }
            } else {
                System.err.println("Invalid file format. Accepted format(s) : .txt");
            }
        } else {
            System.err.println("File containing notes not specified.");
        }
    }
}
