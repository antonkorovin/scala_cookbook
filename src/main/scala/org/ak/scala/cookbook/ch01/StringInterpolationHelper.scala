package org.ak.scala.cookbook.ch01

/**
 * @author antonk
 * @since  7/23/14 - 3:21 PM
 */
object StringInterpolationHelper {

  implicit class MyStringContext(sc: StringContext) {

    object matches {
      def unapplySeq(s: String): Option[Seq[String]] = {
        val re = (sc.parts map ("\\Q" + _ + "\\E")).mkString("^", "(.*)", "$").r
        re findFirstMatchIn s map (_.subgroups)
      }
    }

  }

}
