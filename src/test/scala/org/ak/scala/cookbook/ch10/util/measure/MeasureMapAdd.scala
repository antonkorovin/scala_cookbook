package org.ak.scala.cookbook.ch10.util.measure

import org.ak.scala.cookbook.ch10.CollectionPerformanceResearch.using
import org.ak.scala.cookbook.ch10.util.gen.NonExistentKeys
import org.scalameter.api.Gen

import scala.collection.MapLike

/**
  * @author antonk
  * @since 8/14/17 - 7:23 PM
  */
trait MeasureMapAdd {
  self: NonExistentKeys =>

  def measureMapAddFor(gen: Gen[_ <: MapLike[Int, _, _]]): Unit = {
    // 'update' uses '+' inside:
    // def updated [V1 >: V](key: K, value: V1): Map[K, V1] = this + ((key, value))

    using(Gen.crossProduct(gen, nonExistentKeys)) curve "add" in {
      case (collection, index) =>
        collection + (index -> 42)
    }
  }

}
