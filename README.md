# TextAnalyzer

A simple Java command-line tool that counts lines, words, and characters in a text file.

## Features
- Counts number of lines, words, and characters
- Supports command-line flags:
  - `-l` for lines
  - `-w` for words
  - `-c` for characters
- Handles invalid input and file errors

## To Compile
```bash
javac TextAnalyzer.java
```
## To Run
```bash
java TextAnalyzer sample.txt
java TextAnalyzer -l sample.txt
java TextAnalyzer -w -c sample.txt
```
## Sample Output
```bash
 File: sample.txt
 Words: 7
 Chars: 34
```

## Limitations
- Character counting includes newline characters
