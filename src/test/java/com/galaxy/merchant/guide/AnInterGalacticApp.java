package com.galaxy.merchant.guide;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.galaxy.merchant.guide.constants.InterGalacticAppConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for InterGalactic Decipherer App which can read file from command line and parse texts
 *
 * @author Gayathri Thiyagarajan
 */
public class AnInterGalacticApp {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void canReadLinesFromFile() {
        //Given
        String[] args = {"/Users/gthiyaga/Documents/MerchantsGuideToGalaxy/src/test/resources/notes.txt"};

        //when
        InterGalacticApp.main(args);

        //then
        assertFalse(errContent.toString().contains("Error reading notes from the file."));

        assertTrue(outContent.toString().contains("pish tegj glob glob is 42"));
        assertTrue(outContent.toString().contains("glob prok Silver is 68 Credits"));
        assertTrue(outContent.toString().contains("glob prok Gold is 57800 Credits"));
        assertTrue(outContent.toString().contains("glob prok Iron is 782 Credits"));
        assertTrue(outContent.toString().contains(InterGalacticAppConstants.DEFAULT_ANSWER));
    }

    @Test
    public void failsForNonExistentFile() {
        //Given
        String[] args = {"/Users/gthiyaga/Documents/MerchantsGuideToGalaxy/src/test/resources/notes_dont_exist.txt"};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("Error reading notes from the file /Users/gthiyaga/Documents/MerchantsGuideToGalaxy/src/test/resources/notes_dont_exist.txt", trim(errContent.toString()));


        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsForInvalidPath() {
        //Given
        String[] args = {"sdfjknad;fiehr0q9019i-1233notestxt"};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("Error reading notes from the file sdfjknad;fiehr0q9019i-1233notestxt", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsWhenNoFileSpecified() {
        //Given
        String[] args = {};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("File containing notes not specified.", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));

    }

    @Test
    public void failsWhenFileNameIsBlank() {

        //Given
        String[] args = {""};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("File containing notes not specified.", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsWhenNoArgsPassed() {
        //Given
        String[] args = null;

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("File containing notes not specified.", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsWhenFileNameIsNull() {
        //Given
        String[] args = {null};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("File containing notes not specified.", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsForEmptyFile() {
        //Given
        String[] args = {"/Users/gthiyaga/Documents/MerchantsGuideToGalaxy/src/test/resources/empty_notes.txt"};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("No inputs provided to decipher", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsForFileMissingKeyInfo() {
        //Given
        String[] args = {"/Users/gthiyaga/Documents/MerchantsGuideToGalaxy/src/test/resources/missing_info_in_notes.txt"};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals("No notes with conversion units found; cannot proceed further", trim(errContent.toString()));

        assertTrue(isEmpty(outContent.toString()));
    }

    @Test
    public void failsForFileContainingKeyInfoInInvalidFormat() {
        //Given
        String[] args = {"/Users/gthiyaga/Documents/MerchantsGuideToGalaxy/src/test/resources/info_in_invalid_format_in_notes.txt"};

        //when
        InterGalacticApp.main(args);

        //then
        assertEquals(InterGalacticAppConstants.DEFAULT_ANSWER, trim(outContent.toString()));

        assertTrue(isEmpty(errContent.toString()));
    }

}
