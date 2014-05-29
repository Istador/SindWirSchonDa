package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt
object SITime {
  
  private var cache: Map[String, SIUnit] = Map() 
  def apply(abbr: String) = cache.getOrElse(abbr, SIUnit.NaU)
  
  val s = new SITime("s", 1.0)
  val m = new SITime("m", 60.0)
  val h = new SITime("h", m.baseUnitMultiplier * 60.0)
  val d = new SITime("d", h.baseUnitMultiplier * 24.0)
  val a = new SITime("a", d.baseUnitMultiplier * 365.25)
  
}

//Klasse
case class SITime private(_abbr: String, _bum: Double)
  extends SIUnit(_abbr, _bum) {
  
  //Teil der Konstruktormethode
  SITime.cache += abbreviation -> this
  
  def getBaseUnit = SITime.s
  
  def *(other: SIUnit) = other match {
    case _: SISpeed => SIDistance.m
    case _ => SIUnit.NaU
  }
  
  def /(other: SIUnit) = SIUnit.NaU
  
}