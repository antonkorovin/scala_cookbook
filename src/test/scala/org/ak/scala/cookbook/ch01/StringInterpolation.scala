package org.ak.scala.cookbook.ch01

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  7/23/14 - 2:44 PM
 */
class StringInterpolation extends FunSuite with Matchers {
  test("interpolation local variables") {
    val oneAsInt = 1
    val oneAsString = "one"

    val str = s"$oneAsInt=$oneAsString"
    str shouldEqual "1=one"
  }



  test("interpolation with expressions") {
    val someString = "Hello"
    val str = s"Length of '$someString' is ${someString.length}. It is ${if (someString.length % 2 == 0) "even" else "odd" }."

    str shouldEqual "Length of 'Hello' is 5. It is odd."
  }



  test("interpolation with printf style") {
    val someString = "Hello"
    val someInt = 42
    val someFloat = 73.37f

    val str = f"Str='$someString%7s' Int=0x$someInt%03X Float=$someFloat%4.1f"

    str shouldEqual "Str='  Hello' Int=0x02A Float=73.4"
  }



  test("raw string interpolation") {
    val str = raw"one\ntwo"
    val expected = "one" + "\\" + "n" + "two"

    str shouldEqual expected
  }




  test("own interpolation with unapplySeq") {

    import StringInterpolationHelper._

    val str = "bar someKey=someValue"

    str match {
      case matches"foo $key=$value" =>
        fail("Shouldn't match.")

      case matches"bar $key=$value" =>
        // Should match successfully.

      case _ =>
        fail("Doesn't match to anything.")
    }


  }


  test("own interpolation with regex") {
    import StringInterpolationHelper._

    def toIpAddress(s: String) = {
      s match {
        case regex"""^([0-9]+)$a[.]([0-9]+)$b[.]([0-9]+)$c[.]([0-9]+)$d$$""" =>
          Some(
            a.toInt,
            b.toInt,
            c.toInt,
            d.toInt
          )

        case _ => None
      }
    }

    val ipAddr = toIpAddress("123.45.67.89")
    ipAddr shouldEqual Some(123, 45, 67, 89)
  }
}
