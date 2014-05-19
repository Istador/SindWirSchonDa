package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._

class CalcDistanceActivity extends CalcActivity {
  
  def changed = c := (a * b) toUnit c
  
  def getMenu = R.menu.calc_distance
  def getLayout = R.layout.activity_calc_distance
  def getLayoutId = R.id.aCalcDistance
}