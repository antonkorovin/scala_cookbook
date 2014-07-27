package org.ak.scala.cookbook.ch02

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/27/14 - 9:01 AM
 */
class CastingNumericTypes extends FunSuite with Matchers {
  test("simple casting") {
    val intValue: Int = 42
    val floatValue: Float = intValue.toFloat

    floatValue shouldEqual 42.0F
  }



  test("checking values") {
    val intValue: Int = Int.MaxValue

    intValue.isValidInt shouldEqual true
    intValue.isValidByte shouldEqual false
    intValue.isValidShort shouldEqual false
  }



  test("using type ascription") {
    val shortValue = 0: Short

    shortValue.getClass shouldEqual classOf[Short]
  }
}
