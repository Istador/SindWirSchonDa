package de.blackpinguin.android.sindwirschonda.si

import de.blackpinguin.android.sindwirschonda.R

//Begleitobjekt
object SIDistance extends SIUnitType[SIDistance] {
  
  def nameID = R.string.Distance
  def arrayID = R.array.distance
  
  val in = SIDistance(0, 0.0254)
  val ft = SIDistance(1, 0.3048)
  val yd = SIDistance(2, 0.9144)
  val m  = SIDistance(3, 1.0)
  val km = SIDistance(4, 1000.0)
  val mi = SIDistance(5, 1609.344)
  val au = SIDistance(6, 149597870700.0)
  val lj = SIDistance(7, 9460730472580800.0)
  val pc = SIDistance(8, au.baseUnitMultiplier * (648000.0 / Math.PI ))
  
}

//Klasse
case class SIDistance private(_abbr: String, _bum: Double) extends SIUnit(_abbr, _bum) {
  
  def *(other: SIUnit) = SIUnit.NaU
  
  def /(other: SIUnit) = other match {
    case _: SISpeed => SITime.s
    case _: SITime => SISpeed.m_per_s
    case _ => SIUnit.NaU
  }
  
}