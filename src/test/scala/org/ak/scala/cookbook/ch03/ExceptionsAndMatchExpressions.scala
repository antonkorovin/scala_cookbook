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


  test("catch any type") {
    val e = new Exception("Expected")

    try {
      throw e
    } catch {
      case _: IllegalAccessError =>
        fail("Wrong type of exception")

      case _: IllegalArgumentException =>
        fail("Wrong type of exception")

      case exc: Throwable =>
        exc should be theSameInstanceAs e
    }
  }


  test("declare java compatible throws specification") {
    val e = new Exception("Expected")

    @throws(classOf[Exception])
    def validJavaCall() {
      throw e
    }


    try {
      validJavaCall()
    } catch {
      case exc: Exception =>
        exc should be theSameInstanceAs e
    }
  }
}
