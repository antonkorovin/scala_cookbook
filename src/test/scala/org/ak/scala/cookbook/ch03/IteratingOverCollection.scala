package org.ak.scala.cookbook.ch03

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/29/14 - 10:56 PM
 */
class IteratingOverCollection extends FunSuite with Matchers {
  test("iterating with for") {
    val arr = Array(1, 2, 3, 4, 5)

    var sum = 0
    for (element <- arr) {
      sum += element
    }


    sum shouldEqual arr.sum
  }



  test("iterating with for with indexes") {
    val arr = Array(1, 2, 3, 4, 5)

    var sum = 0
    for (index <- 0 until arr.length) {
      sum += arr(index)
    }


    sum shouldEqual arr.sum
  }



  test("iterating with foreach") {
    val arr = Array(1, 2, 3, 4, 5)

    var sum = 0
    arr.foreach(sum += _)


    sum shouldEqual arr.sum
  }
}
