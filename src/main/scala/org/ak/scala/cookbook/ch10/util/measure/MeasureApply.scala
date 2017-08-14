package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.Indexes
import org.scalameter.api.Gen

import scala.collection.SeqLike

/**
  * @author antonk
  * @since 8/11/17 - 07:59 AM
  */
trait MeasureApply {

  self: Indexes =>

  def measureApplyFor(gen: Gen[_ <: SeqLike[_, _]]) = {
    using(Gen.crossProduct(gen, indexes)) curve "apply" in {
      case (collection, index) =>
        collection(index)
    }
  }

}
