package org.ak.scala.cookbook.ch03

import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec

/**
 * @author antonk
 * @since  8/4/14 - 10:05 AM
 */
class CreatingOwnControlStructure extends FunSuite with Matchers {
  test("whilst example") {
    @tailrec
    def whileNot(condition: => Boolean)(body: => Unit) {
      if (!condition) {
        body
        whileNot(condition)(body)
      }
    }


    // ////////////////////

    var i = 0

    whileNot(i > 10) {
      i += 1
    }


    i shouldEqual 11
  }
}
