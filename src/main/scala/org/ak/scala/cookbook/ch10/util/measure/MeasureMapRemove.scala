package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.Indexes
import org.scalameter.api.Gen

import scala.collection.MapLike

/**
  * @author antonk
  * @since 8/14/17 - 7:26 PM
  */
trait MeasureMapRemove {
  self: Indexes =>

  def measureMapRemoveFor(gen: Gen[_ <: MapLike[Int, _, _]]): Unit = {
    using(Gen.crossProduct(gen, indexes)) curve "remove" in {
      case (collection, index) =>
        collection - index
    }
  }

}
