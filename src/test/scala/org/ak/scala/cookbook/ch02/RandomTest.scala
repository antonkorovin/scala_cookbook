package org.ak.scala.cookbook.ch02

import org.ak.scala.cookbook.StringUtils
import org.scalatest.{Matchers, FunSuite}

import scala.util.Random

/**
 * @author antonk
 * @since  7/27/14 - 12:27 PM
 */
class RandomTest extends FunSuite with Matchers {
  test("generating ints") {
    val rnd = new Random

    for (i <- 0 to 100) {
      val generated = rnd.nextInt(100)
      generated should be >= 0
      generated should be <  100
    }
  }



  test("generating string") {
    val rnd = new Random
    val expectedLength = rnd.nextInt(100) + 1
    // It could be not printable
    val str = rnd.nextString(expectedLength)

    str should have size expectedLength
  }



  test("generating printable string") {
    val rnd = new Random
    val expectedLength = rnd.nextInt(100) + 100
    val str = for (i <- 1 to expectedLength) yield rnd.nextPrintableChar()

    str should have size expectedLength
    str.foreach{ c =>
      if (!StringUtils.isPrintable(c)) fail(s"'$c' isn't printable.")
    }
  }



  test("generating alphanumeric string") {
    val rnd = new Random
    val expectedLength = rnd.nextInt(100) + 100

    val s = rnd.alphanumeric.take(expectedLength).mkString

    s should have size expectedLength
    s.foreach{ c =>
      if (!c.isLetterOrDigit) fail(s"'$c' isn't alphanumeric.")
    }
  }



  test("collection shuffle") {
    val array = (1 to 42).toIndexedSeq
    val shuffledArray = Random.shuffle(array)

    array should be (sorted)
    shuffledArray should not be sorted
  }
}
