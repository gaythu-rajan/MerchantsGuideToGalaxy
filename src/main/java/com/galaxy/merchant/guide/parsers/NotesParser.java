package com.galaxy.merchant.guide.parsers;

import java.util.List;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;

/**
 * Abstract class to parse lines from notes
 *
 * @author Gayathri Thiyagarajan
 */
interface NotesParser {

    void parseNotes(List<String> linesFromNotes) throws InvalidInputFormatException;
}
