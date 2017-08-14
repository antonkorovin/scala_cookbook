package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.Gen

/**
  * @author antonk
  * @since 8/10/17 - 07:55 AM
  */
trait Sizes {
  def sizes: Gen[Int]
}
