package com.galaxy.merchant.guide;

import java.util.HashMap;
import java.util.List;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;

/**
 * Abstract class to parse lines from notes
 *
 * @author Gayathri Thiyagarajan
 */
abstract class NotesParser {

    abstract HashMap<String, ?> parseNotes(List<String> linesFromNotes) throws InvalidInputFormatException;
}
