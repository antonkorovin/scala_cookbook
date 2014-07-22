package org.ak.scala.cookbook.ch01

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author antonk
 * @since  7/22/14 - 3:04 PM
 */
class MultilineStrings extends FlatSpec with Matchers {
  "Multiline string" should "be created by surrounding with three double quotes" in {
    val str ="""multiline
                string"""


    str should fullyMatch regex "^multiline\n[ ]{16}string$"
  }



  it should "not have whitespaces with left-justified every line after the first" in {
    val str =
      """multiline
string"""


    str should fullyMatch regex "^multiline\nstring$"
  }



  it should "not have whitespaces using pipe symbol" in {
    // Strip a leading prefix consisting of blanks or
    // control characters followed by | from the line.
    val str =
      """multiline
         |string""".stripMargin


    str should fullyMatch regex "^multiline\nstring$"
  }



  it should "not have whitespaces using any other character" in {
    // Strip a leading prefix consisting of blanks or
    // control characters followed by 'marginChar' from the line.
    val str =
      """multiline
        #string""".stripMargin('#')


    str should fullyMatch regex "^multiline\nstring$"
  }



  it should "be available include single- and double-quotes" in {
    val str =
      """ "multiline"
        |'string' """.stripMargin


    str should fullyMatch regex "^ [\"]multiline[\"]\n[\']string[\'] $"
  }
}

