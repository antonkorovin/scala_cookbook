package org.ak.scala.cookbook.ch04

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/11/14 - 10:24 PM
 */
class CaseClasses
  extends FunSuite
          with Matchers {

  // 'name' and 'age' are val by default
  case class ConstPerson(
    name: String,
    age: Int
  )


  test("generating accessor for field without val/var keyword") {
    val person = ConstPerson("SomeName", 42)

    person.name shouldEqual "SomeName"
    person.age shouldEqual 42
  }


  test("default toString method") {
    val person = ConstPerson("SomeName", 42)

    person.toString shouldEqual "ConstPerson(SomeName,42)"
  }


  test("using with match") {
    def testNameAndAge(
      p: ConstPerson,
      expName: String,
      expAge: Int
    ) = p match {
      case ConstPerson(name, age)
        if name == expName && age == expAge =>
        true

      case _ =>
        false
    }

    def testAge(
      p: ConstPerson,
      expAge: Int
    ) = p match {
      case ConstPerson(_, age)
        if age == expAge =>
        true

      case _ =>
        false
    }

    // ////////////////////////////////

    val person = ConstPerson("SomeName", 42)

    testNameAndAge(
      person,
      "SomeName",
      42
    ) shouldEqual true

    testNameAndAge(
      person,
      "SomeName",
      24
    ) shouldEqual false


    testAge(
      person,
      42
    ) shouldEqual true

    testAge(
      person,
      24
    ) shouldEqual false
  }



  test("copy method") {
    val person = ConstPerson("SomeName", 42)

    val personCopy = person.copy()

    person shouldEqual personCopy
    person should not be theSameInstanceAs(personCopy)
  }
}
