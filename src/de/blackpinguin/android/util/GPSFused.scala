package de.blackpinguin.android.util

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesClient
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.location.LocationClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest

import android.content.Context
import android.location.Location
import android.os.Bundle

//Verwenden der GooglePlay Service um mehrerer Sensoren zu genaueren "fused" Locations zu verknüfen 

//nur für dieses Package
private[util] class GPSFused(implicit context: Context) extends GPSCommon
  with GooglePlayServicesClient.ConnectionCallbacks
  with GooglePlayServicesClient.OnConnectionFailedListener
  with LocationListener {

  var client: LocationClient = null

  //prüfe, dass GooglPlay Services Verfügbar sind 
  if (ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(context))
    client = new LocationClient(context, this, this)
  else
    error

  private[this] def request = if (client != null) {
    val req = LocationRequest.create
      .setInterval(timing)
      .setFastestInterval(timing)
      .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    //.setSmallestDisplacement(1f) //1m 
    client.requestLocationUpdates(req, this)
  }

  //ConnectionCallbacks Methode
  def onConnected(b: Bundle) = { request; running = true }

  //ConnectionCallbacks Methode
  def onDisconnected = { running = false }

  //OnConnectionFailedListener Methode
  def onConnectionFailed(res: ConnectionResult) = error

  //LocationListener Methode
  def onLocationChanged(loc: Location) = onLocation(loc)

  //überschreiben um nicht stop und start aufzurufen
  override protected[this] def restart = synchronized {
    if (running && client != null) {
      client.removeLocationUpdates(this)
      request
    }
  }

  def stop = synchronized {
    if (running && client != null) client.disconnect
  }

  def start = synchronized {
    if (!running && client != null) client.connect
  }

  start
}