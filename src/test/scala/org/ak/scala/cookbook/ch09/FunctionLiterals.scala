package org.ak.scala.cookbook.ch09

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/29/14 - 11:40 AM
 */
class FunctionLiterals
  extends FunSuite
          with Matchers {

  test("using function literals (anonymous functions)") {
    val x = List.range(1, 10)
    val evens = List.range(start = 2, end = 10, step = 2)

    x.filter((i: Int) => i % 2 == 0) shouldEqual evens
    x.filter(i => i % 2 == 0) shouldEqual evens
    x.filter(_ % 2 == 0) shouldEqual evens

    List(-42.0 , 0.0, 42.0).map(Math.signum) shouldEqual List(-1, 0, 1)
  }
}
