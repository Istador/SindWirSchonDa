package de.blackpinguin.android.sindwirschonda.activities


import de.blackpinguin.android.sindwirschonda.R

class MainActivity extends ButtonsActivity {
  
  override def viewHomeAsBack = false
  
  override def onCreate(state: android.os.Bundle) = {
    this += R.string.calcTime -> Right(classOf[CalcTimeActivity])
    this += R.string.calcDistance -> Right(classOf[CalcDistanceActivity])
    this += R.string.calcSpeed -> Right(classOf[CalcSpeedActivity])
    super.onCreate(state)
  }
    
}