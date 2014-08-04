package org.ak.scala.cookbook.ch03

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec

/**
 * @author antonk
 * @since  8/4/14 - 10:05 AM
 */
class CreatingOwnControlStructure extends FunSuite with Matchers with GeneratorDrivenPropertyChecks {
  test("while not structure example") {
    @tailrec
    def whileNot(condition: => Boolean)(body: => Unit) {
      if (!condition) {
        body
        whileNot(condition)(body)
      }
    }


    // ////////////////////

    forAll(Gen.chooseNum(1, 100)) {
      n =>
        var i = 0
        whileNot(i >= n) {
          i += 1
        }


        i shouldEqual n
    }
  }
}
