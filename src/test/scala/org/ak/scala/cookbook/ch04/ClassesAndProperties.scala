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


  test("fields visibility in case classes") {
    case class Person(defField: String)


    val p = Person("def")
    // p.defField has generated mutator
    // But it can't be reassigned
    p.defField shouldEqual "def"
  }


  test("add auxiliary constructor") {
    class Person(
      val firstName  : String,
      val middleName : Option[String],
      val lastName   : String
    ) {
      def this(
        firstName : String,
        lastName  : String) {
        this(firstName, None, lastName)
      }
    }


    val p = new Person("first", "last")

    p.middleName shouldEqual None
  }


  // <editor-fold desc="Classes with private constuctors">

  class NoArgConstructor private {
  } /* {} can be replaced with ; */

  object NoArgConstructor {
    def create() = new NoArgConstructor
  }

  class OneArgConstructor private (val name: String)
  object OneArgConstructor {
    def apply(name: String) = new OneArgConstructor(name)
  }

  // </editor-fold>

  test("defining private primary constructor") {
    // 'new NoArgConstructor' produces compile error:
    // constructor NoArgConstructor cannot be accessed

    val noArg = NoArgConstructor.create()
    noArg.isInstanceOf[NoArgConstructor] shouldEqual true

    val oneArg = OneArgConstructor("constructorValue")
    oneArg.isInstanceOf[OneArgConstructor] shouldEqual true
    oneArg.name shouldEqual "constructorValue"
  }


  test("default fields values in constructors") {
    class Socket(
      val timeout : Int = 1000,
      val linger  : Int = 2000
    )


    val socketOne = new Socket()
    socketOne.timeout shouldEqual 1000
    socketOne.linger  shouldEqual 2000

    val socketTwo = new Socket(3000)
    socketTwo.timeout shouldEqual 3000
    socketTwo.linger  shouldEqual 2000

    val socketThree = new Socket(3000, 4000)
    socketThree.timeout shouldEqual 3000
    socketThree.linger  shouldEqual 4000

    val socketFour = new Socket(
      linger  = 5000
    )
    socketFour.timeout shouldEqual 1000
    socketFour.linger  shouldEqual 5000

    val socketFive = new Socket(
      linger  = 6000,
      timeout = 7000
    )

    socketFive.timeout shouldEqual 7000
    socketFive.linger  shouldEqual 6000
  }


  test("overriding accessor and mutator") {
    // error: this won't work
//    class Person(private var name: String) {
//      // this line essentially creates a circular reference
//      def name = name
//      def name_=(aName: String) { name = aName }
//    }


    class Person(private var _name: String) {
      def name = _name
      def name_=(newValue: String) {_name = newValue}
    }


    val p = new Person("SomeName")
    p.name shouldEqual "SomeName"

    p.name = "NewName"
    p.name shouldEqual "NewName"
  }



  test("preventing getter and setter methods from being generated") {
    class CagyPerson(
      val name: String,
      private var age: Int
    ) {
      def isOlderThan(someAge: Int) = age > someAge
      def grow(years: Int) {age += years}
    }


    val p = new CagyPerson("SomeName", 42)

    // p.age has no getter and setter
    p.isOlderThan(52) shouldEqual false

    p.grow(11)
    p.isOlderThan(52) shouldEqual true
  }
}
