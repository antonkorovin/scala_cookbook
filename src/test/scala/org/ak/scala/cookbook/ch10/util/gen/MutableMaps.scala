package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen
import org.scalameter.Gen.Collections

import scala.collection.mutable

/**
  * @author antonk
  * @since 8/9/17 - 6:30 PM
  */
trait MutableMaps
  extends Collections
    with MapEntries {

  val mutableHashMaps: Gen[mutable.HashMap[Int, Int]] = {
    for {
      size <- sizes
    } yield {
      mutable.HashMap(
        mapEntries(size): _*
      )
    }
  }.cached

  val mutableWeakHashMaps: Gen[mutable.WeakHashMap[Int, Int]] = {
    for {
      size <- sizes
    } yield {
      mutable.WeakHashMap(
        mapEntries(size): _*
      )
    }
  }.cached
}
