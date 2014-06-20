package de.blackpinguin.android.util

import android.os.SystemClock
import android.location.Location

//nur für dieses Package
private[util] abstract class GPSCommon extends GPS.Listener {

  //die letzte bekannte Position
  private[this] var lastPos: Option[Location] = None

  //Callback Methode über welche die Activity Locations bekommt

  //Instanzvariable
  private[this] var _callback: Option[GPS.Callback] = None

  //getter
  def callback = _callback

  //setter
  def callback_=(cb: GPS.Callback) = {
    //Instanzvariable setzen
    _callback = Option(cb)

    //sofern vorhanden der Activity über den callback die letzte Position schicken
    for (c <- callback; tmp <- lastPos) { //wenn beides nicht null
      val pos = new Location(tmp) //kopie des Location-Objektes
      pos.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos) //um die Zeit zu ändern
      c(Some(pos)) //callback aufrufen
    }

    //falls ein Fehler aufgetreten ist diesen an den neuen callback schicken
    if (hasError) error
  }

  //mindest Zeitabstände zwischen updates

  //Instanzvariable
  private[this] var _timing = 5000

  //Getter
  def timing = _timing

  //Setter
  def timing_=(t: Int) = {
    _timing = t
    if (running) restart //neu starten mit neuem timing
  }

  private[this] var _running = false //instanzvariable
  def running = _running //getter
  protected[this] def running_=(r: Boolean) = _running = r //setter

  protected[this] def restart = { stop; start }

  //"Fehlermeldung"

  //ist bereits aufgetreten
  private[this] var hasError = false

  //tritt jetzt auf
  protected[this] def error = {
    hasError = true
    for (cb <- callback) cb(None)
  }

  //Methode die von den Listener-Methoden der Subklassen aufgerufen werden soll
  protected[this] def onLocation(location: Location) =
    for (loc <- Option(location); cb <- callback) {
      lastPos = Some(loc)
      cb(lastPos)
    }

}
