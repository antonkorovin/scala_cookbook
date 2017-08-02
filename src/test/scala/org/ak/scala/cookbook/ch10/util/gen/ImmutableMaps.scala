package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen.Collections
import org.scalameter.api.Gen

import scala.collection.immutable.{HashMap, ListMap, TreeMap}

/**
  * @author antonk
  * @since 8/1/17 - 4:55 PM
  */
trait ImmutableMaps extends Collections {

  val hashMaps: Gen[HashMap[Int, Int]] = {
    for {
      size <- sizes
    } yield {
      HashMap(
        mapEntries(size): _*
      )
    }
  }.cached


  val treeMaps: Gen[TreeMap[Int, Int]] = {
    for {
      size <- sizes
    } yield {
      TreeMap(
        mapEntries(size): _*
      )
    }
  }.cached


  val listMaps: Gen[ListMap[Int, Int]] = {
    for {
      size <- sizes
    } yield {
      ListMap(
        mapEntries(size): _*
      )
    }
  }.cached

  private def mapEntries(size: Int) = {
    (0 until size).map {
      x => x -> x
    }.toArray
  }
}
