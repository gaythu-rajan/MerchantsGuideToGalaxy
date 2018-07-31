# Merchant's Guide To Galaxy

## Environment
- Java 8
- Gradle 2.8 or above

## Design

InterGalacticApp reads notes given a file and answers queries that it detects from the notes.

The app interprets the notes into three classifications.
- List of lines detected to be galactic numeral conversion factors
- List of lines detected to be transactions containing the Earth materials being transacted and how much they are worth
- List of lines detected to be queries

It uses Parsers to parse and extract the conversion factors from the above inputs.

There are two converters
- InterGalacticToRomanConverter - converts intergalactic phrase into roman numeric e.g. glob prok = IV
- RomanToArabicConverter - converts Roman numeric into equivalent Arabic numeric value e.g. IV = 4

QueryResponder using these conversion factors calculates and constructs the answers.
The answers correspond to each query in the order that they were asked.

If the app cannot comprehend the inputs or extract conversion factors, it responds with a single default answer "I have no idea what you are talking about".

If the app cannot understand one or more questions from a list of valid questions, it responds with default answer for each of the invalid query and valid answer for the rest.

 ## Assumptions

- InterGalactic App is case insensitive glob=GLOB
- Only roman letters will be recognised as valid earthly currency
- The app detects only queries of format "how many ...?" or "how much ...?"
- Only accepts text files with extension .txt


## How To Run

From command line :

Compile and build  - *gradle clean build*
Run Tests          - *gradle test*
Run                - *gradle run -PpathToNotes=/Users/xxxx/Desktop/notes.txt*

*where **pathToNotes** is the absolute path containing the file with input data*

