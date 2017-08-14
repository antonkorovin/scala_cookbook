package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.api.Gen

import scala.collection.immutable.{Queue, WrappedString}

/**
  * @author antonk
  * @since 7/25/17 - 12:26 PM
  */
trait ImmutableCollections
  extends Sizes
    with ImmutableMaps
    with ImmutableSets {


  val lists: Gen[List[Int]] = {
    sizes.map(
      size =>
        List(
          entries(size): _*
        )
    )
  }.cached


  val ranges: Gen[Range] = {
    sizes.map(
      size =>
        0 until size
    )
  }.cached

  val vectors: Gen[Vector[Int]] = {
    sizes.map(
      size =>
        Vector(
          entries(size): _*
        )
    )
  }.cached


  val queues: Gen[Queue[Int]] = {
    for {
      size <- sizes
    } yield {
      (Queue.newBuilder ++= (0 until size)).result()
    }
  }.cached


  val strings: Gen[WrappedString] = {
    val gen: Gen[WrappedString] = for {
      size <- sizes
    } yield {
      "*" * size
    }

    gen.cached
  }

}
