package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.immutable.{Queue, Stack, WrappedString}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.{LinearSeq, mutable}

/**
 * @author antonk
 * @since  9/1/14 - 8:17 PM
 */
class CollectionsHierarchyResearch
  extends FunSuite
          with Matchers {

  test("understanding the collections hierarchy") {
    //                Traversable
    //                     |
    //                  Iterable
    //                     |
    //              ---------------
    //              |      |      |
    //             Seq    Set    Map
    //              |
    //       ----------------
    //       |              |
    //    IndexedSeq      LinearSeq

    val indexedSeq = IndexedSeq(1, 2, 3)

    true shouldEqual indexedSeq.isInstanceOf[Seq[_]]
    true shouldEqual indexedSeq.isInstanceOf[Iterable[_]]
    true shouldEqual indexedSeq.isInstanceOf[Traversable[_]]

    false shouldEqual indexedSeq.isInstanceOf[Set[_]]
    false shouldEqual indexedSeq.isInstanceOf[Map[_, _]]



    val linearSeq = LinearSeq(1, 2, 3)

    true shouldEqual linearSeq.isInstanceOf[Seq[_]]
    true shouldEqual linearSeq.isInstanceOf[Iterable[_]]
    true shouldEqual linearSeq.isInstanceOf[Traversable[_]]

    false shouldEqual linearSeq.isInstanceOf[Set[_]]
    false shouldEqual linearSeq.isInstanceOf[Map[_, _]]



    val set = Set(1, 2, 3)

    false shouldEqual set.isInstanceOf[Seq[_]]
    true shouldEqual set.isInstanceOf[Iterable[_]]
    true shouldEqual set.isInstanceOf[Traversable[_]]

    true shouldEqual set.isInstanceOf[Set[_]]
    false shouldEqual set.isInstanceOf[Map[_, _]]



    val map = Map(1 -> 2)

    false shouldEqual map.isInstanceOf[Seq[_]]
    true shouldEqual map.isInstanceOf[Iterable[_]]
    true shouldEqual map.isInstanceOf[Traversable[_]]

    false shouldEqual map.isInstanceOf[Set[_]]
    true shouldEqual map.isInstanceOf[Map[_, _]]



    val iterable = Iterable(1, 2, 3)

    // Iterable.apply returns List
    true shouldEqual iterable.isInstanceOf[Seq[_]]
    true shouldEqual iterable.isInstanceOf[Iterable[_]]
    true shouldEqual iterable.isInstanceOf[Traversable[_]]

    false shouldEqual iterable.isInstanceOf[Set[_]]
    false shouldEqual iterable.isInstanceOf[Map[_, _]]
  }

  //                                 Sequences hierarchy
  //
  //                                         Seq
  //                                          |
  //                   ------------------------------------------------
  //                   |                      |                       |
  //               IndexedSeq              Buffer                 LinearSeq
  //                   |                      |                       |
  //  --------------------------------   -----|      --------------------------------
  //  |       |        |    |   |    |   |    |      |   |     |      |     |       |
  //  |  StringBuilder | String | ArrayBuffer |    List  | LinkedList | MutableList |
  //  |                |        |             |          |            |             |
  // Array           Range    Vector      ListBuffer   Queue        Stack         Stream

  test("understanding the collections hierarchy for sequences(Array)") {
    testSeq(
      Array(1, 2, 3),
      array = true,
      indexedSeq = true,
      buffer = false,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(Range)") {
    testSeq(
      Range(1, 3),
      range = true,
      indexedSeq = true,
      buffer = false,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(Vector)") {
    testSeq(
      Vector(1, 2, 3),
      vector = true,
      indexedSeq = true,
      buffer = false,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(ListBuffer)") {
    testSeq(
      ListBuffer(1, 2, 3),
      listBuffer = true,
      indexedSeq = false,
      buffer = true,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(Queue)") {
    testSeq(
      Queue(1, 2, 3),
      queue = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(Stack)") {
    testSeq(
      Stack(1, 2, 3),
      stack = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(Stream)") {
    testSeq(
      Stream(1, 2, 3),
      stream = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(StringBuilder)") {
    testSeq(
      new mutable.StringBuilder,
      stringBuilder = true,
      indexedSeq = true,
      buffer = false,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(String)") {
    testSeq(
      new String(),
      string = true,
      indexedSeq = true,
      buffer = false,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(ArrayBuffer)") {
    testSeq(
      ArrayBuffer(1, 2, 3),
      arrayBuffer = true,
      indexedSeq = true,
      buffer = true,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(List)") {
    testSeq(
      List(1, 2, 3),
      list = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(LinkedList)") {
    testSeq(
      mutable.LinkedList(1, 2, 3),
      linkedList = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(MutableList)") {
    testSeq(
      mutable.MutableList(1, 2, 3),
      mutableList = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(IndexedSeq)") {
    testSeq(
      IndexedSeq(1, 2, 3),
      vector = true,
      indexedSeq = true,
      buffer = false,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(Buffer)") {
    testSeq(
      mutable.Buffer(1, 2, 3),
      arrayBuffer = true,
      indexedSeq = true,
      buffer = true,
      linearSeq = false
    )
  }


  test("understanding the collections hierarchy for sequences(LinearSeq)") {
    testSeq(
      LinearSeq(1, 2, 3),
      list = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }


  test("understanding the collections hierarchy for sequences(Seq)") {
    testSeq(
      Seq(1, 2, 3),
      list = true,
      indexedSeq = false,
      buffer = false,
      linearSeq = true
    )
  }



  test("understanding the collections hierarchy for sequences(Map)") {
    import collection.SortedMap
    import collection.immutable.TreeMap

    val hashMap = mutable.HashMap(1 -> 1)
    val weakHashMap = mutable.WeakHashMap(1 -> 1)
    val sortedMap = SortedMap(1 -> 1)
    val treeMap = TreeMap(1 -> 1)
    val linkedHashMap = mutable.LinkedHashMap(1 -> 1)
    val listMap = mutable.ListMap(1 -> 1)

    hashMap.isInstanceOf[mutable.Map[_, _]] shouldEqual true
    weakHashMap.isInstanceOf[mutable.Map[_, _]] shouldEqual true
    sortedMap.isInstanceOf[Map[_, _]] shouldEqual true
    treeMap.isInstanceOf[Map[_, _]] shouldEqual true
    linkedHashMap.isInstanceOf[mutable.Map[_, _]] shouldEqual true
    listMap.isInstanceOf[mutable.Map[_, _]] shouldEqual true
  }


  test("understanding the collections hierarchy for sequences(Set)") {
    import collection.immutable.ListSet
    import collection.immutable.SortedSet
    import collection.immutable.TreeSet


    val bitSet = mutable.BitSet(1)
    val hashSet = mutable.HashSet(1)
    val listSet = ListSet(1)
    val sortedSet = SortedSet(1)
    val treeSet = TreeSet(1)

    bitSet.isInstanceOf[mutable.BitSet] shouldEqual true
    hashSet.isInstanceOf[mutable.HashSet[_]] shouldEqual true
    listSet.isInstanceOf[ListSet[_]] shouldEqual true
    sortedSet.isInstanceOf[SortedSet[_]] shouldEqual true
    treeSet.isInstanceOf[TreeSet[_]] shouldEqual true
    treeSet.isInstanceOf[SortedSet[_]] shouldEqual true
  }


  // ////////////////////////////////////////////


  private def testSeq[T](
    seqUnderTest: Seq[T],
    array: Boolean = false,
    range: Boolean = false,
    vector: Boolean = false,
    listBuffer: Boolean = false,
    queue: Boolean = false,
    stack: Boolean = false,
    stream: Boolean = false,
    stringBuilder: Boolean = false,
    string: Boolean = false,
    arrayBuffer: Boolean = false,
    list: Boolean = false,
    linkedList: Boolean = false,
    mutableList: Boolean = false,
    indexedSeq: Boolean = true,
    buffer: Boolean = true,
    linearSeq: Boolean = true,
    seq: Boolean = true
  ) {
    // Predef.refArrayOps implicitly converts Array to WrappedArray
    array shouldEqual seqUnderTest.isInstanceOf[mutable.ArrayLike[_, _]]
    range shouldEqual seqUnderTest.isInstanceOf[Range]
    vector shouldEqual seqUnderTest.isInstanceOf[Vector[_]]
    listBuffer shouldEqual seqUnderTest.isInstanceOf[ListBuffer[_]]
    queue shouldEqual seqUnderTest.isInstanceOf[Queue[_]]
    stack shouldEqual seqUnderTest.isInstanceOf[Stack[_]]
    stream shouldEqual seqUnderTest.isInstanceOf[Stream[_]]
    stringBuilder shouldEqual seqUnderTest.isInstanceOf[StringBuilder]

    // String implicitly converts to WrappedString or StringOps
    string shouldEqual seqUnderTest.isInstanceOf[WrappedString]
    arrayBuffer shouldEqual seqUnderTest.isInstanceOf[ArrayBuffer[_]]
    list shouldEqual seqUnderTest.isInstanceOf[List[_]]
    linkedList shouldEqual seqUnderTest.isInstanceOf[mutable.LinkedList[_]]
    mutableList shouldEqual seqUnderTest.isInstanceOf[mutable.MutableList[_]]
    indexedSeq shouldEqual seqUnderTest.isInstanceOf[IndexedSeq[_]]
    buffer shouldEqual seqUnderTest.isInstanceOf[mutable.Buffer[_]]
    linearSeq shouldEqual seqUnderTest.isInstanceOf[LinearSeq[_]]
    seq shouldEqual seqUnderTest.isInstanceOf[Seq[_]]
  }
}
