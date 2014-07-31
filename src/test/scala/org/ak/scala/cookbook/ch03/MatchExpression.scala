package org.ak.scala.cookbook.ch03

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  7/31/14 - 3:38 PM
 */
class MatchExpression extends FunSuite with Matchers with GeneratorDrivenPropertyChecks {
  test("simple matching") {
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



  test("simple matching 2") {
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



  private def monthNameWithDefault(i: Int) = {
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


  private def monthNameWithDefault2(i: Int) = {
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
}
