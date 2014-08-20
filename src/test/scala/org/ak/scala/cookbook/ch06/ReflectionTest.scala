package org.ak.scala.cookbook.ch06

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/20/14 - 6:24 PM
 */
class ReflectionTest
  extends FunSuite
          with Matchers {


  test("object casting") {
    val intAsAny: Any = 42
    val intValue: Int = intAsAny.asInstanceOf[Int]

    intValue shouldEqual 42


    intercept[ClassCastException] {
      intAsAny.asInstanceOf[String]
    }
  }


  test("the scala equivalent of java’s .class") {
    "Hello".getClass shouldEqual classOf[String]
  }
}
