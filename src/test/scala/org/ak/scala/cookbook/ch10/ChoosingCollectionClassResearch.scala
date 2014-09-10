package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.{SortedMap, mutable}

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


  test("choosing a linear immutable sequence (List)") {
    val list = List(2, 4, 8, 16, 32)

    // The operation is linear,
    // that is it takes time proportional to the collection size
    list(3) shouldEqual 16

    // The operation takes (fast) constant time
    list.head shouldEqual 2

    // The operation takes (fast) constant time
    list.tail shouldEqual List(4, 8, 16, 32)


    // The operation is linear,
    // that is it takes time proportional to the collection size
    // Can't use like this `list(2) = 42`
    // Instead use:
    val updated = list.updated(2, 42)

    list(2) shouldEqual 8
    updated(2) shouldEqual 42

    // The operation is linear,
    // that is it takes time proportional to the collection size
    val appended = list :+ 42

    // The operation takes (fast) constant time
    val prepended = 42 +: list

    list shouldEqual List(2, 4, 8, 16, 32)
    appended shouldEqual List(2, 4, 8, 16, 32, 42)
    prepended shouldEqual List(42, 2, 4, 8, 16, 32)
  }


  test("choosing a linear mutable sequence (ListBuffer)") {
    val buff = ListBuffer(2, 4, 8, 16, 32)

    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff(3) shouldEqual 16

    // The operation takes (fast) constant time
    buff.head shouldEqual 2

    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff.tail shouldEqual ListBuffer(4, 8, 16, 32)

    // The operation takes (fast) constant time
    buff += 42
    buff shouldEqual ListBuffer(2, 4, 8, 16, 32, 42)

    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff(5) = 37
    buff shouldEqual ListBuffer(2, 4, 8, 16, 32, 37)


    // The operation takes (fast) constant time
    buff.prepend(73)
    buff shouldEqual ListBuffer(73, 2, 4, 8, 16, 32, 37)


    // The operation is linear,
    // that is it takes time proportional to the collection size
    buff.insert(3, 42)
    buff shouldEqual ListBuffer(73, 2, 4, 42, 8, 16, 32, 37)
  }


  // TODO Add tests for Stack, Queue, DoubleLinkedList etc.


  test("choosing a map (HashMap)") {
    // Implements maps using a hashtable
    val map = mutable.HashMap[String, Int]()

    map += ("one" -> 1)
    map += ("two" -> 2)
    map += ("three" -> 3)

    map("two") shouldEqual 2

    map.toList shouldEqual List(("one", 1), ("three", 3), ("two", 2))
  }


  test("choosing a map (LinkedHashMap)") {
    // Implements maps using a hashtable
    val map = mutable.LinkedHashMap[String, Int]()

    map += ("one" -> 1)
    map += ("two" -> 2)
    map += ("three" -> 3)

    map("two") shouldEqual 2

    // Returns elements by the order in which they were inserted.
    map.toList shouldEqual List(("one", 1), ("two", 2), ("three", 3))
  }


  test("choosing a map (ListMap)") {
    // A map implemented using a list data structure
    val map = mutable.ListMap[String, Int]()

    map += ("one" -> 1)
    map += ("two" -> 2)
    map += ("three" -> 3)

    map("two") shouldEqual 2

    // Returns elements in the opposite order by which they
    // were inserted, as though each element is inserted at the
    // head of the map.
    map.toList shouldEqual List(("three", 3), ("two", 2), ("one", 1))
  }



  test("choosing a map (SortedMap)") {
    // A base trait that stores its keys in sorted order.
    // (Creating a variable as a SortedMap
    // currently returns a TreeMap.)
    var map = SortedMap[Int, String]()

    map += (1 -> "one")
    map += (3 -> "three")
    map += (2 -> "two")
    map += (0 -> "zero")

    map(2) shouldEqual "two"

    // Returns elements in the opposite order by which they
    // were inserted, as though each element is inserted at the
    // head of the map.
    map.toList shouldEqual List((0, "zero"), (1, "one"), (2, "two"), (3, "three"))
  }

}
