package de.blackpinguin.android.sindwirschonda.si

import de.blackpinguin.android.sindwirschonda.R

//Begleitobjekt
object SITime extends SIUnitType[SITime] {

  def nameID = R.string.Time
  def arrayID = R.array.time

  val s = SITime(0, 1.0)
  val m = SITime(1, 60.0)
  val h = SITime(2, m.baseUnitMultiplier * 60.0)
  val d = SITime(3, h.baseUnitMultiplier * 24.0)
  val a = SITime(4, d.baseUnitMultiplier * 365.25)

}

//Klasse
case class SITime private (_abbr: String, _bum: Double) extends SIUnit(_abbr, _bum) {

  def *(other: SIUnit) = other match {
    case _: SISpeed => SIDistance.m
    case _ => SIUnit.NaU
  }

  def /(other: SIUnit) = SIUnit.NaU

}