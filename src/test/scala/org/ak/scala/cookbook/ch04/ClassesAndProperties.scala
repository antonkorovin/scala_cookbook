package org.ak.scala.cookbook.ch04

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/5/14 - 10:26 AM
 */
class ClassesAndProperties
  extends FunSuite
  with Matchers
  with GeneratorDrivenPropertyChecks {


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
    case class Socket(
      timeout : Int = 1000,
      linger  : Int = 2000
    )


    val socketOne = Socket()
    socketOne.timeout shouldEqual 1000
    socketOne.linger  shouldEqual 2000

    val socketTwo = Socket(3000)
    socketTwo.timeout shouldEqual 3000
    socketTwo.linger  shouldEqual 2000

    val socketThree = Socket(3000, 4000)
    socketThree.timeout shouldEqual 3000
    socketThree.linger  shouldEqual 4000

    val socketFour = Socket(
      linger = 5000
    )
    socketFour.timeout shouldEqual 1000
    socketFour.linger  shouldEqual 5000

    val socketFive = Socket(
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



  test("assigning a field to a block or function") {
    case class Foo(intValue: Int) {
      val desc = {
        if (intValue < 0) {
          "negative"
        } else if (intValue > 0) {
          "positive"
        } else
          "zero"
      }
    }


    forAll(Gen.negNum[Int]) {
      num =>
        val foo = Foo(num)

        foo.intValue should be < 0
        foo.desc shouldEqual "negative"
    }


    forAll(Gen.posNum[Int]) {
      num =>
        val foo = Foo(num)

        foo.intValue should be > 0
        foo.desc shouldEqual "positive"
    }


    val foo = Foo(0)

    foo.intValue shouldEqual 0
    foo.desc shouldEqual "zero"
  }



  test("handling constructor parameters when extending a class") {
    class Person(val name: String, var age: Int)
    class Employee(name: String, age: Int, var dept: String) extends Person(name, age)


    val e = new Employee("EmployeeName", 42, "IT")

    e.name shouldEqual "EmployeeName"
    e.age  shouldEqual 42
    e.dept shouldEqual "IT"

    e.age += 10
    e.age  shouldEqual 52



    val p: Person = e
    p.name shouldEqual "EmployeeName"

    p.age -= 5
    p.age  shouldEqual 47
  }



  test("calling a superclass constructor") {
    class Parent(val name: String, val age: Int) {
      def this(name: String) {
        this(name, 0)
      }
    }

    class Child(name: String) extends Parent(name) {
      // This is a bit of a trick question, because you can control the superclass constructor that’s
      // called by the primary constructor in a subclass, but you can’t control the superclass
      // constructor that’s called by an auxiliary constructor in the subclass. (c) Alvin Alexander
      //      def this(name: String, age: Int) {
      //        super(name, age)
      //      }
    }


    val c = new Child("ChildName")
    c.name shouldEqual "ChildName"
    c.age shouldEqual 0
  }



  test("using abstract class instead of trait") {
    // There are two reasons
    // • You want to create a base class that requires constructor arguments.
    // • The code will be called from Java code.
    // (c) Alvin Alexander


    abstract class Animal(name: String) {

      def say: String

      override def toString = {s"$name says '$say'"}
    }


    class Dog(name: String) extends Animal(name) {
      def say = "Woof"
    }


    val dog: Animal = new Dog("Ted")

    dog.toString shouldEqual "Ted says 'Woof'"
  }
}
