package org.ak.scala.cookbook.ch10

import org.ak.scala.cookbook.ch10.util.{MeasureHeadAndTail, MeasureSize}
import org.ak.scala.cookbook.ch10.util.gen.ImmutableCollections
import org.scalameter.api._

import scala.language.reflectiveCalls

/**
  * @author antonk
  * @since 7/21/17 - 7:57 PM
  */
object CollectionPerformanceResearch
  extends Bench.OfflineReport
    with ImmutableCollections
    with MeasureSize
    with MeasureHeadAndTail {

  performance of "collections" config (
    exec.minWarmupRuns -> 1,
    exec.maxWarmupRuns -> 1,
    exec.benchRuns-> 3
  ) in {
    performance of "list" in {
      measureSizeFor(lists)

      measureHeadAndTailFor(lists)
    }

    performance of "range" in {
      measureSizeFor(ranges)

      measureHeadAndTailFor(ranges)
    }

    performance of "vector" in {
      measureSizeFor(vectors)

      measureHeadAndTailFor(vectors)
    }

    performance of "queue" in {
      measureSizeFor(queues)

      measureHeadAndTailFor(queues)
    }

    performance of "string" in {
      measureSizeFor(strings)

      measureHeadAndTailFor(strings)
    }
  }

  override def sizes = Gen.exponential("size")(
    1,
    10000,
    10
  )
}
