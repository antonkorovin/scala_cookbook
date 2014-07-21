package org.ak.scala.cookbook.ch0101

import org.scalatest.{Matchers, FlatSpec}

/**
 * @author antonk
 * @since  7/21/14 - 9:01 PM
 */
class StringsTest extends FlatSpec with Matchers {
  "A String" should "be compared with == operator" in {
    val s1 = "Hello, World!"
    val s2 = "Hello, " + "World!"

    s1 shouldEqual s2


    val areEqual = s1 == s2

    areEqual shouldEqual true
  }
}
