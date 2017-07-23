package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.scalameter.api.Gen

import scala.language.reflectiveCalls

/**
  * @author antonk
  * @since 7/25/17 - 12:29 PM
  */
trait MeasureSize extends MethodsUnderTestAware {

  def measureSizeFor(gen: Gen[_ <: SizeAware]) = {
    using(gen) curve "size" in {
      _.size
    }
  }

}
