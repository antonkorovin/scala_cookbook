package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  9/4/14 - 1:07 PM
 */
class ChoosingCollectionClassResearch
  extends FunSuite
          with Matchers {

  test("choosing an indexed immutable sequence") {
    val vec = Vector(2, 4, 8, 16, 32)

    // TODO: Check performance
    vec(3) shouldEqual 16

    // TODO: Check performance
    // Uses `apply(0)`
    vec.head shouldEqual 2

    // TODO: Check performance
    // Uses `drop(1)`
    vec.tail shouldEqual Vector(4, 8, 16, 32)


    // Can't use like this `vec(2) = 42`
    // Instead use:
    val updated = vec.updated(2, 42)

    vec(2) shouldEqual 8
    updated(2) shouldEqual 42

    val appended = vec :+ 42
    val prepended = 42 +: vec

    vec shouldEqual Vector(2, 4, 8, 16, 32)
    appended shouldEqual Vector(2, 4, 8, 16, 32, 42)
    prepended shouldEqual Vector(42, 2, 4, 8, 16, 32)
  }


  test("choosing an indexed mutable sequence") {
    // TODO Add test for ArrayBuffer

    pending
  }
}
