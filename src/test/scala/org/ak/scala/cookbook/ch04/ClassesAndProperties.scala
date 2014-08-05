package org.ak.scala.cookbook.ch04

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  8/5/14 - 10:26 AM
 */
class ClassesAndProperties extends FunSuite with Matchers {
  test("primary constructor") {
    class Person(var firstName: String, var lastName: String) {
      override def toString = s"$firstName $lastName"
    }


    val p = new Person("First", "Last")

    p.toString shouldEqual "First Last"
  }
}
