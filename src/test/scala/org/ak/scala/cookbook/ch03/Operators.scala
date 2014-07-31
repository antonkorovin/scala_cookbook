package org.ak.scala.cookbook.ch03

import org.scalatest.{Matchers, FunSuite}

import scala.util.Random

/**
 * @author antonk
 * @since  7/31/14 - 2:51 PM
 */
class Operators extends FunSuite with Matchers {
  test("ternary operator") {
    val negativeValue = 0 - (1 + Random.nextInt(100))

    val positiveValue = if (negativeValue < 0) -negativeValue else negativeValue


    positiveValue shouldEqual Math.abs(negativeValue)
  }
}
