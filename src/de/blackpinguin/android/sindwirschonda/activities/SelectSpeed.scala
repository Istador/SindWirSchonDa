package de.blackpinguin.android.sindwirschonda.activities

import android.widget.Toast

class SelectSpeed extends SelectActivity {
  
  override def onCreate(state: android.os.Bundle) = {
    this += "Measure..." -> Left{() => Toast.makeText(this, "Measurement... <TODO/>", Toast.LENGTH_SHORT).show }
    this += "Dummy 1" -> Right(classOf[CalcTimeActivity])
    this += "Dummy 2" -> Right(classOf[CalcDistanceActivity])
    this += "Dummy 3" -> Right(classOf[CalcSpeedActivity])
    super.onCreate(state)
  }
}