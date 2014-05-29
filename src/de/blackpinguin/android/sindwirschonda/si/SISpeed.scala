package de.blackpinguin.android.sindwirschonda.si

//Begleitobjekt
object SISpeed {
  
  private var cache: Map[String, SIUnit] = Map() 
  def apply(abbr: String) = cache.getOrElse(abbr, SIUnit.NaU)
  
  val km_per_h = new SISpeed("km/h", 1000.0 / (60.0*60.0))
  val kn = new SISpeed("kn", 463.0 / 900.0)
  val m_per_s = new SISpeed("m/s", 1.0)
  val km_per_s = new SISpeed("km/s", 1000.0)
  
}

//Klasse
case class SISpeed private(_abbr: String, _bum: Double)
  extends SIUnit(_abbr, _bum) {
  
  //Teil der Konstruktormethode
  SISpeed.cache += abbreviation -> this
  
  def getBaseUnit = SISpeed.m_per_s
  
  def *(other: SIUnit) = other match {
    case _: SITime => SIDistance.m
    case _ => SIUnit.NaU
  }
  
  def /(other: SIUnit) = SIUnit.NaU
  
}