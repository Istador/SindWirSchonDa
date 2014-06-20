package de.blackpinguin.android.util

import android.app.Activity
import android.content.Context
import android.location.Location

//Objekt f�r den Zugriuff von Au�en
object GPS {

  implicit class ExtLocation(loc: Location) {
    def to(dest: Location): Double = loc.distanceTo(dest).toDouble
  }

  //type Callback = Option[Location] => Unit
  implicit class Callback(f: Option[Location] => Unit)(implicit a: Activity) {
    def apply(l: Option[Location]) = {
      a runOnUiThread { () => f(l) }
    }
  }

  //toString f�r Location Objekte
  def toStr(loc: Option[Location]) =
    if (loc.isDefined)
      "Pos(" + loc.get.getLatitude + ", " + loc.get.getLongitude + ")"
    else
      "NoPos"

  //Location-Objekt f�r latitude und longitude Objekte
  def apply(lat: Double, lon: Double): Location = {
    val l = new Location("dummyprovider")
    l.setLatitude(lat)
    l.setLongitude(lon)
    l
  }

  //erzeugt einen neuen Location-Listener
  def start(implicit context: Context): Listener = new GPSFused

  //Interface
  trait Listener {
    def callback: Option[GPS.Callback]
    def callback_=(cb: GPS.Callback): Unit
    def timing: Int
    def timing_=(t: Int): Unit
    def stop: Unit
    def start: Unit
  }

}

