package org.ak.scala.cookbook.ch02

import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

/**
 * @author antonk
 * @since  7/28/14 - 10:43 PM
 */
class Ranges extends FunSuite with Matchers {
  test("create it with 'to'") {
    var sum = 0
    // Inclusive
    for (i <- 1 to 10) {
      sum += 1
    }

    sum shouldEqual 10
  }



  test("create it with 'until'") {
    var sum = 0
    // Exclusive
    for (i <- 1 until 10) {
      sum += 1
    }

    sum shouldEqual 9
  }



  test("create it with specific step") {
    var sum = 0
    for (i <- 1 to 10 by 2) {
      sum += 1
    }

    sum shouldEqual 5
  }



  test("using as collection") {
    val begin = Random.nextInt(10)
    val end   = begin + Random.nextInt(10)

    val r = begin to end

    val expectedSum = sumOfArithmeticProgression(begin, end, r.length)
    val sum = r.foldLeft(0)(_ + _)

    sum shouldEqual expectedSum
  }



  private def sumOfArithmeticProgression(begin: Int, end: Int, length: Int) = (begin + end) * length / 2
}
