package org.ak.scala.cookbook.ch10

import org.ak.scala.cookbook.ch10.util.gen.{ImmutableCollections, Indexes, MutableCollections}
import org.ak.scala.cookbook.ch10.util.measure._
import org.scalameter.api._

import scala.collection.{MapLike, SetLike}
import scala.language.reflectiveCalls

/**
  * @author antonk
  * @since 7/21/17 - 7:57 PM
  */
object CollectionPerformanceResearch
  extends Bench.OfflineReport
    with ImmutableCollections
    with MutableCollections
    with Indexes
    with MeasureSize
    with MeasureHeadAndTail
    with MeasureLast
    with MeasureInit
    with MeasureAppend
    with MeasurePrepend
    with MeasureConcat
    with MeasureMin
    with MeasureApply
    with MeasureLookup {

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

        performance of "arraystack" in {
          measureSeqMethodsFor(arraystacks)
        }
      }
    }

    performance of "maps" in {
      performance of "immutable" in {
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


      performance of "mutable" in {
        performance of "hashmaps" in {
          measureMapMethodsFor(mutableHashMaps)
        }

        performance of "weakhashmap" in {
          measureMapMethodsFor(mutableWeakHashMaps)
        }
      }
    }


    performance of "sets" in {
      performance of "immutable" in {
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

      performance of "mutable" in {
        performance of "hashsets" in {
          measureSetMethodsFor(mutableHashSets)
        }

        performance of "treesets" in {
          measureSetMethodsFor(mutableTreeSets)
        }

        performance of "bitset" in {
          measureSetMethodsFor(mutableBitSets)
        }
      }
    }
  }


  // ////////////////////////////////////////////


  override def sizes: Gen[Int] = Gen.exponential("size")(
    2,
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

    measureApplyFor(gen)
  }


  private def measureMapMethodsFor[V](
    gen: Gen[_ <: MapLike[Int, V, _]]
  )(
    implicit valOrdering: Ordering[V]
  ): Unit = {

    measureSizeFor(gen)

    measureMinFor(gen)

    measureLookupFor(gen)
  }


  private def measureSetMethodsFor[T](
    gen: Gen[_ <: SetLike[T, _]]
  )(
    implicit ordering: Ordering[T]
  ): Unit = {

    measureSizeFor(gen)

    measureMinFor(gen)
  }
}
