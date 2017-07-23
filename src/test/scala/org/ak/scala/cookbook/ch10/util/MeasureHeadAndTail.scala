package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 7/25/17 - 1:55 PM
  */
trait MeasureHeadAndTail {

  def measureHeadAndTailFor(gen: Gen[_ <: Traversable[_]]) = {
    using(gen) curve "head" in {
      _.head
    }


    using(gen) curve "tail" in {
      _.tail
    }

  }

}
