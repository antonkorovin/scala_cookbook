package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen.Collections
import org.scalameter.api.Gen

import scala.collection.immutable.{Queue, WrappedString}

/**
  * @author antonk
  * @since 7/25/17 - 12:26 PM
  */
trait ImmutableCollections extends Collections with ImmutableMaps {


  override val lists = super.lists.cached

  override val ranges = super.ranges.cached

  override val vectors = super.vectors.cached

  def queues: Gen[Queue[Int]] = {
    for {
      size <- sizes
    } yield {
      (Queue.newBuilder ++= (0 until size)).result()
    }
  }.cached


  def strings: Gen[WrappedString] = {
    val gen: Gen[WrappedString] = for {
      size <- sizes
    } yield {
      "*" * size
    }

    gen.cached
  }

}
