package org.ak.scala.cookbook.ch08

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/22/14 - 6:00 PM
 */
class TraitsTest
  extends FunSuite
          with Matchers {

  test("using a trait as an interface") {
    trait CaseModifier {
      def modify(s: String): String
    }

    class UpperCaseModifier extends CaseModifier {
      def modify(s: String) = s.toUpperCase
    }

    class LowerCaseModifier extends CaseModifier {
      def modify(s: String) = s.toLowerCase
    }


    // ////////////////////////////////


    val toUpper = new UpperCaseModifier
    val toLower = new LowerCaseModifier


    toUpper.modify("hElLo") shouldEqual "HELLO"
    toLower.modify("hElLo") shouldEqual "hello"
  }



  test("using abstract and concrete fields in traits") {

    trait PizzaTrait {
      var numToppings: Int
      // abstract
      var size = 14
      // concrete
      val maxNumToppings = 10 // concrete
    }


    class Pizza extends PizzaTrait {
      // 'override' not needed
      var numToppings = 10

      // 'var' and 'override' not needed
      size = 16

      // 'override' is required
      override val maxNumToppings = 42
    }


    val p = new Pizza
    p.maxNumToppings shouldEqual 42
    p.numToppings shouldEqual 10
    p.size shouldEqual 16

    p.size += 10
    p.size shouldEqual 26
  }


  test("using traits as simple mixins") {
    trait Tail {
      def wagTail = "tail is wagging"

      def stopTail = "tail is stopped"
    }

    abstract class Pet(val name: String) {
      def speak: String

      // abstract
      def ownerIsHome = "excited"

      def jumpForJoy = "jumping for joy"
    }


    class Dog(name: String) extends Pet(name) with Tail {
      def speak = "woof"
      override def ownerIsHome = wagTail + " and " + speak
    }


    val ted = new Dog("Ted")
    ted.speak shouldEqual "woof"
    ted.ownerIsHome shouldEqual "tail is wagging and woof"
  }


  test("limiting which classes can use a trait by inheritance") {
    abstract class Employee
    class CorporateEmployee extends Employee
    class StoreEmployee extends Employee

    trait DeliversFood extends StoreEmployee
    trait ServicesCustomers extends CorporateEmployee


    // this is allowed
    class DeliveryPerson extends StoreEmployee with DeliversFood

    // won't compile
    //  class DeliveryPerson extends StoreEmployee with ServicesCustomers


    // this is allowed
    class Receptionist extends CorporateEmployee with ServicesCustomers

    // won't compile
    // class Receptionist extends CorporateEmployee with DeliversFood
  }



  test("marking traits so they can only be used by subclasses of a certain type") {
    class Starship
    trait StarfleetWarpCore {
      this: Starship =>
    }

    class Enterprise extends Starship with StarfleetWarpCore

    class RomulanShip
    // this won't compile:
    // class Warbird extends RomulanShip with StarfleetWarpCore

    // compiler says:
    // self-type Warbird does not conform to
    // StarfleetWarpCore's selftype StarfleetWarpCore with Starship
    // class Warbird extends RomulanShip with StarfleetWarpCore
    //                                        ^
  }


  test("ensuring a trait can only be added to a type that has a specific method") {
    trait WarpCore {
      this: { // structural type
        def ejectWarpCore(password: String): Boolean
        def startWarpCore: String
      } =>
    }

    class Starship
    class Enterprise extends Starship with WarpCore {
      def ejectWarpCore(password: String): Boolean = {
        password == "password"
      }
      def startWarpCore = "core started"
    }


    // this won't compile:
    // class Warbird extends Starship with WarpCore
    //                                     ^

    // compiler says:
    //  self-type Warbird does not conform to
    // WarpCore's selftype
    // WarpCore with AnyRef{def ejectWarpCore(password: String): Boolean;
    // def startWarpCore: String}
  }



  test("adding a trait to an object instance") {
    class SomeClass

    object InstancesCounter {
      var count = 0

      def instancesCount = count
    }

    trait InstancesCounter {
      InstancesCounter.count += 1
    }


    InstancesCounter.instancesCount shouldEqual 0

    new SomeClass with InstancesCounter

    InstancesCounter.instancesCount shouldEqual 1
  }


  test("extending a java interface like a trait") {
    class Foo extends Runnable with java.io.Serializable with AutoCloseable {
      // override isn't necessary
      def run(): Unit = {
        // Do some stuff
      }

      // but it isn't forbidden
      override def close(): Unit = {
        // Do some stuff
      }
    }

    val foo = new Foo
    assert(foo.isInstanceOf[Runnable])
    assert(foo.isInstanceOf[java.io.Serializable])
    assert(foo.isInstanceOf[AutoCloseable])
  }
}
