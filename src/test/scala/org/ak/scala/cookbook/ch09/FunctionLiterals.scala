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

  }
}
