package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt ("static")
object SIUnit {

  object NotAUnit extends SIUnit("NaU", Double.NaN) {
    override def getBaseUnit = this
    def *(other: SIUnit) = this
    def /(other: SIUnit) = this
  }

  val NaU = NotAUnit
}

//Klasse
abstract class SIUnit(val abbreviation: String, val baseUnitMultiplier: Double) {

  //relection um das Begleitobjekt der konkreten Klasse zu bekommen
  lazy val unitType = {
    //yay, reflection!
    val c = Class.forName(getClass.getName + "$") //Begleitobjekt von "Klasse" ist "Klasse$"
    c.getField("MODULE$").get(c).asInstanceOf[SIUnitType[_]]
  }

  //Teil der Konstruktormethode: füge die Einheit dem Einheitentyp hinzu
  if (!abbreviation.equals("NaU"))
    unitType += this

  //Basiseinheit dieser Einheit
  def getBaseUnit: SIUnit = unitType.baseUnit

  //welche Einheit kommt raus, wenn man diese mit einer anderen multipliziert?
  def *(other: SIUnit): SIUnit
  def mult(other: SIUnit) = this * other

  //welche Einheit kommt raus, wenn man diese durch eine andere dividiert?
  def /(other: SIUnit): SIUnit
  def div(other: SIUnit) = this / other

  //rechnet den Wert von der Basiseinheit in diese Einheit um
  def fromBaseUnit(value: Double) =
    SIValue(value / baseUnitMultiplier, this)

  //rechnet den Wert von dieser Einheit in die Basiseinheit um
  def toBaseUnit(value: Double) =
    SIValue(value * baseUnitMultiplier, getBaseUnit)

  //Abkürzung
  override def toString = abbreviation
}