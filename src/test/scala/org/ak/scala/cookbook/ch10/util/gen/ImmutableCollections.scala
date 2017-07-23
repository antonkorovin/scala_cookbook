package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen.Collections
import org.scalameter.api.Gen

import scala.collection.immutable.{Queue, WrappedString}

/**
  * @author antonk
  * @since 7/25/17 - 12:26 PM
  */
trait ImmutableCollections extends Collections {

  def queues: Gen[Queue[Int]] = for {
    size <- sizes
  } yield {
    (Queue.newBuilder ++= (0 until size)).result()
  }

  def strings: Gen[WrappedString] = for {
    size <- sizes
  } yield {
    "*" * size
  }

}
