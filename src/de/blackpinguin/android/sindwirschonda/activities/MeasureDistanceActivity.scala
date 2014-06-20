package de.blackpinguin.android.sindwirschonda.activities

import android.location.Location
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ToggleButton
import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.util.GPS
import de.blackpinguin.android.util.GPS._

class MeasureDistanceActivity extends ASimpleActivity {

  def getLayoutID = R.layout.activity_measure_distance
  def getContentID = R.id.aMeasureDistance

  protected lazy val textDistance = findViewById(R.id.textDistance).asInstanceOf[TextView]
  protected lazy val seekBar = findViewById(R.id.seekBar).asInstanceOf[SeekBar]
  protected lazy val butOk = findViewById(R.id.butOK).asInstanceOf[Button]
  protected lazy val butStartStop = findViewById(R.id.butStartStop).asInstanceOf[ToggleButton]

  private[this] var lastLocation: Option[Location] = None

  private[this] val zero = SIValue(0.0, SIDistance.m)

  private[this] var _distance = zero
  private[this] def distance = _distance
  private[this] def distance_=(si: SIValue): Unit = {
    _distance = si
    if (distance > 1000.0 && distance.unit == SIDistance.m)
      _distance = distance.toUnit(SIDistance.km)
    textDistance.setText(distance.value.formatted("%.3f").toString + " " + distance.unit) //ausgeben
  }

  private[this] var gps: GPS.Listener = null

  private[this] var time = 5000

  val callback = { location: Option[Location] =>
    (location, lastLocation) match {
      //erster Messpunkt
      case (Some(now), None) =>
        lastLocation = location
        distance = zero
      //folgende Messpunkte
      case (Some(now), Some(last)) =>
        //Entfernung zwischen beiden Koordinaten berechnen
        val dist = Math.abs(last to now)
        //Bewegung nur wenn über Schwellwert
        if (dist > 1.5) { //1.5 m
          lastLocation = location //Position merken
          distance += SIValue(dist, SIDistance.m) //aufaddieren

          //wenn sich die Zeit verändert hat
          if (time != gps.timing)
            gps.timing = time
        }
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
      siresult(distance).a()
    }

    butStartStop onChange { state =>
      if (state) {
        gps.timing = time
        lastLocation = None
        textDistance.setText("...")
        gps.callback = callback
      } else {
        gps.callback = null //Callback entfernen
        lastLocation = None
      }
    }

    seekBar onChange { progress =>
      time = (progress + 10) * 100
    }
  }

  override def onDestroy = { gps.stop; super.onDestroy }
}