package org.ak.scala.cookbook.ch10.util

/**
  * @author antonk
  * @since 7/25/17 - 12:30 PM
  */
trait MethodsUnderTestAware {

  type SizeAware = {def size: Int}

  type HeadAware[T] = {def head: T}

  type TailAware[T] = {def tail: T}

}
