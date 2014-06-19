package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.sindwirschonda.views.ChartView
import de.blackpinguin.android.sindwirschonda.views.ChartView.Point
import de.blackpinguin.android.util.GPS
import de.blackpinguin.android.util.GPS._
import android.widget.{ Button, ToggleButton, TextView, SeekBar }
import android.location.Location

class MeasureSpeedActivity extends ASimpleActivity {

  def getLayoutID = R.layout.activity_measure_speed
  def getContentID = R.id.aMeasureSpeed

  protected lazy val textSpeed = findViewById(R.id.textSpeed).asInstanceOf[TextView]
  protected lazy val chart = findViewById(R.id.chartView).asInstanceOf[ChartView]
  protected lazy val seekBar = findViewById(R.id.seekBar).asInstanceOf[SeekBar]
  protected lazy val butOk = findViewById(R.id.butOK).asInstanceOf[Button]
  protected lazy val butStartStop = findViewById(R.id.butStartStop).asInstanceOf[ToggleButton]

  private[this] var lastLocation: Option[Location] = None

  private[this] val zero = SIValue(0.0, SISpeed.km_per_h)

  private[this] var pointCount = 0
  private[this] var _speed = zero
  private[this] def speed = _speed
  private[this] def speed_=(si: SIValue): Unit = {
    _speed = si
    textSpeed.setText(
      (if(pointCount == 0) speed.value else speed.value / pointCount)
        .formatted("%.3f").toString+" "+speed.unit) //ausgeben
  }
  
  private[this] var gps: GPS.Listener = null

  private[this] var time = 5000
  
  def chartify(s: Double, t: Long = System.nanoTime) = chart + Point(t.toDouble, s)
  
  val callback = { location: Option[Location] =>
    (location, lastLocation) match {
      //erster Messpunkt
      case (Some(now), None) =>
        chartify(0.0)
        lastLocation = location
        speed = zero
      //folgende Messpunkte
      case (Some(now), Some(last)) =>
        //Entfernung zwischen beiden Koordinaten berechnen (in metern)
        val dist = Math.abs(last.to(now).toDouble)
        
        //Zeit zwischen beiden Koordinaten (in sekunden)
        val t = Math.abs(now.getElapsedRealtimeNanos - last.getElapsedRealtimeNanos).toDouble * 0.000000001
        
        //Geschwindigkeit
        var v = dist / t

        //betrachte Geschwindigkeiten kleiner als 0.5 m/s als Messfehler
        if(v < 0.5)
          v = 0.0
        
        //anzeige auf chart
        chartify(v, now.getElapsedRealtimeNanos)
        
        lastLocation = location //Position merken
        pointCount += 1
        speed += SIValue(v, SISpeed.m_per_s) //aufaddieren
          
        //wenn sich die Zeit verändert hat
        if(time != gps.timing)
          gps.timing = time
        
      //Fehlermeldung von GPS.Listener
      case (None, _) =>
        alert("Unknown Error")
        finish
    }
  }
  
  
  override def onCreate(state: android.os.Bundle) = {
    super.onCreate(state)
    
    //beim erstellen der Activity anfangen, wegen der GPS-Aufwärmphase
    gps = GPS.start

    butOk onClick { _ =>
      //GPS anhalten
      gps.stop
      //Ergebnis zurückgeben an vorherige Activity
      if(pointCount == 0)
        siresult(zero).a()
      else
        siresult(speed / pointCount).a()
    }

    butStartStop onChange { state =>
      if (state) {
        gps.timing = time
        lastLocation = None
        pointCount = 0
        chart.clear
        textSpeed.setText("...")
        gps.callback = callback
      } else {
        gps.callback = null //Callback entfernen
        lastLocation = None
      }
    }
    
    seekBar onChange { progress => 
      time = (progress+10)*100
    }
  }
  
}