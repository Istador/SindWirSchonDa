package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R;
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._

class CalcSpeedActivity extends CalcActivity {
  def changed = c := (a / b) toUnit c
  
  def getMenu = R.menu.calc_speed
  def getLayout = R.layout.activity_calc_speed
  def getLayoutId = R.id.aCalcSpeed
}