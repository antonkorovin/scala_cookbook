package org.ak.scala.cookbook.ch10.util.gen

import org.scalameter.api.Gen

/**
  * @author antonk
  * @since 8/11/17 - 07:57 AM
  */
trait Indexes extends Sizes {

  import org.scalameter.picklers.Implicits._

  def indexes: Gen[Int] = {
    sizes.flatMap(
      s =>
        Gen.enumeration("indexes")(0, s / 2, s - 1)
    )
  }
}
