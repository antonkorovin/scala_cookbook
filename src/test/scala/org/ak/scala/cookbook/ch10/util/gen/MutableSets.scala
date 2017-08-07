package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen
import org.scalameter.Gen.Collections

import scala.collection.mutable

/**
  * @author antonk
  * @since 8/8/17 - 00:02 PM
  */
trait MutableSets
  extends Collections
    with ArrayEntries {

  val mutableHashSets: Gen[mutable.HashSet[Int]] = {
    for {
      size <- sizes
    } yield {
      mutable.HashSet(
        entries(size): _*
      )
    }
  }.cached


  val mutableBitSets: Gen[mutable.BitSet] = {
    for {
      size <- sizes
    } yield {
      mutable.BitSet(
        entries(size): _*
      )
    }
  }.cached

  val mutableTreeSets: Gen[mutable.TreeSet[Int]] = {
    for {
      size <- sizes
    } yield {
      mutable.TreeSet(
        entries(size): _*
      )
    }
  }.cached
}
