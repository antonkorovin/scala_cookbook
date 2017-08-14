package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen

import scala.collection.mutable

/**
  * @author antonk
  * @since 8/8/17 - 6:22 PM
  */
trait MutableCollections
  extends Sizes
    with MutableMaps
    with MutableSets {

  val arraybuffers: Gen[mutable.ArrayBuffer[Int]] = {
    sizes.map(size => entries(size).to[mutable.ArrayBuffer]).cached
  }

  val listbuffers: Gen[mutable.ListBuffer[Int]] = {
    sizes.map(size => entries(size).to[mutable.ListBuffer])
  }

  val stringbuilders: Gen[mutable.StringBuilder] = {
    sizes.map(size => new mutable.StringBuilder("*" * size)).cached
  }


  val mutablelists: Gen[mutable.MutableList[Int]] = {
    sizes.map(size => entries(size).to[mutable.MutableList]).cached
  }

  val mutablequeues: Gen[mutable.Queue[Int]] = {
    sizes.map(size => entries(size).to[mutable.Queue]).cached
  }

  val arrayseqs: Gen[mutable.ArraySeq[Int]] = {
    sizes.map(size => entries(size).to[mutable.ArraySeq]).cached
  }


  val arraystacks: Gen[mutable.ArrayStack[Int]] = {
    sizes.map(size => entries(size).to[mutable.ArrayStack]).cached
  }
}
