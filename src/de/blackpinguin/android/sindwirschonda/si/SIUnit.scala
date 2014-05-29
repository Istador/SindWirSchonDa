package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt ("static")
object SIUnit {
  
  object NotAUnit extends SIUnit ("NaU", Double.NaN){
    def getBaseUnit = this
    def *(other: SIUnit) = this
    def /(other: SIUnit) = this
  }
  
  val NaU = NotAUnit
}

//Klasse
abstract class SIUnit(val abbreviation: String, val baseUnitMultiplier: Double) {
  
  def getBaseUnit: SIUnit
  def *(other: SIUnit): SIUnit
  def /(other: SIUnit): SIUnit
  
  def fromBaseUnit(value: Double) =
    SIValue(value / baseUnitMultiplier, this)
  
  def toBaseUnit(value: Double) = 
    SIValue(value * baseUnitMultiplier, getBaseUnit)
  
  override def toString = abbreviation
}