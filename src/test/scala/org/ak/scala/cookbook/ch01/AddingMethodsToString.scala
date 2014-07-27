package org.ak.scala.cookbook.ch01

import org.ak.scala.cookbook.StringUtils
import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/27/14 - 8:38 AM
 */
class AddingMethodsToString extends FunSuite with Matchers {
  test("adding isValid method") {
    import StringUtils._

    val s1: String = null
    val s2: String = ""
    val s3: String = "   "
    val s4: String = "validString"


    s1.isValid shouldEqual false
    s2.isValid shouldEqual false
    s3.isValid shouldEqual false
    s4.isValid shouldEqual true
  }
}
