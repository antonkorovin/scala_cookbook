package org.ak.scala.cookbook.ch10

import org.ak.scala.cookbook.ch10.util._
import org.ak.scala.cookbook.ch10.util.gen.{ImmutableCollections, MutableCollections}
import org.ak.scala.cookbook.ch10.util.measure._
import org.scalameter.api._

import scala.language.reflectiveCalls

/**
  * @author antonk
  * @since 7/21/17 - 7:57 PM
  */
object CollectionPerformanceResearch
  extends Bench.OfflineReport
    with ImmutableCollections
    with MutableCollections
    with MeasureSize
    with MeasureHeadAndTail
    with MeasureLast
    with MeasureInit
    with MeasureAppend
    with MeasurePrepend
    with MeasureConcat
    with MeasureMin {

  performance of "collections" config(
    exec.minWarmupRuns -> 1,
    exec.maxWarmupRuns -> 1,
    exec.benchRuns -> 3
  ) in {

    performance of "sequences" in {
      performance of "immutable" in {
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
      }

      performance of "mutable" in {
        performance of "arraybuffer" in {
          measureSeqMethodsFor(arraybuffers)
        }

        performance of "listbuffer" in {
          measureSeqMethodsFor(listbuffers)
        }


        performance of "stringbuilder" in {
          measureSeqMethodsFor(stringbuilders)
        }

        performance of "mutablelist" in {
          measureSeqMethodsFor(mutablelists)
        }

        performance of "queue" in {
          measureSeqMethodsFor(mutablequeues)
        }

        performance of "arrayseq" in {
          measureSeqMethodsFor(arrayseqs)
        }

        performance of "stack" in {
          measureSeqMethodsFor(mutablestacks)
        }

        performance of "arraystack" in {
          measureSeqMethodsFor(arraystacks)
        }
      }
    }

    performance of "maps" in {
      performance of "hashmaps" in {
        measureMapMethodsFor(hashMaps)
      }

      performance of "treemaps" in {
        measureMapMethodsFor(treeMaps)
      }

      performance of "listmaps" in {
        measureMapMethodsFor(listMaps)
      }
    }


    performance of "sets" in {
      performance of "hashsets" in {
        measureSetMethodsFor(hashSets)
      }

      performance of "treesets" in {
        measureSetMethodsFor(treeSets)
      }

      performance of "bitset" in {
        measureSetMethodsFor(bitSets)
      }
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


  private def measureMapMethodsFor[K, V](
    gen: Gen[_ <: Map[K, V]]
  )(
    implicit keyOrdering: Ordering[K],
    valOrdering: Ordering[V]
  ): Unit = {

    measureSizeFor(gen)

    measureMinFor(gen)
  }


  private def measureSetMethodsFor[T](
    gen: Gen[_ <: Set[T]]
  )(
    implicit ordering: Ordering[T]
  ): Unit = {

    measureSizeFor(gen)

    measureMinFor(gen)
  }
}
