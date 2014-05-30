package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt ("static")
object SIUnit {
    
  object NotAUnit extends SIUnit ("NaU", Double.NaN){
    override def getBaseUnit = this
    def *(other: SIUnit) = this
    def /(other: SIUnit) = this
  }
  
  val NaU = NotAUnit
}

//Klasse
abstract class SIUnit(val abbreviation: String, val baseUnitMultiplier: Double) {
  
  //das konkrete Begleitobjekt der konkreten Klasse
  lazy val unitType = {
    //yay, reflection!
    val c = Class.forName(getClass.getName() + "$")
    c.getField("MODULE$").get(c).asInstanceOf[SIUnitType[_]]
  }
  
  //Teil der Konstruktormethode: füge die Einheit dem Einheitentyp hinzu
  if(!abbreviation.equals("NaU"))
    unitType += this
  
  def getBaseUnit: SIUnit = unitType.baseUnit
  def *(other: SIUnit): SIUnit
  def /(other: SIUnit): SIUnit
  
  def fromBaseUnit(value: Double) =
    SIValue(value / baseUnitMultiplier, this)
  
  def toBaseUnit(value: Double) = 
    SIValue(value * baseUnitMultiplier, getBaseUnit)
  
}