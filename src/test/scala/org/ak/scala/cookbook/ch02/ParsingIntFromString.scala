package org.ak.scala.cookbook.ch02

import org.ak.scala.cookbook.StringUtils
import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/27/14 - 8:44 AM
 */
class ParsingIntFromString extends FunSuite with Matchers {
  test("using String methods") {
    "123".toInt shouldEqual 123
  }


  test("using method with radix") {
    import StringUtils._;

    "0xff".toInt(16) shouldEqual 255
    "0XFF".toInt(16) shouldEqual 255
    "FF".toInt(16) shouldEqual 255
  }
}
