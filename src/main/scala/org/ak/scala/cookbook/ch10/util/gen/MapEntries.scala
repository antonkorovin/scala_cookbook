package org.ak.scala.cookbook.ch10.util.gen

/**
  * @author antonk
  * @since 8/9/17 - 6:31 PM
  */
trait MapEntries {
  private[gen] def mapEntries(size: Int) = {
    (0 until size).map {
      x => x -> x
    }.toArray
  }
}
