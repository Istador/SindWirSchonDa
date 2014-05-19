package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R;
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._

class CalcTimeActivity extends CalcActivity {
  def changed = c := (a / b) toUnit c
  
  def getMenu = R.menu.calc_time
  def getLayout = R.layout.activity_calc_time
  def getLayoutId = R.id.aCalcTime
}