package org.ak.scala.cookbook.ch10

import org.scalatest.{FunSuite, Matchers}

import scala.collection.immutable.{Queue, Stack}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.{LinearSeq, mutable}
import scala.reflect.internal.util.StringOps

/**
 * @author antonk
 * @since  9/1/14 - 8:17 PM
 */
class CollectionsResearch
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


  test("understanding the collections hierarchy for sequences(Array)") {
    testSeq(
      Array(1, 2, 3),
      array = true,
      range = false,
      vector = false,
      listBuffer = false,
      queue = false,
      stack = false,
      stream = false,
      stringBuilder = false,
      string = false,
      arrayBuffer = false,
      list = false,
      linkedList = false,
      mutableList = false,
      indexedSeq = true,
      buffer = false,
      linearSeq = false,
      seq = true
    )
  }



  private def testSeq[T](
    seqUnderTest: Seq[T],
    array: Boolean,
    range: Boolean,
    vector: Boolean,
    listBuffer: Boolean,
    queue: Boolean,
    stack: Boolean,
    stream: Boolean,
    stringBuilder: Boolean,
    string: Boolean,
    arrayBuffer: Boolean,
    list: Boolean,
    linkedList: Boolean,
    mutableList: Boolean,
    indexedSeq: Boolean,
    buffer: Boolean,
    linearSeq: Boolean,
    seq: Boolean
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

    // Predef.augmentString() implicitly converts String to StringOps
    string shouldEqual seqUnderTest.isInstanceOf[StringOps]
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
