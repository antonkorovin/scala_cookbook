package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer

/**
 * @author antonk
 * @since  9/4/14 - 1:07 PM
 */
class ChoosingCollectionClassResearch
  extends FunSuite
          with Matchers {

  test("choosing an indexed immutable sequence (Vector)") {
    val vec = Vector(2, 4, 8, 16, 32)

    // The operation takes effectively constant time
    vec(3) shouldEqual 16

    // The operation takes effectively constant time
    // Uses `apply(0)`
    vec.head shouldEqual 2

    // The operation takes effectively constant time
    // Uses `drop(1)`
    vec.tail shouldEqual Vector(4, 8, 16, 32)


    // The operation takes effectively constant time
    // Can't use like this `vec(2) = 42`
    // Instead use:
    val updated = vec.updated(2, 42)

    vec(2) shouldEqual 8
    updated(2) shouldEqual 42

    // The operation takes effectively constant time
    val appended = vec :+ 42

    // The operation takes effectively constant time
    val prepended = 42 +: vec

    vec shouldEqual Vector(2, 4, 8, 16, 32)
    appended shouldEqual Vector(2, 4, 8, 16, 32, 42)
    prepended shouldEqual Vector(42, 2, 4, 8, 16, 32)
  }


  test("choosing an indexed mutable sequence (ArrayBuffer)") {
    val buff = ArrayBuffer(2, 4, 8, 16, 32)

    // The operation takes (fast) constant time
    buff(3) shouldEqual 16

    // The operation takes effectively constant time
    buff.head shouldEqual 2

    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff.tail shouldEqual ArrayBuffer(4, 8, 16, 32)

    // The operation takes amortized constant time
    buff += 42
    buff shouldEqual ArrayBuffer(2, 4, 8, 16, 32, 42)

    // The operation takes (fast) constant time
    buff(5) = 37
    buff shouldEqual ArrayBuffer(2, 4, 8, 16, 32, 37)


    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff.prepend(73)
    buff shouldEqual ArrayBuffer(73, 2, 4, 8, 16, 32, 37)


    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff.insert(3, 42)
    buff shouldEqual ArrayBuffer(73, 2, 4, 42, 8, 16, 32, 37)
  }


  test("choosing a linear immutable sequence") {
    // TODO Add test for List

    pending
  }


  test("choosing a linear mutable sequence") {
    // TODO Add test for ListBuffer

    pending
  }
}
