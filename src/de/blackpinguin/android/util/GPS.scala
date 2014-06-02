package de.blackpinguin.android.util

import de.blackpinguin.android.util._
import android.location.Location
import android.content.Context
import android.app.Activity

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

  def toStr(loc: Option[Location]) =
    if (loc.isDefined)
      "Pos(" + loc.get.getLatitude + ", " + loc.get.getLongitude + ")"
    else
      "NoPos"

  def apply(lat: Double, lon: Double): Location = {
    val l = new Location("dummyprovider")
    l.setLatitude(lat)
    l.setLongitude(lon)
    l
  }

  def start(implicit context: Context): Listener = new GPSFused
  
  trait Listener {
    def callback: Option[GPS.Callback]
    def callback_=(cb: GPS.Callback): Unit
    def timing: Int
    def timing_=(t: Int): Unit
    def stop: Unit
    def start: Unit
  }

}

