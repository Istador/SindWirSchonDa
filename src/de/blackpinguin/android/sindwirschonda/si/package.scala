package de.blackpinguin.android.sindwirschonda

import scala.language.implicitConversions

package object si {
  
  implicit def val2unit(a: SIValue): SIUnit = a.unit
  
  implicit def val2Double(value: SIValue): Double = value.value
  
  /*
  implicit class ValueExtended(self: SIValue){
    def +(other: SIValue) = self.add(other)
    def *(other: SIValue) = self.multiply(other)
    def /(other: SIValue) = self.divide(other)
  }
  
  implicit class UnitExtended(self: SIUnit){
    def *(other: SIUnit) = self.multiply(other)
    def /(other: SIUnit) = self.divide(other)
  }
  */
}