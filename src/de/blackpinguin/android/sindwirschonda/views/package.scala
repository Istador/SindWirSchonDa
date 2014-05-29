package de.blackpinguin.android.sindwirschonda

import de.blackpinguin.android.sindwirschonda.si._

import scala.language.implicitConversions

package object views {
  implicit def SIInput2SIVal(v: SIValueInput): SIValue = v.value
  implicit def SIOutput2SIVal(v: SIValueOutput): SIValue = v.value
  
  implicit def SIInput2SIUnit(v: SIValueInput): SIUnit = v.unit
  implicit def SIOutput2SIUnit(v: SIValueOutput): SIUnit = v.unit
  
  /*
  implicit class InputExteded(self: SIValueInput){
    def :=(other: SIValue) = { self.value = other }
    def *(other: SIValue) = self.value * other
    def /(other: SIValue) = self.value / other
  }
  
  implicit class OutputExteded(self: SIValueOutput){
    def :=(other: SIValue) = { self.value = other }
    def *(other: SIValue) = self.value * other
    def /(other: SIValue) = self.value / other
  }
  */
  
}