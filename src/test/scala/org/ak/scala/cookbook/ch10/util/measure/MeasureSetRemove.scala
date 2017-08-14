package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.Indexes
import org.scalameter.api.Gen

import scala.collection.SetLike

/**
  * @author antonk
  * @since 8/14/17 - 7:29 PM
  */
trait MeasureSetRemove {
  self: Indexes =>

  def measureSetRemoveFor(gen: Gen[_ <: SetLike[Int, _]]): Unit = {
    using(Gen.crossProduct(gen, indexes)) curve "remove" in {
      case (collection, index) =>
        collection - index
    }
  }

}
