package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.Indexes
import org.scalameter.api.Gen

import scala.collection.MapLike

/**
  * @author antonk
  * @since 8/11/17 - 8:37 PM
  */
trait MeasureLookup {
  self: Indexes =>

  def measureLookupFor(gen: Gen[_ <: MapLike[Int, _, _]]) = {
    using(Gen.crossProduct(gen, indexes)) curve "apply" in {
      case (collection, index) =>
        collection.get(index)
    }
  }


}
