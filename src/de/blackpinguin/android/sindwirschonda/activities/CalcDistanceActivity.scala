package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._

class CalcDistanceActivity extends CalcActivity {
  
  def changed = c := (a * b) toUnit c
  
  def getMenuID = R.menu.calc_distance
  def getLayoutID = R.layout.activity_calc_distance
  def getContentID = R.id.aCalcDistance
}