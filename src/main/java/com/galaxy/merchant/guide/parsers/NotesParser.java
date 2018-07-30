package com.galaxy.merchant.guide.parsers;

import java.util.HashMap;
import java.util.List;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;

/**
 * Abstract class to parse lines from notes
 *
 * @author Gayathri Thiyagarajan
 */
interface NotesParser {

    HashMap<String, ?> parseNotes(List<String> linesFromNotes) throws InvalidInputFormatException;
}
