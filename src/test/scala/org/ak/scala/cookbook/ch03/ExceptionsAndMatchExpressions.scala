package org.ak.scala.cookbook.ch03

import java.io.{File, PrintWriter}
import java.util.Scanner

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/2/14 - 8:59 PM
 */
class ExceptionsAndMatchExpressions extends FunSuite with Matchers {
  test("throw and catch") {
    val e = new Exception("Expected")

    try {
      throw e
    } catch {
      case exc: Exception =>
        exc should be theSameInstanceAs e
    }
  }


  test("catch any type") {
    val e = new Exception("Expected")

    try {
      throw e
    } catch {
      case _: IllegalAccessError =>
        fail("Wrong type of exception")

      case _: IllegalArgumentException =>
        fail("Wrong type of exception")

      case exc: Throwable =>
        exc should be theSameInstanceAs e
    }
  }


  test("declare java compatible throws specification") {
    val e = new Exception("Expected")

    @throws(classOf[Exception])
    def validJavaCall() {
      throw e
    }


    try {
      validJavaCall()
    } catch {
      case exc: Exception =>
        exc should be theSameInstanceAs e
    }
  }


  test("declaring a variable before using it in a try/catch/finally block") {

    val file = File.createTempFile("scalaCookbook", "exceptions")
    file.deleteOnExit()

    val testData = "Hello"

    var out = None: Option[PrintWriter]
    try {
      out = Some(new PrintWriter(file))
      out.foreach {
        _.println(testData)
      }
    } finally {
      out.foreach(_.close())
    }



    var in = None: Option[Scanner]
    try {
      in = Some(new Scanner(file))
      val lines = in.map(_.nextLine()).toArray

      lines should have size 1
      lines shouldEqual Array(testData)

    } finally {
      in.foreach(_.close())
    }
  }
}
