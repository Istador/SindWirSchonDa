package de.blackpinguin.android.util

import android.location.Location
import android.content.Context
import android.location.LocationManager
import android.location.LocationListener
import android.location.Criteria
import android.os.Bundle
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object GPS {
  
  type Callback = Option[Location] => Unit
  
  def toStr(loc: Option[Location]) = 
    if(loc.isDefined)
      "Pos("+loc.get.getLatitude+", "+loc.get.getLongitude+")"
    else
      "NoPos"
  
  def getCurrent(callback: Callback)(implicit context: Context) = new ContinuousUpdates(callback)
  
  def distance(a: Location, b: Location): Double = 0.0
  
}

class ContinuousUpdates(callback: GPS.Callback)(implicit context: Context) extends LocationListener {
  private[this] val locMan = context.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]
  private[this] val cri = new Criteria()
  cri.setAccuracy(Criteria.ACCURACY_FINE)
  cri.setHorizontalAccuracy(Criteria.ACCURACY_HIGH)
  cri.setVerticalAccuracy(Criteria.ACCURACY_HIGH)
  
  private[this] var _timing = 500
  def timing = _timing
  def timing_=(t: Int) = { 
    _timing = t
    restart
  }
  
  private[this] var running = false
  
  private[this] def restart = { stop ; start }
  
  def stop = if(running){
    locMan.removeUpdates(this)
    running = false
  }
  
  def start = if(!running){
    Option(locMan.getBestProvider(cri, false)) match {
      case Some(pro) =>
        locMan.requestLocationUpdates(pro, _timing, 0, this)
        running = true
      case None =>
        if(callback != null) callback(None)
    }
  }
  
  def onLocationChanged(loc: Location) = if(loc != null){
    if(callback != null) callback(Some(loc))
  }
  def onProviderDisabled(pro: String) = {}
  def onProviderEnabled(pro: String) = {}
  def onStatusChanged(pro: String, status: Int, extras: Bundle) = {}
  
  start
}

class SinglePosition(callback: GPS.Callback)(implicit context: Context) extends LocationListener {
  private[this] val locMan = context.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]
  private[this] val cri = new Criteria()
  cri.setAccuracy(Criteria.ACCURACY_FINE)
  cri.setHorizontalAccuracy(Criteria.ACCURACY_HIGH)
  //cri.setVerticalAccuracy(Criteria.ACCURACY_HIGH)
  
  Option(locMan.getBestProvider(cri, false)) match {
    case Some(pro) =>
      locMan.requestSingleUpdate(pro, this, null)
    case None =>
      if(callback != null) callback(None)
  }
  
  def onLocationChanged(loc: Location) = {
    locMan.removeUpdates(this)
    if(callback != null) callback(Some(loc))
  }
  def onProviderDisabled(pro: String) = {}
  def onProviderEnabled(pro: String) = {}
  def onStatusChanged(pro: String, status: Int, extras: Bundle) = {}
  
  
}