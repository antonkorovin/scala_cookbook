package org.ak.scala.cookbook

/**
 * @author antonk
 * @since  8/21/14 - 3:52 PM
 */
package object ch06 {
  def increment(str: String): String = str.map(c => (c + 1).toChar)
}
