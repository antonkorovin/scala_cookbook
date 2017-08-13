package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.Indexes
import org.scalameter.api.Gen

import scala.collection.SetLike

/**
  * @author antonk
  * @since 8/13/17 - 10:29 PM
  */
trait MeasureContains {
  self: Indexes =>

  def measureContainsFor(gen: Gen[_ <: SetLike[Int, _]]): Unit = {
    using(Gen.crossProduct(gen, indexes)) curve "contains" in {
      case (collection, index) =>
        collection.contains(index)
    }
  }

}
