package de.blackpinguin.android.sindwirschonda

import scala.language.implicitConversions

import de.blackpinguin.android.sindwirschonda.si._

package object views {
  implicit def SIInput2SIVal(v: SIValueInput): SIValue = v.value
  implicit def SIOutput2SIVal(v: SIValueOutput): SIValue = v.value

  implicit def SIInput2SIUnit(v: SIValueInput): SIUnit = v.unit
  implicit def SIOutput2SIUnit(v: SIValueOutput): SIUnit = v.unit
}