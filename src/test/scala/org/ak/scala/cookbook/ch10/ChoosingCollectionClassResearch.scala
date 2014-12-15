package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.immutable.{TreeSet, ListSet, HashSet}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.{BitSet, SortedMap, mutable}
import org.ak.scala.cookbook.StringUtils._
import scala.language.postfixOps
import scala.language.implicitConversions

/**
 * @author antonk
 * @since  9/4/14 - 1:07 PM
 */
class ChoosingCollectionClassResearch
  extends FunSuite
          with Matchers {

  // <editor-fold desc="Choosing sequence">

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


  // </editor-fold>

  // TODO Add tests for Stack, Queue, DoubleLinkedList etc.


  test("choosing a Queue") {
    // `Queue` objects implement data structures that allow to
    // insert and retrieve elements in a first-in-first-out (FIFO) manner.
    val q = mutable.Queue[Int]()

    q.enqueue(1)
    q.enqueue(2, 3)

    q.toList shouldEqual List(1, 2, 3)

    q.dequeue() shouldEqual 1

    q.toList shouldEqual List(2, 3)


    q.enqueue(4 to 6: _*)
    q.toList shouldEqual (2 to 6).toList


    // Returns the first element in the queue which satisfies the
    // given predicate, and removes this element from the queue.
    q.dequeueFirst(_ > 5) shouldEqual Some(6)
    q.toList shouldEqual (2 to 5).toList

    q.dequeueFirst(_ < 0) shouldEqual None

    // Returns the first element in the queue, or throws an error if there
    // is no element contained in the queue.
    q.front shouldEqual q.head


    // Returns all elements in the queue which satisfy the
    // given predicate, and removes those elements from the queue.
    val evens = q.dequeueAll(_ % 2 == 0)
    evens shouldEqual List(2, 4)
    q.toList shouldEqual List(3, 5)
  }


  test("choosing a Stack") {
    // A stack implements a data structure which allows to store and retrieve
    // objects in a last-in-first-out (LIFO) fashion.
    val stack = mutable.Stack[Int]()

    stack.push(1)
    stack.push(2)
    stack.push(3)

    stack.toList shouldEqual List(3, 2, 1)

    stack.pop() shouldEqual 3
    stack.pop() shouldEqual 2
    stack.pop() shouldEqual 1

    stack should have size 0
  }



  // <editor-fold desc="Choosing map">

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



  test("choosing a map (WeakHash)") {
    case class Key(s: String, i: Int) {
      def canEqual(other: Any): Boolean = other.isInstanceOf[Key]

      override def equals(other: Any): Boolean = other match {
        case that: Key =>
          (that canEqual this) &&
            s == that.s &&
            i == that.i
        case _ => false
      }

      override def hashCode(): Int = {
        val state = Seq(s, i)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
      }
    }

    // A hash map with weak references,
    // it’s a wrapper around java.util.WeakHashMap.
    val map = mutable.WeakHashMap[Key, String]()

    val keyStr = "SomeString"
    val keys = ArrayBuffer(
      Key(keyStr, 1),
      Key(keyStr, 3),
      Key(keyStr, 2),
      Key(keyStr, 0)
    )

    map += (keys(0) -> "one")
    map += (keys(1) -> "three")
    map += (keys(2) -> "two")
    map += (keys(3) -> "zero")

    map(keys(2)) shouldEqual "two"

    map.toList shouldEqual List(
      (Key(keyStr, 0), "zero"),
      (Key(keyStr, 1), "one"),
      (Key(keyStr, 2), "two"),
      (Key(keyStr, 3), "three")
    )


    keys.clear()
    System.gc()

    map should be (empty)
  }

  // </editor-fold>


  // <editor-fold desc="Choosing set">

  test("choosing a set (BitSet)") {
    // A set of “non-negative integers represented as variable-size arrays of bits packed
    // into 64-bit words.” Used to save memory when you have a set of integers.

    val set = BitSet(3, 1, 2, 4)

    set | Set(5, 2, 4, 3) shouldEqual Set(1, 2, 3, 4, 5)
    set & Set(5, 2, 4, 3) shouldEqual Set(2, 3, 4)
    set &~ Set(5, 2, 4, 3) shouldEqual Set(1)
    Set(5, 2, 4, 3) &~ set shouldEqual Set(5)

    set.toBitMask shouldEqual Array("11110".toInt(2))

    set.toList shouldEqual List(1, 2, 3, 4)
  }



  test("choosing a set (HashSet)") {
    // The immutable version “implements sets using a hash trie”;
    // the mutable version “implements sets using a hashtable.”

    val set = HashSet("3", "1", "2", "4")

    set.toList shouldEqual List("4", "1", "2", "3")

    set | Set("5", "2", "4", "3") shouldEqual Set("1", "2", "3", "4", "5")
    set & Set("5", "2", "4", "3") shouldEqual Set("2", "3", "4")
    Set("5", "2", "4", "3") &~ set shouldEqual Set("5")
  }



  test("choosing a set (ListSet)") {
    // A set implemented using a list structure.

    val set = ListSet("3", "1", "2", "4")

    set.toList shouldEqual List("4", "2", "1", "3")

    set | Set("5", "2", "4", "3") shouldEqual Set("1", "2", "3", "4", "5")
    set & Set("5", "2", "4", "3") shouldEqual Set("2", "3", "4")
    Set("5", "2", "4", "3") &~ set shouldEqual Set("5")
  }




  test("choosing a set (TreeSet/SortedSet)") {
    // The immutable version “implements immutable sets using a tree.”
    // The mutable version is a mutable SortedSet with
    // “an immutable AVL Tree as underlying data structure.”

    val set = TreeSet("3", "1", "2", "4")

    set.toList shouldEqual List("1", "2", "3", "4")

    set | Set("5", "2", "4", "3") shouldEqual Set("1", "2", "3", "4", "5")
    set & Set("5", "2", "4", "3") shouldEqual Set("2", "3", "4")
    Set("5", "2", "4", "3") &~ set shouldEqual Set("5")
  }

  // </editor-fold>


  // <editor-fold desc="Other collections classes (and types that act like collections)">

  test("choosing an Enumeration") {
    //  A finite collection of constant values (i.e., the days in a week or months in a year).

    // //////////////////////

    object WeekDay extends Enumeration {
      type WeekDay = Value
      val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
    }

    import WeekDay._

    def isWorkingDay(d: WeekDay) = ! (d == Sat || d == Sun)


    // //////////////////////


    (WeekDay.values filter isWorkingDay toList) shouldEqual List(Mon, Tue, Wed, Thu, Fri)

    WeekDay.maxId shouldEqual WeekDay.values.size

    WeekDay(3) shouldEqual Thu

    WeekDay.withName("Mon") shouldEqual Mon

    intercept[NoSuchElementException] {
      WeekDay.withName("NonExistent") // throws an exception caused by `None.get`
    }
  }


  test("choosing an Iterator") {
    // An iterator isn’t a collection; instead, it gives you a way to access the elements in a collection.
    // It does, however, define many of the methods you’ll see in a normal collection class,
    // including foreach, map, flatMap, etc. You can also convert an iterator to a collection when needed.

    // //////////////////////

    Iterator(1, 2, 3).contains(2) shouldEqual true
    Iterator(1, 2, 3).contains(4) shouldEqual false
    Iterator(1, 2, 3).isTraversableAgain shouldEqual false

    Iterator.empty.hasNext shouldEqual false


    intercept[NoSuchElementException] {
      Iterator.empty.next()
    }

    Iterator.empty.hasDefiniteSize shouldEqual true
    Iterator(1, 2, 3).hasDefiniteSize shouldEqual false

    Iterator.fill(5)(2).size shouldEqual 5
    Iterator.fill(5)(2).sum shouldEqual 10

    // An infinite-length iterator returning the results of evaluating an expression.
    Iterator.continually(42).next() shouldEqual 42

    Iterator.single(42).size shouldEqual 1

    // Creates an iterator producing the values of a given function
    // over a range of integer values starting from 0.
    Iterator.tabulate(5)(_ * 2).toList shouldEqual List(0, 2, 4, 6, 8)

    Iterator.range(1, 4).toList shouldEqual List(1, 2, 3)
    Iterator.range(1, 5, 2).toList shouldEqual List(1, 3)

    val plus3Iterator = Iterator.iterate(1)(_ + 3)
    plus3Iterator.next() shouldEqual 1
    plus3Iterator.next() shouldEqual 4
    plus3Iterator.next() shouldEqual 7
    plus3Iterator.next() shouldEqual 10


    val incrementIterator = Iterator.from(5)
    incrementIterator.next() shouldEqual 5
    incrementIterator.next() shouldEqual 6
    incrementIterator.next() shouldEqual 7

    val incrementIteratorWithStep = Iterator.from(5, 2)
    incrementIteratorWithStep.next() shouldEqual 5
    incrementIteratorWithStep.next() shouldEqual 7
    incrementIteratorWithStep.next() shouldEqual 9


    (Iterator(1, 2, 3) ++ Iterator(4, 5, 6)).toList shouldEqual (1 to 6).toList

    val filteredIterator = Iterator(1, 2, 3, 4).filter(_ % 2 ==0)
    filteredIterator.next() shouldEqual 2
    filteredIterator.next() shouldEqual 4
    filteredIterator.hasNext shouldEqual false
  }


  test("choosing an Option") {
    // Acts as a collection that contains zero or one elements.
    // The Some class and None object extend Option.
    // Some is a container for one element, and None holds zero elements.


    Some(1).size shouldEqual 1
    None.size shouldEqual 0
    Some(1).isEmpty shouldEqual false
    Some(1).isDefined shouldEqual true

    None.isEmpty shouldEqual true
    None.isDefined shouldEqual false


    (for (v <- Some(1)) yield v).toList should have size 1


    Some(1).get shouldEqual 1

    intercept[NoSuchElementException] {
      None.get
    }
  }


  test("choosing a Tuple") {
    // Supports a heterogeneous collection of elements. There is no one “Tuple” class;
    // tuples are implemented as case classes ranging from Tuple1 to Tuple22,
    // which support 1 to 22 elements.

    val t = (1, "Str")
    t._1 shouldEqual 1
    t._2 shouldEqual "Str"


    t.productIterator.toList shouldEqual List(1, "Str")

    val productIterator = t.productIterator
    productIterator.next shouldEqual 1
    productIterator.next shouldEqual "Str"

    productIterator.hasNext shouldEqual false
  }


    // </editor-fold>
}
