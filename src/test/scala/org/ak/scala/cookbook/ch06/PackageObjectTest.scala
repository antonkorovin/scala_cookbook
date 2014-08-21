package org.ak.scala.cookbook.ch06

import org.scalatest.{FunSuite, Matchers}

/**
 * @author antonk
 * @since  8/21/14 - 3:54 PM
 */
class PackageObjectTest
  extends FunSuite
          with Matchers {

  test("using function from package object") {
    // Does not require import
    increment("HAL") shouldEqual "IBM"
  }
}
