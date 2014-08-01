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



  test("pattern matching in match expressions") {
    case class Person(first: String, last: String)
    case class Dog(name: String)

    def echoWhatYouGaveMe(x: Any): String = x match {
      // constant patterns
      case 0 => "zero"
      case true => "true"
      case "hello" => "you said 'hello'"
      case Nil => "an empty List"


      // sequence patterns
      case List(0, _, _) => "a three-element list with 0 as the first element"
      case List(1, _*) => "a list beginning with 1, having any number of elements"
      case Vector(1, _*) => "a vector starting with 1, having any number of elements"


      // tuples
      case (one, two) => s"got $one and $two"
      case (one, two, three) => s"got $one, $two, and $three"


      // constructor patterns
      case Person(first, "Alexander") => s"found an Alexander, first name = $first"
      case Dog("Suka") => "found a dog named Suka"


      // typed patterns
      case s: String => s"you gave me this string: $s"
      case i: Int => s"thanks for the int: $i"
      case f: Float => s"thanks for the float: $f"
      case a: Array[_] => s"an array : ${a.mkString(",")}"
      case d: Dog => s"dog: ${d.name}"
      case list: List[_] => s"thanks for the List: $list"
      case m: Map[_, _] => String.valueOf(m)

      // the default wildcard pattern
      case _ => "Unknown"
    }


    // //////////////////////


    // constant patterns
    echoWhatYouGaveMe(0) shouldEqual "zero"
    echoWhatYouGaveMe(true) shouldEqual "true"
    echoWhatYouGaveMe("hello") shouldEqual "you said 'hello'"

    // sequence patterns
    echoWhatYouGaveMe(Nil) shouldEqual "an empty List"
    echoWhatYouGaveMe(List(0, 1, 2)) shouldEqual "a three-element list with 0 as the first element"
    echoWhatYouGaveMe(List(1, 0, 2)) shouldEqual "a list beginning with 1, having any number of elements"
    echoWhatYouGaveMe(Vector(1, 0, 2)) shouldEqual "a vector starting with 1, having any number of elements"

    // tuples
    echoWhatYouGaveMe(("one", 2)) shouldEqual "got one and 2"
    echoWhatYouGaveMe((1, "two", 3.0)) shouldEqual "got 1, two, and 3.0"

    // constructor patterns
    echoWhatYouGaveMe(Person("Richard", "Alexander")) shouldEqual "found an Alexander, first name = Richard"
    echoWhatYouGaveMe(Dog("Suka")) shouldEqual "found a dog named Suka"


    // typed patterns
    echoWhatYouGaveMe("Hello") shouldEqual "you gave me this string: Hello"
    echoWhatYouGaveMe(42) shouldEqual "thanks for the int: 42"
    echoWhatYouGaveMe(73.37F) shouldEqual "thanks for the float: 73.37"
    echoWhatYouGaveMe((1 to 9 by 2).toArray) shouldEqual "an array : 1,3,5,7,9"
    echoWhatYouGaveMe(Dog("Another")) shouldEqual "dog: Another"
    echoWhatYouGaveMe(List("one", "two")) shouldEqual "thanks for the List: List(one, two)"
    echoWhatYouGaveMe(Map("one" -> 1, "two" -> 2)) shouldEqual "Map(one -> 1, two -> 2)"

    echoWhatYouGaveMe(None) shouldEqual "Unknown"
  }



  test("adding variables to patterns") {
    def startsWithOneAndHasLength3(any: Any) = {
      any match {
        // a variable-binding pattern
        case list @ List(1, _*) =>
          list.size == 3
        case _ =>
          false
      }
    }

    // //////////////////////


    startsWithOneAndHasLength3(List(1, 2, 3)) shouldEqual true
    startsWithOneAndHasLength3(List(2, 2, 3)) shouldEqual false
    startsWithOneAndHasLength3(List(1, 2, 3, 4)) shouldEqual false
    startsWithOneAndHasLength3(None) shouldEqual false
  }



  test("if guards in pattern matching") {
    def description(i: Any) = i match {
      case negative: Int if negative < 0 =>
        "negative"

      case number: Int if number == 0 =>
        "zero"

      case positive: Int if positive > 0 =>
        "positive"
    }


    forAll(Gen.choose(Int.MinValue, -1)) {
      i =>
        description(i) shouldEqual "negative"
    }

    description(0) shouldEqual "zero"


    forAll(Gen.choose(0, Int.MaxValue)) {
      i =>
        description(i) shouldEqual "positive"
    }
  }
}
