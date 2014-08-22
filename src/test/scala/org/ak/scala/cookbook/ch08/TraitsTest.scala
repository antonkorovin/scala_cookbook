package org.ak.scala.cookbook.ch08

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  8/22/14 - 6:00 PM
 */
class TraitsTest
  extends FunSuite
          with Matchers {

  test("using a trait as an interface") {
    trait CaseModifier {
      def modify(s: String): String
    }

    class UpperCaseModifier extends CaseModifier {
      def modify(s: String) = s.toUpperCase
    }

    class LowerCaseModifier extends CaseModifier {
      def modify(s: String) = s.toLowerCase
    }


    // ////////////////////////////////


    val toUpper = new UpperCaseModifier
    val toLower = new LowerCaseModifier


    toUpper.modify("hElLo") shouldEqual "HELLO"
    toLower.modify("hElLo") shouldEqual "hello"
  }
}
