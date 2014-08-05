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


  test("fields visibility") {
    // ////////////////////////////////

    class Person(
      var varField: String,
      val valField: String,
          defField: String,
      private val privateVal: String,
      private var privateVar: String
    )  {
      def getDefFieldValue = defField

      def getPrivateVar = privateVar
      def setPrivateVar(newValue: String) {privateVar = newValue}

      def getPrivateVal = privateVal
    }

    // ////////////////////////////////


    val p = new Person(
      "var",
      "val",
      "def",
      "privateVal",
      "privateVar"
    )


    p.varField shouldEqual "var"
    p.valField shouldEqual "val"

    // p.defField has no generated mutator/accessor
    // And it can't be reassigned
    p.getDefFieldValue shouldEqual "def"


    // p.privateVal has no generated mutator/accessor
    // And it can't be reassigned
    p.getPrivateVal shouldEqual "privateVal"


    // p.privateVal has no generated mutator/accessor
    p.getPrivateVar shouldEqual "privateVar"

    // But it can be reassigned via handmade method
    p.setPrivateVar("newPrivateVar")
    p.getPrivateVar shouldEqual "newPrivateVar"
  }
}
