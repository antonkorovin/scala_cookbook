package org.ak.scala.cookbook.ch01

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/27/14 - 8:23 AM
 */
class ProcessingStringByChar extends FunSuite with Matchers {
  test("using map") {
    val str = "hello"
    val capitalized = str.map(_.toUpper)

    capitalized shouldEqual "HELLO"
  }



  test("using for") {
    val str = "hello"
    val capitalized = for (c <- str) yield c.toUpper

    capitalized shouldEqual "HELLO"
  }
}
