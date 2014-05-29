package de.blackpinguin.android.sindwirschonda.si


//Begleitobjekt
object SIDistance {
  
  private var cache: Map[String, SIUnit] = Map() 
  def apply(abbr: String) = cache.getOrElse(abbr, SIUnit.NaU)
  
  val ft = new SIDistance("ft", 0.3048)
  val m = new SIDistance("m", 1.0)
  val km = new SIDistance("km", 1000.0)
  val mi = new SIDistance("mi", 1609.344)
  val au = new SIDistance("AU", 149597870700.0)
  val lj = new SIDistance("ly", 9460730472580800.0)
  val pc = new SIDistance("pc", au.baseUnitMultiplier * (648000.0 / Math.PI ))
  
}

//Klasse
case class SIDistance private(_abbr: String, _bum: Double)
  extends SIUnit(_abbr, _bum) {
  
  //Teil der Konstruktormethode
  SIDistance.cache += abbreviation -> this
  
  def getBaseUnit = SIDistance.m
  
  def *(other: SIUnit) = SIUnit.NaU
  
  def /(other: SIUnit) = other match {
    case _: SISpeed => SITime.s
    case _: SITime => SISpeed.m_per_s
    case _ => SIUnit.NaU
  }
  
}