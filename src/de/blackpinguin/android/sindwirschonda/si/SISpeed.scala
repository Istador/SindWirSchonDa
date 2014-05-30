package de.blackpinguin.android.sindwirschonda.si

import de.blackpinguin.android.sindwirschonda.R

//Begleitobjekt
object SISpeed extends SIUnitType[SISpeed] {
  
  def nameID = R.string.Speed
  def arrayID = R.array.speed
  
  val km_per_h = SISpeed(0, 1000.0 / 3600.0)
  val mph      = SISpeed(1, 0.44704)
  val kn       = SISpeed(2, 463.0 / 900.0)
  val m_per_s  = SISpeed(3, 1.0)
  val km_per_s = SISpeed(4, 1000.0)
  
}

//Klasse
case class SISpeed private(_abbr: String, _bum: Double) extends SIUnit(_abbr, _bum) {
    
  def *(other: SIUnit) = other match {
    case _: SITime => SIDistance.m
    case _ => SIUnit.NaU
  }
  
  def /(other: SIUnit) = SIUnit.NaU
  
}