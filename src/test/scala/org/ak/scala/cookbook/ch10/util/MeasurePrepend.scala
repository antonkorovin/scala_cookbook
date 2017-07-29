package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 8/2/17 - 5:20 PM
  */
trait MeasurePrepend {

  def measurePrependFor[T](gen: Gen[_ <: Seq[T]]) = {
    using(gen) curve "prepend" in {
      _.+:(null)
    }
  }

}
