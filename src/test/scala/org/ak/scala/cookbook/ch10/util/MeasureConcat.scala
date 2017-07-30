package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 7/31/17 - 11:23 AM
  */
trait MeasureConcat {
  def measureConcatFor[T](gen: Gen[_ <: Seq[T]]) = {
    using(gen) curve "concat(self)" in {
      x =>
        x ++ x
    }
  }

}
