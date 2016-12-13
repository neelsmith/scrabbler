#!/bin/sh
exec scala "$0" "$@"
!#

/*
Scala script for exploring ngrams in  a list of words.

Usage: scrabble.scala WORDLISTFILE

WORDLISTFILE is a tab-delimited file in 2 columns, with word lists for a given text on each line:

- column 1 is an identifier for the text.
- column 2 is a comma-delimited list of words

*/

import scala.collection.mutable.ListBuffer
import scala.io.Source


case class StringCount(s: String, count: Int)

// creates a list of substrings of size n in string s
def ngram(n: Int, s: String) = {
  val letters = s.toList
  val slideList = letters.sliding(n).toList
  slideList.map(_.mkString(""))
}

def nextNgram(s: String, wordList: Array[String]) = {
  val longWords = wordList.filter(_.size > s.size)
  val ngramList = longWords.map(w => ngram(s.size,w))
  val matchingNgrams =  ngramList.filter(_.contains(s))

  var nextN = new ListBuffer[String]()
  for (mtch <- matchingNgrams) {
    val idx = mtch.indexOf(s)
    val nextIndex = idx + 1
    if (nextIndex < mtch.size) {
     nextN += mtch(idx)(0) + mtch(nextIndex)
    } else {
     //
    }
  }
  nextN.toVector
}



def prevNgram(s: String, wordList: Array[String]) = {
  val longWords = wordList.filter(_.size > s.size)
  val ngramList = longWords.map(w => ngram(s.size,w))
  val matchingNgrams =  ngramList.filter(_.contains(s))

  var prevN = new ListBuffer[String]()
  for (mtch <- matchingNgrams) {
    val idx = mtch.indexOf(s)
    val prevIndex = idx - 1
    if (prevIndex >= 0) {
     prevN +=   mtch(prevIndex) + mtch(idx).takeRight(1)
    } else {
     //
    }
  }
  prevN.toVector
}

// reads second column of a tab-delimited file,
// parses as a comma-delimited list of words.
def readWordList(fileName: String): Array[String] = {
  val wordsPerText = Source.fromFile(fileName).getLines.toVector
  val wordList = wordsPerText.map(_.split("\t")).map(_(1)).
  mkString
  wordList.split(",").filterNot(_.isEmpty).map(w => "^" + w + "#")
}

def reportNgrams(ngrams: Vector[String]) :  Unit = {
  val grouped = ngrams.groupBy(w => w)
  val counts = grouped.map {
    case (k,v) => StringCount(k, v.size)
  }.toVector
  for (cnt <- counts.sortBy(_.count * -1)) {
    println(cnt.s + " " + cnt.count)
  }
  println("Number matching ngrams: " + counts.size)
  println("Total occurrences: " + counts.map(_.count).sum)
  println("\n")
}


def scrabble(s: String, words: Array[String]) :  Unit = {
  val followingN = nextNgram(s,words)
  val precedingN = prevNgram(s,words)

  println("\nFollowing letters:")
  reportNgrams(followingN)

  println("Preceding letters:")
  reportNgrams(precedingN)

  val fragment = scala.io.StdIn.readLine("Fragment:  ")
  if (fragment.size > 0) {
    scrabble(fragment, words)
  } else {
    println("Done.")
  }
}

object scrabbler {
  def main(args: Array[String]): Unit = {
    if (args.size != 1) {
      println("Usage: scrabble.scala WORDLISTFILE")

    }  else  {
      val dictionary = readWordList(args(0))
      println("Enter a string of characters to look for")
      println("(or just hit the enter key to quit).\n")

      val fragment = scala.io.StdIn.readLine("Fragment:  ")
      if (fragment.size > 0) {
        scrabble(fragment, dictionary)
      } else {
        println("Done.")
      }
    }
  }
}
scrabbler.main(args)
