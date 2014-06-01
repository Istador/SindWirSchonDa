package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.util.Timer
import android.widget.{Button, ToggleButton, TextView}
import android.widget.SeekBar

class MeasureDistance extends SimpleActivity {
  
  def getLayoutID = R.layout.activity_measure_distance
  def getContentID = R.id.aMeasureDistance
  
  protected lazy val textDistance = findViewById(R.id.textDistance).asInstanceOf[TextView]
  protected lazy val seekBar = findViewById(R.id.seekBar).asInstanceOf[SeekBar]
  protected lazy val butOk = findViewById(R.id.butOK).asInstanceOf[Button]
  protected lazy val butStartStop = findViewById(R.id.butStartStop).asInstanceOf[ToggleButton]
  
  /*
  private var siv = Timer.Timestamp("00:00:00.000", 0, "s")
  
  //Timer erstellen, Callbackmethode
  private var timer = new Timer(this, {time =>
    siv = Timer.analyze(time)
    textTime.setText(siv.str)
  })
  
  override def onCreate(state: android.os.Bundle) = {
    super.onCreate(state)
    
    butOk onClick { _ =>
      //Timer anhalten
      timer.stop
      //Ergebnis zurückgeben an vorherige Activity
      siresult(SIValue(siv.value, SITime(siv.unit))).a()
    }
    
    butStartStop onChange { state => 
      if(state) timer.start //Timer starten
      else timer.stop //Timer anhalten
    }
  }
  
  override def onDestroy = { timer.stop ; super.onDestroy }
  */
}