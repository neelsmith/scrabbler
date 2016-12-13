#!/bin/sh
exec scala "$0" "$@"
!#

import scala.io.Source

// reads a comma-delimited list of words from a file
def readWordList(fileName: String): Array[String] = {
  val wordList = Source.fromFile(fileName).getLines.mkString
  wordList.split(",").filterNot(_.isEmpty).map(w => "^" + w + "#")
}

def wordMatch(s: String, words : Array[String]) :  Unit = {
  val fullwords = words.filter(_.contains(s))
  for (w <- fullwords) {
    println("  " + w)
  }
  println("Total matches: " + fullwords.size)
  println("\n")

  val fragment = scala.io.StdIn.readLine("Fragment:  ")
  if (fragment.size > 0) {
    wordMatch(fragment, words)
  } else {
    println("Done.")
  }
}

object wordMatcher {
  def main(args: Array[String]): Unit = {
    if (args.size != 1) {
      println("Usage: wordmatch.scala WORDLISTFILE")

    }  else  {
      val dictionary = readWordList(args(0))
      val fragment = scala.io.StdIn.readLine("\nFragment:  ")
      if (fragment.size > 0) {
        wordMatch(fragment, dictionary)
      } else {
        println("\nDone.")
      }
    }
  }
}
wordMatcher.main(args)
