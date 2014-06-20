package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.views._

class CalcTimeActivity extends ACalcActivity {

  def changed = c := (a / b) toUnit c

  def getLayoutID = R.layout.activity_calc_time
  def getContentID = R.id.aCalcTime
}