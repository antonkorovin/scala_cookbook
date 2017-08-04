package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 7/25/17 - 12:29 PM
  */
trait MeasureSize {

  def measureSizeFor(gen: Gen[_ <: Traversable[_]]) = {
    using(gen) curve "size" in {
      _.size
    }
  }

}
