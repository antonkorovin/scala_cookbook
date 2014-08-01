package org.ak.scala.cookbook.ch03

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  7/31/14 - 3:38 PM
 */
class MatchExpression extends FunSuite with Matchers with GeneratorDrivenPropertyChecks {
  test("simple matching with default case as variable") {

    def monthNameWithDefault(i: Int) = {
      i match {
        case 1 => "January"
        case 2 => "February"
        case 3 => "March"
        case 4 => "April"
        case 5 => "May"
        case 6 => "June"
        case 7 => "July"
        case 8 => "August"
        case 9 => "September"
        case 10 => "October"
        case 11 => "November"
        case 12 => "December"

        // catch the default with a variable so you can print it
        case whoa => s"Unexpected case: $whoa"
      }
    }


    val month = 6
    val expectedMonthName = "June"

    val monthName = monthNameWithDefault(month)
    monthName shouldEqual expectedMonthName


    forAll(Gen.chooseNum(Int.MinValue, Int.MaxValue)) {
      unexpectedMonth =>
        whenever(unexpectedMonth < 1 || unexpectedMonth > 12) {
          monthNameWithDefault(unexpectedMonth) shouldEqual s"Unexpected case: $unexpectedMonth"
        }
    }
  }



  test("simple matching with default case as wildcard") {
    def monthNameWithDefault2(i: Int) = {
      i match {
        case 1 => "January"
        case 2 => "February"
        case 3 => "March"
        case 4 => "April"
        case 5 => "May"
        case 6 => "June"
        case 7 => "July"
        case 8 => "August"
        case 9 => "September"
        case 10 => "October"
        case 11 => "November"
        case 12 => "December"

        // the default, catch-all
        case _ => "Invalid month"
      }
    }


    val month = 6
    val expectedMonthName = "June"

    val monthName = monthNameWithDefault2(month)
    monthName shouldEqual expectedMonthName


    forAll(Gen.chooseNum(Int.MinValue, Int.MaxValue)) {
      unexpectedMonth =>
        whenever(unexpectedMonth < 1 || unexpectedMonth > 12) {
          monthNameWithDefault2(unexpectedMonth) shouldEqual "Invalid month"
        }
    }
  }


  test("simple matching with 'tableswitch' optimisation") {
    def monthNameWithSwitchAnnotation(i: Int) = {

      import scala.annotation.switch

      (i: @switch) match {
        case 1 => "January"
        case 2 => "February"
        case 3 => "March"
        case 4 => "April"
        case 5 => "May"
        case 6 => "June"
        case 7 => "July"
        case 8 => "August"
        case 9 => "September"
        case 10 => "October"
        case 11 => "November"
        case 12 => "December"

        // the default, catch-all
        case _ => "Invalid month"
      }
    }

    val month = 6
    val expectedMonthName = "June"

    val monthName = monthNameWithSwitchAnnotation(month)
    monthName shouldEqual expectedMonthName


    forAll(Gen.chooseNum(Int.MinValue, Int.MaxValue)) {
      unexpectedMonth =>
        whenever(unexpectedMonth < 1 || unexpectedMonth > 12) {
          monthNameWithSwitchAnnotation(unexpectedMonth) shouldEqual "Invalid month"
        }
    }
  }



  test("simple matching without default case") {
    def monthNameWithoutDefault(i: Int) = {
      i match {
        case 1 => "January"
        case 2 => "February"
        case 3 => "March"
        case 4 => "April"
        case 5 => "May"
        case 6 => "June"
        case 7 => "July"
        case 8 => "August"
        case 9 => "September"
        case 10 => "October"
        case 11 => "November"
        case 12 => "December"
      }
    }

    val month = 6
    val expectedMonthName = "June"

    val monthName = monthNameWithoutDefault(month)
    monthName shouldEqual expectedMonthName


    forAll(Gen.chooseNum(Int.MinValue, Int.MaxValue)) {
      unexpectedMonth =>
        whenever(unexpectedMonth < 1 || unexpectedMonth > 12) {
          intercept[MatchError] {
            monthNameWithoutDefault(unexpectedMonth)
          }
        }
    }
  }


  test("simple matching multiple conditions with one case") {
    def isOddOrEven(i: Int) = i match {
      case 1 | 3 | 5 | 7 | 9 => "odd"
      case 2 | 4 | 6 | 8 | 10 => "even"
    }

    // //////////////////////


    forAll(Gen.oneOf(1 to 9 by 2)) {
      n =>
        isOddOrEven(n) shouldEqual "odd"
    }


    forAll(Gen.oneOf(2 to 10 by 2)) {
      n =>
        isOddOrEven(n) shouldEqual "even"
    }
  }
}
