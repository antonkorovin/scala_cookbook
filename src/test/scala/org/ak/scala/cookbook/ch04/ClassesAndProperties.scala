package org.ak.scala.cookbook.ch04

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  8/5/14 - 10:26 AM
 */
class ClassesAndProperties extends FunSuite with Matchers {
  test("primary constructor") {
    var personsCreated = 0


    class Person(var firstName: String, var lastName: String) {

      // <editor-fold desc="Primary constructor body">

      personsCreated += 1

      // </editor-fold>

      // //////////////////////////////

      override def toString = s"$firstName $lastName"
    }


    personsCreated shouldEqual 0

    val p = new Person("First", "Last")

    personsCreated shouldEqual 1

    p.toString shouldEqual "First Last"

    p.firstName = "NewFirstName"
    p.lastName = "NewLastName"

    p.toString shouldEqual s"${p.firstName} ${p.lastName}"
  }
}
