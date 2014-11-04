package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.SeqView
import scala.collection.parallel.mutable.ParArray

/**
 * @author antonk
 * @since  9/23/14 - 10:59 PM
 */
class ChoosingCollectionMethodResearch
  extends FunSuite
          with Matchers {

  // <editor-fold desc="Common collection methods">

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


  test("Traversable.flatten") {
    // Converts a collection of collections (such as a list of lists) to a single collection (single list).

    val c1 = List(List(0, 1), List(0), List(2), List(3))

    c1.flatten shouldEqual List(0, 1, 0, 2, 3)

    // But ...
    val c2 = List(List(0, List(1)), List(0), List(2), List(3))
    c2.flatten shouldEqual List(0, List(1), 0, 2, 3)
  }


  test("Traversable.flatMap") {
    // Returns a new collection by applying a function to all elements of the collection c (like map), and
    // then flattening the elements of the resulting collections.

    val c1 = List(List(0, 1), List(0), List(2, 3))

    c1.flatMap(
      _.reverse
    ) shouldEqual List(1, 0, 0, 3, 2)
  }


  test("Traversable.foldLeft") {
    // Applies the operation to successive elements, going from left to right, starting at element z.

    val c = Traversable(0, 1, 0, 2, 3)

    // (((((10 - 0) - 1) - 0) - 2) - 3)
    c.foldLeft(10)(
      _ - _
    ) shouldEqual 4


    c.foldLeft("")(
      _ + _
    ) shouldEqual "01023"
  }


  test("Traversable.foldRight") {
    // Applies the operation to successive elements, going from right to left, starting at element z.

    val c = Traversable(0, 1, 2, 3)

    // (0 - (1 - (2 - (3 - 20))))
    c.foldRight(20)(
      (nextElement, z) => nextElement - z
    ) shouldEqual 18


    // Same. 'z' is always the second argument
    // (0 - (1 - (2 - (3 - 20))))
    c.foldRight(20)(
      _ - _
    ) shouldEqual 18


    c.foldRight("")(
      _ + _
    ) shouldEqual "0123"
  }


  test("Traversable.forAll") {
    // Returns true if the predicate is true for all elements, false otherwise.

    val c = Traversable(0, 1, 0, 2, 3)

    c.forall(_ > 0) shouldEqual false
    c.forall(_ >= 0) shouldEqual true
  }


  test("Traversable.foreach") {
    // Applies the function f to all elements of the collection.


    val c = Traversable(0, 1, 0, 2, 3)

    val sb = StringBuilder.newBuilder
    c.foreach {
      sb.append
    }

    sb.toString shouldEqual "01023"
  }


  test("Traversable.groupBy") {
    // Partitions the collection into a Map of collections according to the function.

    val c = Traversable(0, 1, 0, 2, 3)

    c.groupBy(_ > 0) shouldEqual Map(
      false -> Traversable(0, 0),
      true -> Traversable(1, 2, 3)
    )

    c.groupBy(_.toString) shouldEqual Map(
      "0" -> Traversable(0, 0),
      "1" -> Traversable(1),
      "2" -> Traversable(2),
      "3" -> Traversable(3)
    )
  }


  test("Traversable.hasDefinitiveSize") {

    // Tests whether the collection has a finite size. (Returns false for a Stream or Iterator, for
    // example.)


    Traversable(0, 1, 0, 2, 3).hasDefiniteSize shouldEqual true

    Stream(1, 2, 3).hasDefiniteSize shouldEqual false
  }

  test("Traversable.head") {

    // Returns the first element of the collection. Throws a NoSuchElementException if the
    // collection is empty.

    Traversable(0, 1, 0, 2, 3).head shouldEqual 0


    intercept[NoSuchElementException] {
      Traversable.empty.head
    }
  }


  test("Traversable.headOption") {
    // Returns the first element of the collection as Some[A] if the element exists, or None if the
    // collection is empty.

    Traversable(0, 1, 0, 2, 3).headOption shouldEqual Some(0)

    Traversable.empty[AnyRef].headOption shouldEqual None
  }


  test("Traversable.init") {
    // Selects all elements from the collection except the last one. Throws an
    // UnsupportedOperationException if the collection is empty.

    Traversable(0, 1, 0, 2, 3).init shouldEqual Traversable(0, 1, 0, 2)

    intercept[UnsupportedOperationException] {
      Traversable.empty[AnyRef].init
    }
  }


  test("Seq.intersect") {
    // On collections that support it, it returns the intersection of the two collections (the elements
    // common to both collections).


    val c1 = Seq(0, 1, 0, 2, 3)
    val c2 = Seq(7, 1, 5, 2, 4)

    c1.intersect(c2) shouldEqual Seq(1, 2)
  }


  test("Traversable.isEmpty") {
    // Returns true if the collection is empty, false otherwise.

    Traversable(0, 1, 0, 2, 3).isEmpty shouldEqual false
    Traversable.empty.isEmpty shouldEqual true
    Traversable().isEmpty shouldEqual true
  }


  test("Traversable.last") {

    // Returns the last element from the collection. Throws a NoSuchElementException if the
    // collection is empty.

    Traversable(0, 1, 0, 2, 3).last shouldEqual 3


    intercept[NoSuchElementException] {
      Traversable.empty.last
    }
  }


  test("Traversable.lastOption") {
    // Returns the last element of the collection as Some[A] if the element exists, or None if the
    // collection is empty.

    Traversable(0, 1, 0, 2, 3).lastOption shouldEqual Some(3)

    Traversable.empty[AnyRef].lastOption shouldEqual None
  }


  test("Traversable.map") {
    // Creates a new collection by applying the function to all the elements of the collection.

    Traversable(0, 1, 0, 2, 3).map(_ * 2) shouldEqual Traversable(0, 2, 0, 4, 6)
    Traversable.empty[Int].map(_ * 2) shouldEqual Traversable.empty[Int]
  }


  test("Traversable.max") {
    // Returns the largest element from the collection.
    Traversable(0, 1, 0, 2, 3).max shouldEqual 3

    intercept[UnsupportedOperationException] {
      Traversable.empty[Int].max
    }
  }

  test("Traversable.min") {
    //  Returns the smallest element from the collection.
    Traversable(0, 1, 0, 2, 3).min shouldEqual 0

    intercept[UnsupportedOperationException] {
      Traversable.empty[Int].min
    }
  }


  test("Traversable.nonEmpty") {
    // Returns true if the collection is not empty, false otherwise.

    Traversable(0, 1, 0, 2, 3).nonEmpty shouldEqual true
    Traversable.empty.nonEmpty shouldEqual false
    Traversable().nonEmpty shouldEqual false
  }


  test("Traversable.par") {
    // Returns a parallel implementation of the collection, e.g., Array returns ParArray.

    Array(0, 1, 0, 2, 3).par shouldEqual ParArray(0, 1, 0, 2, 3)
  }



  test("Traversable.partition") {
    // Returns two collections according to the predicate algorithm.

    val (left, right) = Traversable(0, 1, 0, 2, 3).partition(_ < 2)

    left shouldEqual Traversable(0, 1, 0)
    right shouldEqual Traversable(2, 3)
  }


  test("Traversable.reduceLeft") {
    // The same as foldLeft, but begins at the first element of the collection.

    val c = Traversable(0, 1, 0, 2, 3)

    // ((((0 - 1) - 0) - 2) - 3)
    c.reduceLeft(
      _ - _
    ) shouldEqual -6


    c.reduceLeft(
      _ + _
    ) shouldEqual 6

  }


  test("Traversable.reduceRight") {
    // The same as foldRight, but begins at the last element of the collection.

    val c = Traversable(0, 1, 2, 3)

    // (0 - (1 - (2 - 3)))
    c.reduceRight(
      (nextElement, z) => nextElement - z
    ) shouldEqual -2


    // Same. 'z' is always the second argument
    // (0 - (1 - (2 - 3)))
  }


  test("Seq.reverse") {
    // Returns a collection with the elements in reverse order. (Not available on Traversable, but
    // common to most collections, from GenSeqLike.)

    Seq(0, 1, 0, 2, 3).reverse shouldEqual Seq(3, 2, 0, 1, 0)
    Seq.empty[AnyRef].reverse shouldEqual Seq.empty[AnyRef]
  }


  test("Traversable.size") {
    // Returns the size of the collection.

    Traversable(0, 1, 0, 2, 3).size shouldEqual 5
    Traversable.empty.size shouldEqual 0
    Traversable().size shouldEqual 0
  }


  test("Traversable.slice") {
    // Returns the interval of elements beginning at element from and ending at element to.

    // inclusive 'from', exclusive 'to'
    Traversable(0, 1, 0, 2, 3).slice(1, 3) shouldEqual Traversable(1, 0)
    Traversable(0, 1, 0, 2, 3).slice(2, 6) shouldEqual Traversable(0, 2, 3)
    Traversable(0, 1, 0, 2, 3).slice(5, 6) shouldEqual Traversable.empty
  }


  test("Seq.sortWith") {
    // Returns a version of the collection sorted by the comparison function f.

    // *  The sort is stable. That is, elements that are equal (as determined by `lt`)
    // *  appear in the same order in the sorted sequence as in the original.
    Seq(0, 1, 0, 2, 3).sortWith(_ > _) shouldEqual Seq(3, 2, 1, 0, 0)
    Seq(0, 1, 0, 2, 3).sortWith(_ < _) shouldEqual Seq(0, 0, 1, 2, 3)
  }


  test("Traversable.span") {
    // Returns a collection of two collections; the first created by c.takeWhile(p), and the second
    // created by c.dropWhile(p).

    Traversable(0, 1, 0, 2, 3).span(_ < 2) shouldEqual(Traversable(0, 1, 0), Traversable(2, 3))
    Traversable.empty[Int].span(_ < 2) shouldEqual(Traversable.empty[Int], Traversable.empty[Int])
  }


  test("Traversable.splitAt") {
    // Returns a collection of two collections by splitting the collection c at element n.

    Traversable(0, 1, 0, 2, 3).splitAt(2) shouldEqual(Traversable(0, 1), Traversable(0, 2, 3))
    Traversable(0, 1, 0, 2, 3).splitAt(0) shouldEqual(Traversable.empty, Traversable(0, 1, 0, 2, 3))
    Traversable(0, 1, 0, 2, 3).splitAt(5) shouldEqual(Traversable(0, 1, 0, 2, 3), Traversable.empty)
  }


  test("Traversable.sum") {
    // Returns the sum of all elements in the collection.

    Traversable(0, 1, 0, 2, 3).sum shouldEqual 6
    Traversable.empty[Int].sum shouldEqual 0
  }


  test("Traversable.tail") {
    // Returns all elements from the collection except the first element.

    Traversable(0, 1, 0, 2, 3).tail shouldEqual Traversable(1, 0, 2, 3)
    Traversable(0).tail shouldEqual Traversable.empty

    intercept[UnsupportedOperationException] {
      Traversable.empty.tail
    }
  }


  test("Traversable.take") {
    // Returns the first n elements of the collection.

    Traversable(0, 1, 0, 2, 3).take(3) shouldEqual Traversable(0, 1, 0)
    Traversable(0, 1, 0, 2, 3).take(10) shouldEqual Traversable(0, 1, 0, 2, 3)
    Traversable(0, 1, 0, 2, 3).take(0) shouldEqual Traversable.empty
    Traversable.empty[AnyRef].take(10) shouldEqual Traversable.empty
  }


  test("Traversable.takeWhile") {
    // Returns elements from the collection while the predicate is true. Stops when the predicate
    // becomes false.

    Traversable(0, 1, 0, 2, 3).takeWhile(_ != 2) shouldEqual Traversable(0, 1, 0)
    Traversable(0, 1, 0, 2, 3).takeWhile(_ >= 0) shouldEqual Traversable(0, 1, 0, 2, 3)
    Traversable(0, 1, 0, 2, 3).takeWhile(_ < 0) shouldEqual Traversable.empty
    Traversable.empty[Int].takeWhile(_ < 0) shouldEqual Traversable.empty
  }


  test("Seq.union") {
    // Returns the union (all elements) of two collections.


    val c1 = Seq(0, 1, 0, 2, 3)
    val c2 = Seq(7, 1, 5, 2, 4)

    c1.union(c2) shouldEqual Seq(0, 1, 0, 2, 3, 7, 1, 5, 2, 4)
    c2.union(c1) shouldEqual Seq(7, 1, 5, 2, 4, 0, 1, 0, 2, 3)
  }

  test("Traversable.unzip") {
    // The opposite of zip, breaks a collection into two collections by dividing each element into two
    // pieces, as in breaking up a collection of Tuple2 elements.

    Traversable(
      (0, 7), (1, 1), (0, 5), (2, 2), (3, 4)
    ).unzip shouldEqual(
      Traversable(0, 1, 0, 2, 3),
      Traversable(7, 1, 5, 2, 4)
      )
  }


  test("Traversable.view") {
    // Returns a nonstrict (lazy) view of the collection.

    Traversable(0, 1, 0, 2, 3).view.isInstanceOf[SeqView[_, _]] shouldEqual true
  }


  test("Seq.zip") {
    // Creates a collection of pairs by matching the element 0 of c1 with element 0 of c2, element 1
    // of c1 with element 1 of c2, etc.

    val c1 = Seq(0, 1, 0, 2, 3)
    val c2 = Seq(7, 1, 5, 2, 4)

    c1.zip(c2) shouldEqual Seq((0, 7), (1, 1), (0, 5), (2, 2), (3, 4))


    val c3 = Seq(0, 1, 0, 2, 3)
    val c4 = Seq(7, 1, 5)

    c3.zip(c4) shouldEqual Seq((0, 7), (1, 1), (0, 5))
  }


  test("Seq.zipWithIndex") {
    // Zips the collection with its indices.

    val c1 = Seq(0, 1, 0, 2, 3)

    c1.zipWithIndex shouldEqual Seq((0, 0), (1, 1), (0, 2), (2, 3), (3, 4))
  }

  // </editor-fold>


  // <editor-fold desc="Mutable collection methods">


  // TODO Immutable collection operators

  // </editor-fold>


  // TODO Maps
}
