package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

import scala.collection.TraversableLike

/**
  * @author antonk
  * @since 8/9/17 - 5:28 PM
  */
trait MeasureMin {
  def measureMinFor[T](gen: Gen[_ <: TraversableLike[T, _]])(implicit ordering: Ordering[T]) = {
    using(gen) curve "min" in {
      _.min
    }
  }

}
