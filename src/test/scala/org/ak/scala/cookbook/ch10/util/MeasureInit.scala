package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 8/2/17 - 6:26 PM
  */
trait MeasureInit {

  def measureInitFor(gen: Gen[_ <: Traversable[_]]) = {
    using(gen) curve "init" in {
      _.init
    }
  }


}
