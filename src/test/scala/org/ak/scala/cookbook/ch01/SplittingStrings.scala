package org.ak.scala.cookbook.ch01

import org.scalatest.{Matchers, FlatSpec}

/**
 * @author antonk
 * @since  7/23/14 - 12:21 PM
 */
class SplittingStrings extends FlatSpec with Matchers {
  "String" should "be split on field separator (java style)" in {
    val s = "one, two, three"
    val arrOne = s.split(",")

    arrOne should have size 3
    arrOne should contain only ("one", " two", " three")


    val arrTwo = s.split("\\s+")
    arrTwo should have size 3
    arrTwo should contain only ("one,", "two,", "three")
  }


  it should "be split and result should have map, filter and other functions" in {
    val strOne = "one, two, three"
    val arrOne = strOne.split(",").map(_.trim)

    arrOne should have size 3
    arrOne should contain only ("one", "two", "three")


    val strTwo = "1, 2, 3"
    val arrTwo = strTwo.split(",")
                       .map(_.trim)
                       .map(_.toInt)
                       .filter(_ % 2 != 0)

    arrTwo should have size 2
    arrTwo should contain only (1, 3)
  }
}
