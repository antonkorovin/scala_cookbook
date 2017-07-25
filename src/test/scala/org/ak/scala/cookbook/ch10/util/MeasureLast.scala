package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen


/**
  * @author antonk
  * @since 7/24/17 - 5:21 PM
  */
trait MeasureLast {

  def measureLastFor(gen: Gen[_ <: Traversable[_]]) = {
    using(gen) curve "last" in {
      _.last
    }
  }


}
