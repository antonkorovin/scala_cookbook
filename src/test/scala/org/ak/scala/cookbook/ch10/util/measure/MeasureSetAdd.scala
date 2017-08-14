package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.NonExistentKeys
import org.scalameter.api.Gen

import scala.collection.SetLike

/**
  * @author antonk
  * @since 8/14/17 - 7:31 PM
  */
trait MeasureSetAdd {
  self: NonExistentKeys =>

  def measureSetAddFor(gen: Gen[_ <: SetLike[Int, _]]): Unit = {
    using(Gen.crossProduct(gen, nonExistentKeys)) curve "add" in {
      case (collection, index) =>
        collection + index
    }
  }

}
