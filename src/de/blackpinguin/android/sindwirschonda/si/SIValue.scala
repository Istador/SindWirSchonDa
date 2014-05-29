package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt ("static")
object SIValue {
  
  object NotAValue extends SIValue(Double.NaN, SIUnit.NaU)
  
  val NaV = NotAValue
}

//Klasse
case class SIValue(value: Double, unit: SIUnit) {
  
  //Dieses Value-Objekt umwandeln in die Basiseinheit
  lazy val toBaseUnit =
    if(unit == unit.getBaseUnit) this
    else SIValue(unit.toBaseUnit(value), unit.getBaseUnit)
  
  def toUnit(newUnit: SIUnit) = 
    if(unit.getBaseUnit != newUnit.getBaseUnit)
      SIValue.NaV
    else
      newUnit.fromBaseUnit(this.toBaseUnit)
  
  /*
   * Operationen
   */
  
  //Addition
  def +(other: SIValue): SIValue =
    //nicht identische Größen
    if(unit.getBaseUnit != other.unit.getBaseUnit)
      SIValue.NaV
    //identische Größen
    else
      unit.fromBaseUnit(this.toBaseUnit.value + other.toBaseUnit.value)
  
  //Subtraktion
  def -(other: SIValue): SIValue = 
    //nicht identische Größen
    if(unit.getBaseUnit != other.unit.getBaseUnit)
      SIValue.NaV
    //identische Größen
    else
      unit.fromBaseUnit(this.toBaseUnit.value - other.toBaseUnit.value)
  
  //Multiplikation
  def *(other: SIValue): SIValue = {
    //Einheit die bei der Operation resultiert
    val u = unit * other.unit
    //keine bekannte Ausgabeeinheit
    if(u == SIUnit.NaU)
      SIValue.NaV
    //Operation ist möglich
    else
      SIValue(toBaseUnit.value * other.toBaseUnit.value, u)
  }
  
  //Division
  def /(other: SIValue): SIValue = {
    //Einheit die bei der Operation resultiert
    val u = unit / other.unit
    //keine bekannte Ausgabeeinheit
    if(u == SIUnit.NaU)
      SIValue.NaV
    //Operation ist möglich
    else
      SIValue(toBaseUnit.value / other.toBaseUnit.value, u)
  }
  
  
}