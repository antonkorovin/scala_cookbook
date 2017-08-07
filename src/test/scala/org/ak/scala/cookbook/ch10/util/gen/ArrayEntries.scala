package org.ak.scala.cookbook.ch10.util.gen

/**
  * @author antonk
  * @since 8/6/17 - 22:54 PM
  */
trait ArrayEntries {
  private[gen] def entries(size: Int) = {
    (0 until size).toArray
  }
}
