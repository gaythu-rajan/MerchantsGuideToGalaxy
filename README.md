Merchant's Guide To Galaxy

##H2 
Assumptions

InterGalactic App is Case insensitive glob=GLOB
Only roman letters will be recognised as valid units

##H2
Design

InterGalacticApp can read notes from a file and decipher the notes into three classifications.
..*List of Galactic Unit conversion factors - Galactic currency and its Earthly equivalent in roman numerals
..*List of transactions containing the Earth materials being transacted and how much they were sold for
..*List of queries

InterGalacticApp uses corresponding Parsers to interpret the key inputs using which the subsequent queries will be answered. If these inputs are invalid or absent, the app will not be able to answer any queries and will give default answer "I have no idea what you are talking about"

QueryResponder then takes the parsed inputs, calculates and constructs the answers. 

Answers correspond to each query in the order that they were asked and is case sensitive.

If the app cannot comprehend the inputs, it responds with the default answer "I have no idea what you are talking about".

#H1 
How To Run

From command line
gradle run -PpathToNotes=/Users/gthiyaga/Desktop/notes.txt

pathToNotes is the absolute path containing the file with input data

