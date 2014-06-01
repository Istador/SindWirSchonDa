package de.blackpinguin.android.sindwirschonda.activities


import de.blackpinguin.android.sindwirschonda.R
import android.graphics.PorterDuff
import android.graphics.Color
import android.graphics.LightingColorFilter

class MainActivity extends ButtonsActivity {
  
  override def viewHomeAsBack = false
  
  override def onCreate(state: android.os.Bundle) = {
    this += R.string.calcTime -> Right(classOf[CalcTimeActivity])
    this += R.string.calcDistance -> Right(classOf[CalcDistanceActivity])
    this += R.string.calcSpeed -> Right(classOf[CalcSpeedActivity])
    super.onCreate(state)
    
    //Buttons einfärben
    buttons(0).getBackground.setColorFilter(new LightingColorFilter(0xFF000000, 0x00CCFFCC))
    buttons(1).getBackground.setColorFilter(new LightingColorFilter(0xFF000000, 0x00FFCCCC))
    buttons(2).getBackground.setColorFilter(new LightingColorFilter(0xFF000000, 0x00CCCCFF))
  }
    
}