package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.parallel.mutable.ParArray
import scala.collection.{SeqView, mutable}

/**
 * @author antonk
 * @since  9/23/14 - 10:59 PM
 */
class ChoosingCollectionMethodResearch
  extends FunSuite
          with Matchers {

  // <editor-fold desc="Common collection methods">

  test("Traversable.aggregate") {
    // Builds a new collection by applying a partial function to all elements of the collection on which
    // the function is defined.

    val c = Traversable(0, 1, 0, 2, 3)

    c.aggregate(List[Int]())(
      (res, element) => element * 10 :: res,
      _ ::: _
    ) shouldEqual List(30, 20, 0, 10, 0)
  }


  test("Traversable.collect") {
    // Builds a new collection by applying a partial function to all elements of the collection on which
    // the function is defined.

    val divideAsMatch: PartialFunction[Int, Int] = {
      case d: Int if d != 0 => 42 / d
    }


    val c = Traversable(0, 1, 0, 2, 3)

    c.collect(divideAsMatch) shouldEqual Traversable(42, 21, 14)
  }


  test("Traversable.collectFirst") {
    // Builds a new collection by applying a partial function to all elements of the collection on which
    // the function is defined.

    val divideAsMatch: PartialFunction[Int, Int] = {
      case d: Int if d != 0 => 42 / d
    }

    val absForNegatives: PartialFunction[Int, Int] = {
      case d: Int if d < 0 => Math.abs(d)
    }


    val c = Traversable(0, 1, 0, 2, 3)

    c.collectFirst(divideAsMatch) shouldEqual Some(42)
    c.collectFirst(absForNegatives) shouldEqual None
  }


  test("Traversable.copyToArray") {
    pending
  }


  test("Traversable.count") {
    // Counts the number of elements in the collection for which the predicate is satisfied.

    val predicate = (x: Int) => x != 0

    val c = Traversable(0, 1, 0, 2, 3)

    c.count(predicate) shouldEqual 3
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


  test("Traversable.fold") {
    pending
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


  test("Traversable.isEmpty") {
    // Returns true if the collection is empty, false otherwise.

    Traversable(0, 1, 0, 2, 3).isEmpty shouldEqual false
    Traversable.empty.isEmpty shouldEqual true
    Traversable().isEmpty shouldEqual true
  }


  test("Traversable.isTraversableAgain") {
    pending
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


  test("Traversable.maxBy") {
    pending
  }


  test("Traversable.min") {
    //  Returns the smallest element from the collection.
    Traversable(0, 1, 0, 2, 3).min shouldEqual 0

    intercept[UnsupportedOperationException] {
      Traversable.empty[Int].min
    }
  }


  test("Traversable.minBy") {
    pending
  }


  test("Traversable.mkString") {
    pending
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


  test("Traversable.product") {
    pending
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


    intercept[UnsupportedOperationException] {
      Traversable.empty[Int].reduceLeft(_ - _)
    }
  }


  test("Traversable.reduceLeftOption") {
    pending
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


  test("Traversable.reduceRightOption") {
    pending
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


  test("Traversable.toArray") {
    pending
  }


  test("Traversable.toBuffer") {
    pending
  }


  test("Traversable.toIndexedSeq") {
    pending
  }


  test("Traversable.toIterable") {
    pending
  }


  test("Traversable.toIterator") {
    pending
  }


  test("Traversable.toList") {
    pending
  }


  test("Traversable.toMap") {
    pending
  }


  test("Traversable.toSeq") {
    pending
  }


  test("Traversable.toSet") {
    pending
  }


  test("Traversable.toStream") {
    pending
  }


  test("Traversable.toTraversable") {
    pending
  }


  test("Traversable.toVector") {
    pending
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



  // </editor-fold>


  // <editor-fold desc="Seq specific methods">


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




  test("Seq.indexWhere") {
    // Finds index of the first element satisfying some predicate after or at some start index.

    pending
  }


  test("Seq.indexWhere") {
    pending
  }


  test("Seq.intersect") {
    // On collections that support it, it returns the intersection of the two collections (the elements
    // common to both collections).


    val c1 = Seq(0, 1, 0, 2, 3)
    val c2 = Seq(7, 1, 5, 2, 4)

    c1.intersect(c2) shouldEqual Seq(1, 2)
  }


  test("Seq.reverse") {
    // Returns a collection with the elements in reverse order. (Not available on Traversable, but
    // common to most collections, from GenSeqLike.)

    Seq(0, 1, 0, 2, 3).reverse shouldEqual Seq(3, 2, 0, 1, 0)
    Seq.empty[AnyRef].reverse shouldEqual Seq.empty[AnyRef]
  }



  test("Seq.sortWith") {
    // Returns a version of the collection sorted by the comparison function f.

    // *  The sort is stable. That is, elements that are equal (as determined by `lt`)
    // *  appear in the same order in the sorted sequence as in the original.
    Seq(0, 1, 0, 2, 3).sortWith(_ > _) shouldEqual Seq(3, 2, 1, 0, 0)
    Seq(0, 1, 0, 2, 3).sortWith(_ < _) shouldEqual Seq(0, 0, 1, 2, 3)
  }


  test("Seq.union") {
    // Returns the union (all elements) of two collections.


    val c1 = Seq(0, 1, 0, 2, 3)
    val c2 = Seq(7, 1, 5, 2, 4)

    c1.union(c2) shouldEqual Seq(0, 1, 0, 2, 3, 7, 1, 5, 2, 4)
    c2.union(c1) shouldEqual Seq(7, 1, 5, 2, 4, 0, 1, 0, 2, 3)
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

  test("Mutable.+=") {
    val arr = mutable.ArrayBuffer[Int]()
    arr should be(empty)

    // Adds the element x to the collection c.
    arr += 1
    arr += 2
    arr += 3

    arr shouldEqual mutable.ArrayBuffer(1, 2, 3)

    // Adds the elements x, y, and z to the collection c.
    arr +=(4, 5, 6)

    arr shouldEqual mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)
  }



  test("Mutable.++=") {
    val arr1 = mutable.ArrayBuffer(1, 2, 3)
    val arr2 = mutable.ArrayBuffer(4, 5, 6)

    // Adds the elements in the collection c2 to the collection c1.
    arr1 ++= arr2

    arr1 shouldEqual mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)
  }


  test("Mutable.-=") {
    val arr = mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)

    // Removes the element x from the collection c.
    arr -= 1
    arr -= 2
    arr -= 3

    arr shouldEqual mutable.ArrayBuffer(4, 5, 6)

    // Removes the elements x , y, and z from the collection c.
    arr -=(4, 5, 6)

    arr should be(empty)
  }


  test("Mutable.--=") {
    val arr1 = mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)
    val arr2 = mutable.ArrayBuffer[Int](4, 5, 6)

    // Removes the elements in the collection c2 from the collection c1.
    arr1 --= arr2

    arr1 shouldEqual mutable.ArrayBuffer(1, 2, 3)
  }


  test("Mutable.(n)=x") {
    val arr = mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)

    // Assigns the value x to the element c(n).
    arr(2) = 42

    arr shouldEqual mutable.ArrayBuffer(1, 2, 42, 4, 5, 6)
  }


  test("Mutable.clear") {
    val arr = mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)

    // Removes all elements from the collection.
    arr.clear()

    arr should be(empty)
  }


  test("Mutable.remove") {
    val arr = mutable.ArrayBuffer(1, 2, 3, 4, 5, 6)

    // Removes the element at position n, or the elements beginning at position n and continuing for length
    arr.remove(2)

    arr shouldEqual mutable.ArrayBuffer(1, 2, 4, 5, 6)

    arr.remove(1, 2)

    arr shouldEqual mutable.ArrayBuffer(1, 5, 6)
  }


  // </editor-fold>


  // <editor-fold desc="Immutable collection operators">

  test("Immutable.++") {
    // Creates a new collection by appending the elements in the collection c2 to the collection c1.

    val seq1 = Seq(1, 2, 3)
    val seq2 = Seq(4, 5, 6)

    seq1 ++ seq2 shouldEqual Seq(1, 2, 3, 4, 5, 6)
  }


  test("Immutable.:+") {
    // Returns a new collection with the element e appended to the collection c.

    val seq1 = Seq(1, 2, 3)
    val seq2 = seq1 :+ 4
    val seq3 = seq2 :+ 5 :+ 6

    seq3 shouldEqual Seq(1, 2, 3, 4, 5, 6)
  }


  test("Immutable.+:") {
    // Returns a new collection with the element e prepended to the collection c.

    val seq1 = Seq(1, 2, 3)
    val seq2 = 4 +: seq1
    val seq3 = 6 +: 5 +: seq2

    seq3 shouldEqual Seq(6, 5, 4, 1, 2, 3)
  }


  test("Immutable.::") {
    // Returns a List with the element e prepended to the List named list. (:: works only on List.)

    val list1 = List(1, 2, 3)
    val list2 = 4 :: list1
    val list3 = 6 :: 5 :: list2

    list3 shouldEqual List(6, 5, 4, 1, 2, 3)

    1 :: 2 :: 3 :: Nil shouldEqual List(1, 2, 3)
  }

  // </editor-fold>


  // <editor-fold desc="Maps">

  test("ImmutableMap.-") {
    // Returns a map with the key k (and its corresponding value) removed.
    // Returns a map with the keys k1, k2, and k3 removed.

    val m = Map(1 -> "one", 2 -> "two", 3 -> "three")

    val m2 = m - 2
    val m3 = m -(2, 3)

    m2 shouldEqual Map(1 -> "one", 3 -> "three")
    m3 shouldEqual Map(1 -> "one")
  }


  test("ImmutableMap.--") {
    // Returns a map with the keys in the collection removed. (Although List is shown,
    // this can be any sequential collection.)

    val m = Map(1 -> "one", 2 -> "two", 3 -> "three")

    val m2 = m -- (2 :: Nil)
    val m3 = m -- (2 :: 3 :: Nil)

    m2 shouldEqual Map(1 -> "one", 3 -> "three")
    m3 shouldEqual Map(1 -> "one")
  }


  // ////////////////////////////////////////////


  test("MutableMap.+=") {
    // Add the key/value pair(s) to the mutable map mm.

    val m = mutable.Map(1 -> "one")

    m += 2 -> "two"
    m +=(3 -> "three", 4 -> "four")

    m shouldEqual mutable.Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )
  }


  test("MutableMap.++=") {
    // Add the elements in the collection c to the mutable map mm.

    val m = mutable.Map(1 -> "one")

    m ++= List(2 -> "two", 3 -> "three", 4 -> "four")

    m shouldEqual mutable.Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )
  }

  test("MutableMap.-= key(s)") {
    // Remove map entries from the mutable map mm based on the given key(s).

    val m = mutable.Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )


    m -=(2, 4)

    m shouldEqual mutable.Map(
      1 -> "one",
      3 -> "three"
    )


    m -= 1

    m shouldEqual mutable.Map(
      3 -> "three"
    )
  }


  test("MutableMap.-= collection") {
    // Remove the map entries from the mutable map mm based on the keys in the collection c.

    val m = mutable.Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )


    m --= List(1, 2, 4)

    m shouldEqual mutable.Map(
      3 -> "three"
    )
  }


  // ////////////////////////////////////////////


  test("Map.(k)") {
    // Returns the value associated with the key k.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m(1) shouldEqual "one"

    intercept[NoSuchElementException] {
      m(0)
    }
  }


  test("Map.contains") {
    // Returns true if the map m contains the key k.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.contains(1) shouldEqual true
    m.contains(0) shouldEqual false
  }


  test("Map.filter") {
    // Returns a map whose keys and values match the condition of the predicate p.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )


    val filteredByKeys = m.filter(_._1 % 2 == 0)

    filteredByKeys shouldEqual Map(
      2 -> "two",
      4 -> "four"
    )


    val filteredByValues = m.filter(_._2.length > 3)

    filteredByValues shouldEqual Map(
      3 -> "three",
      4 -> "four"
    )
  }


  test("Map.filterKeys") {
    // Returns a map whose keys match the condition of the predicate p.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )


    val filteredByKeys = m.filterKeys(_ % 2 == 0)

    filteredByKeys shouldEqual Map(
      2 -> "two",
      4 -> "four"
    )
  }


  test("Map.get") {
    // Returns the value for the key k as Some[A] if the key is found, None otherwise.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.get(1) shouldEqual Some("one")
    m.get(0) shouldEqual None
  }


  test("Map.getOrElse") {
    // Returns the value for the key k if the key is found, otherwise returns the default value d.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.getOrElse(1, "default") shouldEqual "one"
    m.getOrElse(0, "default") shouldEqual "default"
  }


  test("Map.isDefinedAt") {
    // Returns true if the map contains the key k.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.isDefinedAt(1) shouldEqual true
    m.isDefinedAt(0) shouldEqual false
  }


  test("Map.keys") {
    // Returns the keys from the map as an Iterable.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.keys shouldEqual Set(1, 2, 3, 4).asInstanceOf[Iterable[_]]
  }


  test("Map.keysIterator") {
    // Returns the keys from the map as an Iterator.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    val keysItr = m.keysIterator
    val expItr = Iterator(1, 2, 3, 4)


    while (keysItr.hasNext || expItr.hasNext) {
      keysItr.next shouldEqual expItr.next
    }
  }


  test("Map.keySet") {
    // Returns the keys from the map as a Set.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.keySet shouldEqual Set(1, 2, 3, 4)
  }


  test("Map.mapValues") {
    // Returns a new map by applying the function f to every value in the initial map.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.mapValues(_.toUpperCase) shouldEqual Map(
      1 -> "ONE",
      2 -> "TWO",
      3 -> "THREE",
      4 -> "FOUR"
    )
  }


  test("Map.values") {
    // Returns the values from the map as an Iterable.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    m.values should contain only("one", "two", "three", "four")
  }


  test("Map.valuesIterator") {
    // Returns the values from the map as an Iterator.

    val m = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four"
    )

    val valuesItr = m.valuesIterator
    val expItr = Iterator("one", "two", "three", "four")


    while (valuesItr.hasNext || expItr.hasNext) {
      valuesItr.next shouldEqual expItr.next
    }
  }


  // </editor-fold>

}
