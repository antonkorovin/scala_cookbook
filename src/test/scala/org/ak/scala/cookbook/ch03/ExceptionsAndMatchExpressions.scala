package org.ak.scala.cookbook.ch03

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  8/2/14 - 8:59 PM
 */
class ExceptionsAndMatchExpressions extends FunSuite with Matchers {
  test("throw and catch") {
    val e = new Exception("Expected")

    try {
      throw e
    } catch {
      case exc: Exception =>
        exc should be theSameInstanceAs e
    }
  }
}
