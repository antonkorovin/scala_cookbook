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

    test("unapply regexp") {
      val pattern = "^([^=]+)=([^=]+)$".r
      val str = "keyString=valueString"


     str match {
       case pattern(k, v) =>
         k shouldEqual "keyString"
         v shouldEqual "valueString"

       case _ =>
         fail()
     }
    }

  test("replacing by pattern") {
    val address = "123 Main Street".replaceAll("[0-9]", "x")

    address shouldEqual "xxx Main Street"
  }
}
