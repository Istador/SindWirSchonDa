package de.blackpinguin.android.sindwirschonda.activities

import android.graphics.LightingColorFilter
import de.blackpinguin.android.sindwirschonda.R

class MainActivity extends AButtonsActivity {

  override def viewHomeAsBack = false

  override def onCreate(state: android.os.Bundle) = {
    this += R.string.calcTime -> Right(classOf[CalcTimeActivity])
    this += R.string.calcDistance -> Right(classOf[CalcDistanceActivity])
    this += R.string.calcSpeed -> Right(classOf[CalcSpeedActivity])

    super.onCreate(state)

    //Buttons einfärben
    buttons(0).getBackground.setColorFilter(new LightingColorFilter(0xFF000000, 0x0099FF99))
    buttons(1).getBackground.setColorFilter(new LightingColorFilter(0xFF000000, 0x00FF9999))
    buttons(2).getBackground.setColorFilter(new LightingColorFilter(0xFF000000, 0x009999FF))
  }

}