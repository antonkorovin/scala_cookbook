package org.ak.scala.cookbook.ch10

import org.scalatest.{Matchers, FunSuite}

import scala.collection.LinearSeq

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

}
