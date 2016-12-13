# scrabbler

Command-line scripts in scala for playing with word fragments and word lists.

This is derived from Tess Starshak's work analyzing fragmentary texts preserved in the floor tiles from Chertsey Abbey: see [her discussion](https://github.com/tessstarshak/portfolio/wiki).


## Requirements

- [scala](http://www.scala-lang.org/)

## Examples in the `data` directory

The file `crusaderWordList.txt` has word lists from two Crusader chronicles:

- Richard of Devizes' *Chronicles of Richard I* (dated 1192)
- William of Tyre's *A History of Deeds Done Beyond the Sea* (dated between 1170 and 1184)

The file `fragments.txt` is just a hand-typed list of strings for testing the scripts in the `scripts` directory.

## Scripts

### `wordmatch.scala`

Interactive script to find matching words in a given wordlist for strings you enter.  Example: run

    ./scripts/wordmatch.scala data/crusaderWordList.txt

You will be prompted to enter substrings.  In addition to the characters in the text, you may use `^` to indicate word-initial position, and `#` to indicate word-final position.  Enter an empty string (just press return key) to exit.


### `scrabble.scala`

A simple interactive script to find the frequency of in a given corpus of letters following a string you enter. Example: run

    ./scripts/scrabble.scala data/crusaderWordList.txt

You will be prompted to enter a (sub)string.  In addition to the characters in the text, you may use `^` to indicate word-initial position, and `#` to indicate word-final position.  Enter an empty string (just press return key) to exit.

Note that the counts report frequencies of *occurrences* in the given corpus, *not* frequencies of distinct vocabulary items.


### `multiscrabble.scala`
