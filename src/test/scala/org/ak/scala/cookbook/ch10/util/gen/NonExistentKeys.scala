package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 8/14/17 - 3:24 PM
  */
trait NonExistentKeys extends Sizes {

  import org.scalameter.picklers.Implicits._

  // Use with maps and sets only
  def nonExistentKeys: Gen[Int] = {
    sizes.flatMap(
      s =>
        Gen.enumeration("nonExistentKeys")(0,  s + 1)
    )
  }
}
