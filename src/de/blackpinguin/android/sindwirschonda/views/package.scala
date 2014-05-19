package de.blackpinguin.android.sindwirschonda

import de.blackpinguin.android.sindwirschonda.si._

import scala.language.implicitConversions

package object views {
  implicit def SIInput2SIVal(v: SIValueInput): SIValue = v.getValue
  implicit def SIOutput2SIVal(v: SIValueOutput): SIValue = v.getValue
  
  implicit def SIInput2SIUnit(v: SIValueInput): SIUnit = v.getUnit
  implicit def SIOutput2SIUnit(v: SIValueOutput): SIUnit = v.getUnit
  
  implicit class InputExteded(self: SIValueInput){
    def :=(other: SIValue) = self.setValue(other)
    def *(other: SIValue) = self.getValue * other
    def /(other: SIValue) = self.getValue / other
  }
  
  implicit class OutputExteded(self: SIValueOutput){
    def :=(other: SIValue) = self.setValue(other)
    def *(other: SIValue) = self.getValue * other
    def /(other: SIValue) = self.getValue / other
  }
}