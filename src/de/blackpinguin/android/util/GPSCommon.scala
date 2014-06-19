package de.blackpinguin.android.util

import android.location.LocationListener
import android.os.Bundle
import android.location.Location

private[util] abstract class GPSCommon extends GPS.Listener {

  private[this] var lastPos: Option[Location] = None

  //Callback Methode über welche die Activity Locations bekommt
  private[this] var _callback: Option[GPS.Callback] = None
  def callback = _callback
  def callback_=(cb: GPS.Callback) = {
    _callback = Option(cb)
    //sofern vorhanden dem callback die letzte Position schicken
    for (c <- callback; tmp <- lastPos) {
      val pos = new Location(tmp)
      pos.setElapsedRealtimeNanos(System.nanoTime) //zum aktuellen Zeitpunkt
      c(Some(pos))
    }
    //falls ein Fehler aufgetreten ist diesen an den neuen callback schicken
    if (hasError) error
  }
  
  //mindest Zeitabstände zwischen updates
  private[this] var _timing = 5000
  def timing = _timing
  def timing_=(t: Int) = {
    _timing = t
    if(running) restart
  }
  
  private[this] var _running = false
  def running = _running
  protected[this] def running_=(r: Boolean) = _running = r

  protected[this] def restart = { stop; start }
  
  //"Fehlermeldung"
  private[this] var hasError = false
  protected[this] def error = {
    hasError = true
    for (cb <- callback) cb(None)
  }
  
  protected[this] def onLocation(location: Location) = 
    for (loc <- Option(location); cb <- callback) {
      lastPos = Some(loc)
      cb(lastPos)
    } 
  
}