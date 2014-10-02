package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  9/23/14 - 10:59 PM
 */
class ChoosingCollectionMethodResearch
  extends FunSuite
          with Matchers {

  // TODO Common collection methods
  test("Traversable.collect") {
    // Builds a new collection by applying a partial function to all elements of the collection on which
    // the function is defined.

    val divideAsMatch: PartialFunction[Int, Int] = {
      case d: Int if d != 0 => 42 / d
    }


    val c = Traversable(0, 1, 0, 2, 3)

    c.collect(divideAsMatch) shouldEqual Traversable(42, 21, 14)
  }


  test("Traversable.count") {
    // Counts the number of elements in the collection for which the predicate is satisfied.

    val predicate = (x: Int) => x != 0

    val c = Traversable(0, 1, 0, 2, 3)

    c.count(predicate) shouldEqual 3
  }


  test("Seq.diff") {
    // Returns the difference of the elements in c1 and c2.

    val c1 = Seq(0, 1, 0, 2, 3)
    val c2 = Seq(7, 1, 5, 2, 4)

    c1.diff(c2) shouldEqual Seq(0, 0, 3)
    c2.diff(c1) shouldEqual Seq(7, 5, 4)

    c1.diff(Seq.empty) shouldEqual Seq(0, 1, 0, 2, 3)
    Seq.empty.diff(c1) shouldEqual Seq.empty

    c1.diff(c1) shouldEqual Seq.empty
  }



  test("Traversable.drop") {
    // Returns all elements in the collection except the first n elements.

    val c = Traversable(0, 1, 0, 2, 3)

    c.drop(3) shouldEqual Traversable(2, 3)
    c.drop(c.size * 10) shouldEqual Traversable.empty
  }


  test("Traversable.dropWhile") {
    // Returns a collection that contains the “longest prefix of elements that satisfy the predicate.”

    val c = Traversable(0, 1, 0, 2, 3)

    c.dropWhile(_ != 2) shouldEqual Traversable(2, 3)
    c.dropWhile(_ >= 0) shouldEqual Traversable.empty
  }


  test("Traversable.exists") {
    // Returns true if the predicate is true for any element in the collection.

    val c = Traversable(0, 1, 0, 2, 3)

    c.exists(_ < 0) shouldEqual false
    c.exists(_ == 0) shouldEqual true
    c.exists(_ > 0) shouldEqual true
  }


  test("Traversable.filter") {
    // Returns all elements from the collection for which the predicate is true.

    val c = Traversable(0, 1, 0, 2, 3)

    c.filter(_ != 0) shouldEqual Traversable(1, 2, 3)
  }


  test("Traversable.filterNot") {
    // Returns all elements from the collection for which the predicate is false.

    val c = Traversable(0, 1, 0, 2, 3)

    c.filterNot(_ == 0) shouldEqual Traversable(1, 2, 3)
  }


  test("Traversable.find") {
    // Returns the first element that matches the predicate as Some[A]. Returns None if no match is found.

    val c = Traversable(0, 1, 0, 2, 3)

    c.find(_ == 42) shouldEqual None
    c.find(_ * 21 == 42) shouldEqual Some(2)
  }

  // TODO Mutable collection methods


  // TODO Immutable collection operators


  // TODO Maps
}
