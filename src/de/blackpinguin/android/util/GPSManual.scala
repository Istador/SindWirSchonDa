package de.blackpinguin.android.util

import android.os.Bundle
import android.location.LocationListener
import android.location.LocationManager
import android.location.Criteria
import android.content.Context
import android.location.Location

private[util] class GPSManual(implicit context: Context) extends GPSCommon with LocationListener {
  
  //Manager erstellen
  private[this] val locMan = context.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]

  //Genauigkeit setzen
  private[this] val cri = new Criteria()
  cri.setAccuracy(Criteria.ACCURACY_FINE)
  cri.setHorizontalAccuracy(Criteria.ACCURACY_HIGH)
  cri.setVerticalAccuracy(Criteria.ACCURACY_HIGH)

  //bester Location Provider für die gewünschte Genauigkeit
  private[this] val provider = Option(locMan.getBestProvider(cri, true))

  def stop = if (running) {
    locMan.removeUpdates(this)
    running = false
  }

  def start = synchronized {
    if (!running) {
      provider match {
        //es wurde ein Provider gefunden
        case Some(pro) =>
          locMan.requestLocationUpdates(pro, timing, 1, this)
          running = true
        //es wurde kein Provider gefunden
        case None =>
          error
      }
    }
  }
  
  //Listenermethoden
  def onLocationChanged(loc: Location) = onLocation(loc)
  def onProviderDisabled(pro: String) = error
  def onProviderEnabled(pro: String) = {}
  def onStatusChanged(pro: String, status: Int, extras: Bundle) = {}
  
  
  start
}