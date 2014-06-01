package de.blackpinguin.android.sindwirschonda

import scala.language.implicitConversions

package object si {
  
  implicit def val2unit(a: SIValue): SIUnit = a.unit
  
  implicit def val2Double(value: SIValue): Double = value.value
  
}