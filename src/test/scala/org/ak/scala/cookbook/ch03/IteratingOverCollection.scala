package org.ak.scala.cookbook.ch03

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

import scala.util.control.Breaks
import scala.util.control.Breaks._

/**
 * @author antonk
 * @since  7/29/14 - 10:56 PM
 */
class IteratingOverCollection
  extends FunSuite
  with Matchers
  with GeneratorDrivenPropertyChecks {
  test("iterating with for") {
    val arr = (1 to 5).toArray

    var sum = 0
    for (element <- arr) {
      sum += element
    }


    sum shouldEqual arr.sum
  }



  test("iterating with for with indexes") {
    val arr = (1 to 5).toArray

    var sum = 0
    for (index <- 0 until arr.length) {
      sum += arr(index)
    }


    sum shouldEqual arr.sum
  }



  test("iterating with foreach") {
    val arr = (1 to 5).toArray

    var sum = 0
    arr.foreach(sum += _)


    sum shouldEqual arr.sum
  }


  test("for comprehensions") {
    val arr = (1 to 5).toArray
    val expectedArray = Array("1", "2", "3", "4", "5")

    val strArr = for (element <- arr) yield element.toString

    strArr shouldEqual expectedArray
  }


  test("for with guards") {
    val arr = (1 to 10).toArray
    val expectedArray = Array("2", "4", "6", "8", "10")

    val strArr = for {
      element <- arr
      if element % 2 == 0
    } yield element.toString

    strArr shouldEqual expectedArray
  }


  test("for with multiple counters") {
    val sb = new StringBuilder
    for {
      i <- 2 to 9
      j <- 2 to 9
    } {
      sb.append(s"$i * $j = ${i * j}")
      sb.append("\n")
    }

    val expected =
      """2 * 2 = 4
        |2 * 3 = 6
        |2 * 4 = 8
        |2 * 5 = 10
        |2 * 6 = 12
        |2 * 7 = 14
        |2 * 8 = 16
        |2 * 9 = 18
        |3 * 2 = 6
        |3 * 3 = 9
        |3 * 4 = 12
        |3 * 5 = 15
        |3 * 6 = 18
        |3 * 7 = 21
        |3 * 8 = 24
        |3 * 9 = 27
        |4 * 2 = 8
        |4 * 3 = 12
        |4 * 4 = 16
        |4 * 5 = 20
        |4 * 6 = 24
        |4 * 7 = 28
        |4 * 8 = 32
        |4 * 9 = 36
        |5 * 2 = 10
        |5 * 3 = 15
        |5 * 4 = 20
        |5 * 5 = 25
        |5 * 6 = 30
        |5 * 7 = 35
        |5 * 8 = 40
        |5 * 9 = 45
        |6 * 2 = 12
        |6 * 3 = 18
        |6 * 4 = 24
        |6 * 5 = 30
        |6 * 6 = 36
        |6 * 7 = 42
        |6 * 8 = 48
        |6 * 9 = 54
        |7 * 2 = 14
        |7 * 3 = 21
        |7 * 4 = 28
        |7 * 5 = 35
        |7 * 6 = 42
        |7 * 7 = 49
        |7 * 8 = 56
        |7 * 9 = 63
        |8 * 2 = 16
        |8 * 3 = 24
        |8 * 4 = 32
        |8 * 5 = 40
        |8 * 6 = 48
        |8 * 7 = 56
        |8 * 8 = 64
        |8 * 9 = 72
        |9 * 2 = 18
        |9 * 3 = 27
        |9 * 4 = 36
        |9 * 5 = 45
        |9 * 6 = 54
        |9 * 7 = 63
        |9 * 8 = 72
        |9 * 9 = 81
        |""".stripMargin

    sb.toString shouldEqual expected
  }



  test("for with multiple counters and multidimensional arrays ") {
    val arr = Array.ofDim[Int](8, 8)
    for {
      row <- 2 to 9
      col <- 2 to 9
    } {
      arr(row - 2)(col - 2) = row * col
    }


    val doubleIndexes =
      for {
        row <- Gen.choose(2, 9)
        col <- Gen.choose(2, 9)
      } yield (row, col)


    forAll(doubleIndexes) {
      (doubleIndex) =>
        val (row, col) = doubleIndex

        arr(row - 2)(col - 2) shouldEqual (row * col)
    }
  }



  test("break example") {

    breakable {
      var found = 0
      for (i <- 1 to 10) {
        if (i > 4) {
          found = i
          break()
        } // break out of the for loop
      }


      found shouldEqual 5
    }

  }



  test("labeled break example") {
    val inner = new Breaks
    val outer = new Breaks


    var foundOuter = 0
    var foundInner = '\u0000'

    outer.breakable {
      for (i <- 1 to 5) {
        inner.breakable {
          for (j <- 'a' to 'e') {
            if (i == 1 && j == 'c') {
              foundOuter = i
              foundInner = j

              inner.break()
            }

            if (i == 2 && j == 'b') outer.break()
          }
        }
      }
    }


    foundInner shouldEqual 'c'
    foundOuter shouldEqual 1
  }
}
