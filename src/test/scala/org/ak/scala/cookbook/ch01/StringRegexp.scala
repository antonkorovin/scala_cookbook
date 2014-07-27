package org.ak.scala.cookbook.ch01

import org.scalatest.{Matchers, FunSuite}

/**
 * @author antonk
 * @since  7/26/14 - 11:56 PM
 */
class StringRegexp extends FunSuite with Matchers {
    test("creating regexp with r postfix") {
      val pattern = "^([^=]+)=([^=]+)$".r
      val str = "key=value"

      val matches = pattern.findAllIn(str)

      matches.next()
      matches.groupCount shouldEqual 2
      matches.group(1) shouldEqual "key"
      matches.group(2) shouldEqual "value"
    }

}
