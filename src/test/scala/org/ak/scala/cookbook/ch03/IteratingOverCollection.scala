package org.ak.scala.cookbook.ch03

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/29/14 - 10:56 PM
 */
class IteratingOverCollection extends FunSuite with Matchers {
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
}
