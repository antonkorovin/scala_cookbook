package org.ak.scala.cookbook.ch02

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/27/14 - 9:39 AM
 */
class NumericOperators extends FunSuite with Matchers {
  test("alternative to ++ and --") {
    var i = 42

    i += 1
    i shouldEqual 43

    i -= 1
    i shouldEqual 42
  }
}
