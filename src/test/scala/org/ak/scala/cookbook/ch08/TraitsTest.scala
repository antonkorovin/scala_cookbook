package org.ak.scala.cookbook.ch08

import org.scalatest.{FunSuite, Matchers}

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



  test("using abstract and concrete fields in traits") {

    trait PizzaTrait {
      var numToppings: Int // abstract
      var size = 14 // concrete
      val maxNumToppings = 10 // concrete
    }


    class Pizza extends PizzaTrait {
      // 'override' not needed
      var numToppings = 10

      // 'var' and 'override' not needed
      size = 16

      // 'override' is required
      override val maxNumToppings = 42
    }


    val p = new Pizza
    p.maxNumToppings shouldEqual 42
    p.numToppings shouldEqual 10
    p.size shouldEqual 16

    p.size += 10
    p.size shouldEqual 26
  }
}
