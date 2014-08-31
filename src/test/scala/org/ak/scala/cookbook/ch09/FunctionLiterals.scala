package org.ak.scala.cookbook.ch09

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/29/14 - 11:40 AM
 */
class FunctionLiterals
  extends FunSuite
          with Matchers {

  test("using function literals (anonymous functions)") {
    val x = List.range(1, 10)
    val evens = List.range(start = 2, end = 10, step = 2)

    x.filter((i: Int) => i % 2 == 0) shouldEqual evens
    x.filter(i => i % 2 == 0) shouldEqual evens
    x.filter(_ % 2 == 0) shouldEqual evens

    List(-42.0, 0.0, 42.0).map(Math.signum) shouldEqual List(-1, 0, 1)
  }


  test("using functions as variables") {
    val double = (i: Int) => {
      i * 2
    }

    double(2) shouldEqual 4
    double(4) shouldEqual 8

    List.range(
      1,
      5
    ).map(double) shouldEqual List.range(2, 10, 2)
  }


  test("defining a method that accepts a simple function parameter") {
    def execute[T](t: T, f: T => T) = f(t)

    execute[Int](4, _ * 2) shouldEqual 8
    execute[Int](4, _ / 2) shouldEqual 2
    execute[Int](4, _ + 2) shouldEqual 6


    def execute2[T](
      t1: T,
      t2: T,
      f: (T, T) => T
    ) = f(t1, t2)


    execute2[Int](
      2,
      3,
      _ * _
    ) shouldEqual 6

    execute2[Double](
      2,
      3,
      Math.pow
    ) shouldEqual 8
  }


  test("using closures") {
    object OtherScope {

      class Foo {
        // a method that takes a function and a string, and passes the string into
        // the function, and then executes the function
        def exec(f: (String) => String, name: String) = {
          f(name)
        }
      }

    }

    object ClosureExample {
      var greeting = "Hello"

      def sayHello(name: String) = {
        s"$greeting, $name"
      }
    }

    // execute sayHello from the exec method foo
    val foo = new OtherScope.Foo
    foo.exec(
      ClosureExample.sayHello,
      "Al"
    ) shouldEqual "Hello, Al"

    // change the local variable 'greeting', then execute sayHello from
    // the exec method of foo, and see what happens
    ClosureExample.greeting = "Hola"
    foo.exec(
      ClosureExample.sayHello,
      "Lorenzo"
    ) shouldEqual "Hola, Lorenzo"
  }



  test("using partially applied functions") {
    def wrap(
      prefix: String,
      html: String,
      suffix: String
    ) = {
      prefix + html + suffix
    }


    val wrapWithDiv = wrap("<div>", _: String, "</div>")
    wrapWithDiv(
      "<p>Hello, world</p>"
    ) shouldEqual "<div><p>Hello, world</p></div>"
  }



  test("creating a function that returns a function") {
    def saySomething(prefix: String) = (s: String) => {
      prefix + " " + s
    }

    val sayHello = saySomething("Hello")

    sayHello(
      "Al"
    ) shouldEqual "Hello Al"



    // //////////////////////////////////////////


    def greeting(language: String) = (name: String) => {
      val english = () => "Hello, " + name
      val spanish = () => "Buenos dias, " + name

      language match {
        case "english" => english()
        case "spanish" => spanish()
      }
    }


    val hello = greeting("english")
    val buenosDias = greeting("spanish")

    hello("Al") shouldEqual "Hello, Al"
    buenosDias("Lorenzo") shouldEqual "Buenos dias, Lorenzo"
  }



  test("creating partial functions") {
    val divideAsMatch: PartialFunction[Int, Int] = {
      case d: Int if d != 0 => 42 / d
    }

    val divideAsSubclass = new PartialFunction[Int, Int] {
      def apply(d: Int) = 42 / d

      def isDefinedAt(d: Int) = d != 0
    }

    intercept[MatchError] {
      List(0, 1, 2) map divideAsMatch
    }

    intercept[ArithmeticException] {
      List(0, 1, 2) map divideAsSubclass
    }



    List(
      0, 1, 2
    ) collect divideAsMatch shouldEqual List(42, 21)

    List(
      0, 1, 2
    ) collect divideAsSubclass shouldEqual List(42, 21)
  }



  test("using 'orElse' and 'andThen'") {
    // converts 1 to "one", etc., up to 5
    val convert1to5 = new PartialFunction[Int, String] {
      val nums = Array("one", "two", "three", "four", "five")

      def apply(i: Int) = nums(i - 1)

      def isDefinedAt(i: Int) = i > 0 && i < 6
    }
    // converts 6 to "six", etc., up to 10
    val convert6to10 = new PartialFunction[Int, String] {
      val nums = Array("six", "seven", "eight", "nine", "ten")

      def apply(i: Int) = nums(i - 6)

      def isDefinedAt(i: Int) = i > 5 && i < 11
    }


    val handle1to10 = convert1to5 orElse convert6to10

    handle1to10(2) shouldEqual "two"
    handle1to10(8) shouldEqual "eight"


    // //////////////////////

    val absolute: PartialFunction[Double, Double] = {
      case x: Double if x < 0 => -x
      case x: Double => x
    }

    val sqrt: PartialFunction[Double, Double] = {
      case x: Double if x < 0 => Double.NaN
      case x: Double => Math.sqrt(x)
    }


    // List.map(sqrt) returns List(NaN, NaN, 0.0, 1.0, 2.0)
    List(
      -4.0, -1.0, 0.0, 1.0, 4.0
    ).map(
        absolute andThen sqrt
      ) shouldEqual List(2.0, 1.0, 0.0, 1.0, 2.0)
  }
}
