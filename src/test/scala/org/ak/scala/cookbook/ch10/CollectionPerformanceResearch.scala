package org.ak.scala.cookbook.ch10

import org.scalameter.Gen.Collections
import org.scalameter.api._

import scala.collection.immutable.{Queue, WrappedString}
import scala.language.reflectiveCalls

/**
  * @author antonk
  * @since 7/21/17 - 7:57 PM
  */
object CollectionPerformanceResearch
  extends Bench.OfflineReport
    with Collections {

  type SizeAware = {def size: Int}

  def queues: Gen[Queue[Int]] = for {
    size <- sizes
  } yield {
    (Queue.newBuilder ++= (0 until size)).result()
  }

  performance of "collections" in {
    performance of "list" in {
      testSizeFor(lists)
    }

    performance of "range" in {
      testSizeFor(ranges)
    }

    performance of "vector" in {
      testSizeFor(vectors)
    }

    performance of "queue" in {
      testSizeFor(queues)
    }

    performance of "string" in {
      testSizeFor(strings)
    }
  }

  override def sizes = Gen.exponential("size")(
    1,
    10000,
    10
  )

  def strings: Gen[WrappedString] = for {
    size <- sizes
  } yield {
    "*" * size
  }


  private def testSizeFor(gen: Gen[_ <: SizeAware]) = {
    using(gen) curve "size" in {
      _.size
    }
  }
}
