package org.ak.scala.cookbook.ch01

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  7/23/14 - 2:44 PM
 */
class StringInterpolation extends FunSuite with Matchers {
  test("interpolation local variables") {
    val oneAsInt = 1
    val oneAsString = "one"

    val str = s"$oneAsInt=$oneAsString"
    str shouldEqual "1=one"
  }



  test("interpolation with expressions") {
    val someString = "Hello"
    val str = s"Length of '$someString' is ${someString.length}. It is ${if (someString.length % 2 == 0) "even" else "odd" }."

    str shouldEqual "Length of 'Hello' is 5. It is odd."
  }
}
