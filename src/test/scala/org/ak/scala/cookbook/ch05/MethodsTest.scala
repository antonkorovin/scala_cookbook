package org.ak.scala.cookbook.ch05

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/15/14 - 12:37 PM
 */
class MethodsTest
  extends FunSuite
          with Matchers
          with GeneratorDrivenPropertyChecks {

  test("calling a method on a superclass") {
    trait BaseMember {
      def role = "NoRole"
    }
    trait Member {
      def role = "MemberRole"
    }
    trait Mother extends Member {
      override def role = "MotherRole"
    }
    trait Father extends Member {
      override def role = "FatherRole"
    }

    class Child
      extends Member
              with Mother
              with Father {
      override def role = "ChildRole"

      // def baseRole = super[BaseMember].role
      // Compile error: BaseMember does not name
      // a parent class of class Child
      // to avoid this add "with BaseMember"

      def memberRole = super[Member].role
      def motherRole = super[Mother].role
      def fatherRole = super[Father].role
    }


    // ////////////////////////////////


    val child = new Child
    child.role shouldEqual "ChildRole"
    child.motherRole shouldEqual "MotherRole"
    child.fatherRole shouldEqual "FatherRole"
    child.memberRole shouldEqual "MemberRole"
  }




  test("creating methods that take variable-argument fields") {
    def sum(args: Int*) = {
      args.sum
    }


    sum() shouldEqual 0
    sum(1, 2, 3, 4) shouldEqual 10
    sum(
      List(1, 2, 3, 4): _*
    ) shouldEqual 10
  }


  test("supporting a fluent style of programming") {
    class Person {
      protected var firstName = ""
      protected var lastName = ""

      // explicitly setting this.type as the return type
      def setFirstName(name: String): this.type = {
        firstName = name
        this
      }


      // explicitly setting this.type as the return type
      def setLastName(name: String): this.type = {
        lastName = name
        this
      }
    }

    class Employee extends Person {
      protected var role = ""

      // explicitly setting this.type as the return type
      def setRole(role: String): this.type = {
        this.role = role
        this
      }

      override def toString = {
        "%s, %s, %s".format(firstName, lastName, role)
      }
    }


    // ////////////////////////////////////////////////////


    val e = new Employee
    e.setFirstName("firstNameValue")
      .setLastName("lastNameValue")
      .setRole("roleValue")

    e.toString shouldEqual "firstNameValue, lastNameValue, roleValue"

  }
}
