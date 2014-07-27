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


  test("using BigInt for very large numbers") {
    val i1 = BigInt(Long.MaxValue)
    val i2 = BigInt(Long.MaxValue)

    (i1 * i2) shouldEqual BigInt("85070591730234615847396907784232501249")

  }
}
