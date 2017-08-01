package org.ak.scala.cookbook.ch10

import org.ak.scala.cookbook.ch10.util._
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
    with MeasureHeadAndTail
    with MeasureLast
    with MeasureInit
    with MeasureAppend
    with MeasurePrepend
    with MeasureConcat {

  performance of "collections" config(
    exec.minWarmupRuns -> 1,
    exec.maxWarmupRuns -> 1,
    exec.benchRuns -> 3
  ) in {
    performance of "list" in {
      measureSeqMethodsFor(lists)
    }

    performance of "range" in {
      measureSeqMethodsFor(ranges)
    }

    performance of "vector" in {
      measureSeqMethodsFor(vectors)
    }

    performance of "queue" in {
      measureSeqMethodsFor(queues)
    }

    performance of "string" in {
      measureSeqMethodsFor(strings)
    }

    performance of "arraybuffer" in {
      measureSeqMethodsFor(arraybuffers)
    }
  }


  // ////////////////////////////////////////////


  override def sizes = Gen.exponential("size")(
    1,
    0x0FFFFF + 1,
    2
  )

  private def measureSeqMethodsFor[T](gen: Gen[_ <: Seq[T]]): Unit = {
    measureSizeFor(gen)

    measureHeadAndTailFor(gen)

    measureLastFor(gen)

    measureInitFor(gen)

    measureAppendFor(gen)

    measurePrependFor(gen)

    measureConcatFor(gen)
  }

}
