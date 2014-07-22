package org.ak.scala.cookbook.ch01.p01

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author antonk
 * @since  7/21/14 - 9:01 PM
 */
class StringsEquality extends FlatSpec with Matchers {
  "A String equality with == operator" should "compare strings content instead references" in {
    val s1 = "Hello, World!"
    val s2 = "Hello, " + "World!"

    s1 shouldEqual s2


    val areEqual = s1 == s2

    areEqual shouldEqual true
  }



  it should "not throw NullPointerException when comparing with null" in {
    val s1 = "NotNullString"
    val s2: String = null
    val s3: String = null


    (s1 == s2) shouldEqual false
    (s2 == s1) shouldEqual false
    (s2 == s3) shouldEqual true
  }



  it should "compare strings content case sensitive" in {
    val s1 = "Hello"
    val s2 = "hello"

    (s1 == s2) shouldEqual false

    s1.equalsIgnoreCase(s2) shouldEqual true
  }
}
