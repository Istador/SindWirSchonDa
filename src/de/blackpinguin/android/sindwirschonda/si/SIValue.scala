package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt ("static")
object SIValue {

  //SIUnit lässt sich nicht einfach serialisieren, deshalb manuelle Serialisierung
  case class Serialized(value: Double, unitType: String, unit: String) {
    def unserialize: SIValue = SIValue(value, SIUnitType(unitType)(unit))
  }

  object NotAValue extends SIValue(Double.NaN, SIUnit.NaU)

  val NaV = NotAValue
}

//Klasse
case class SIValue(value: Double, unit: SIUnit) {

  //SIUnit lässt sich nicht einfach serialisieren, deshalb manuelle Serialisierung
  def serialize = SIValue.Serialized(value, unit.unitType.name, unit.abbreviation)

  //Dieses Value-Objekt umwandeln in die Basiseinheit
  lazy val toBaseUnit =
    if (unit == unit.getBaseUnit) this
    else SIValue(unit.toBaseUnit(value), unit.getBaseUnit)

  //diese Größe umrechnen in eine andere Einheit
  def toUnit(newUnit: SIUnit) =
    if (unit.getBaseUnit != newUnit.getBaseUnit)
      SIValue.NaV
    else
      newUnit.fromBaseUnit(this.toBaseUnit)

  /*
   * Operationen
   */

  //Addition
  def +(other: SIValue): SIValue =
    //nicht identische Größen
    if (unit.getBaseUnit != other.unit.getBaseUnit)
      SIValue.NaV
    //identische Größen
    else
      unit.fromBaseUnit(this.toBaseUnit.value + other.toBaseUnit.value)

  //Subtraktion
  def -(other: SIValue): SIValue =
    //nicht identische Größen
    if (unit.getBaseUnit != other.unit.getBaseUnit)
      SIValue.NaV
    //identische Größen
    else
      unit.fromBaseUnit(this.toBaseUnit.value - other.toBaseUnit.value)

  //Multiplikation
  def *(other: SIValue): SIValue = {
    //Einheit die bei der Operation resultiert
    val u = unit * other.unit
    //keine bekannte Ausgabeeinheit
    if (u == SIUnit.NaU)
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
    if (u == SIUnit.NaU)
      SIValue.NaV
    //Operation ist möglich
    else
      SIValue(toBaseUnit.value / other.toBaseUnit.value, u)
  }

  def *(scale: Double): SIValue = SIValue(value * scale, unit)
  def /(scale: Double): SIValue = SIValue(value / scale, unit)

  //andere Methodennamen für Java-Anwendungen
  def add(other: SIValue) = this + other
  def sub(other: SIValue) = this - other
  def mult(other: SIValue) = this * other
  def div(other: SIValue) = this / other
  def mult(scale: Double) = this * scale
  def div(scale: Double) = this / scale

  override def toString = value.toString + " " + unit.abbreviation
}