package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen

import scala.collection.immutable.{BitSet, HashSet, TreeSet}

/**
  * @author antonk
  * @since 8/9/17 - 6:11 PM
  */
trait ImmutableSets
  extends Sizes
    with ArrayEntries {

  def hashSets: Gen[HashSet[Int]] = {
    for {
      size <- sizes
    } yield {
      HashSet(
        entries(size): _*
      )
    }
  }.cached


  def treeSets: Gen[TreeSet[Int]] = {
    for {
      size <- sizes
    } yield {
      TreeSet(
        entries(size): _*
      )
    }
  }.cached

  def bitSets: Gen[BitSet] = {
    for {
      size <- sizes
    } yield {
      BitSet(
        entries(size): _*
      )
    }
  }.cached
}
