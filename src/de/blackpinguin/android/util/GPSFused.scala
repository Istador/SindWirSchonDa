package de.blackpinguin.android.util

import android.os.Bundle
import android.content.Context
import com.google.android.gms.location.LocationClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.GooglePlayServicesClient
import com.google.android.gms.location.LocationListener
import android.location.Location

private[util] class GPSFused (implicit context: Context) extends GPSCommon 
  with GooglePlayServicesClient.ConnectionCallbacks
  with GooglePlayServicesClient.OnConnectionFailedListener
  with LocationListener
{

  var client: LocationClient = null
  
  if(ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(context))
    client = new LocationClient(context, this, this)
  else
    error
  
    
  private[this] def request = {
    val req = LocationRequest.create
      .setInterval(timing)
      .setFastestInterval(500)
      .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
      //.setSmallestDisplacement(1f) //1m 
    client.requestLocationUpdates(req, this)
  }
    
  def onConnected(b: Bundle) = {
    request
    running = true
  }
  
  def onDisconnected = {
    running = false
  }
  
  def onConnectionFailed(res: ConnectionResult) = error
  
  def onLocationChanged(loc: Location) = onLocation(loc)

  override protected[this] def restart = synchronized {
    if(running){
      client.removeLocationUpdates(this)
      request
    }
  }
  
  def stop = synchronized {
    if (running) client.disconnect
  }

  def start = synchronized {
    if (!running) client.connect
  }

  start
}