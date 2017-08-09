package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen.Collections

import scala.collection.mutable

/**
  * @author antonk
  * @since 8/8/17 - 6:22 PM
  */
trait MutableCollections
  extends Collections
    with MutableMaps
    with MutableSets {
  override val arraybuffers = super.arraybuffers.cached

  val listbuffers = {
    for {
      size <- sizes
    } yield mutable.ListBuffer(0 until size: _*)
  }.cached

  val stringbuilders = {
    for {
      size <- sizes
    } yield {
      new mutable.StringBuilder("*" * size)
    }
  }.cached


  val mutablelists = {
    for {
      size <- sizes
    } yield mutable.MutableList(0 until size: _*)
  }.cached

  val mutablequeues = {
    for {
      size <- sizes
    } yield mutable.Queue(0 until size: _*)
  }.cached

  val arrayseqs = {
    for {
      size <- sizes
    } yield mutable.ArraySeq(0 until size: _*)
  }.cached

  val mutablestacks = {
    for {
      size <- sizes
    } yield mutable.Stack(0 until size: _*)
  }.cached


  val arraystacks = {
    for {
      size <- sizes
    } yield mutable.ArrayStack(0 until size: _*)
  }.cached
}
