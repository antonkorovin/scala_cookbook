package org.ak.scala.cookbook

/**
 * @author antonk
 * @since  7/27/14 - 8:48 AM
 */
object StringUtils {

  implicit class StringWithAdditional(s: String) {
    def isValid = s != null && !s.trim.isEmpty

    @throws(classOf[NumberFormatException])
    def toInt(radix: Int) = {
      if (radix == 16 && (s.startsWith("0x") || s.startsWith("0X"))) {
        Integer.parseInt(s.drop(2), radix)
      }
      else
        Integer.parseInt(s, radix)
    }
  }

}
