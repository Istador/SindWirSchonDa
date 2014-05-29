package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._

class CalcTimeActivity extends CalcActivity {
  
  def changed = c := (a / b) toUnit c
  
  def getMenuID = R.menu.calc_time
  def getLayoutID = R.layout.activity_calc_time
  def getContentID = R.id.aCalcTime
}