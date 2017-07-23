package org.ak.scala.cookbook.ch10.util

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.{lists, using}
import org.scalameter.api.Gen

import scala.language.reflectiveCalls

/**
  * @author antonk
  * @since 7/25/17 - 1:55 PM
  */
trait MeasureHeadAndTail extends MethodsUnderTestAware {
  def measureHeadAndTailFor(gen: Gen[_ <: HeadAware[_] with TailAware[_]]) = {
    using(gen) curve "head" in {
      _.head
    }


    using(gen) curve "tail" in {
      _.tail
    }

  }

}
