package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si._

class SelectTimeActivity extends ASelectActivity {

  override def onCreate(state: android.os.Bundle) = {
    this += getStr(R.string.measure) -> Right { classOf[MeasureTimeActivity] }

    this += getStr(R.string.time_moon) -> siresult(SIValue(29.53, SITime.d))
    this += getStr(R.string.time_pregnancy) -> siresult(SIValue(266.0, SITime.d))
    this += getStr(R.string.time_decade) -> siresult(SIValue(10.0, SITime.a))
    this += getStr(R.string.time_ttl) -> siresult(SIValue(80.0, SITime.a))
    this += getStr(R.string.time_p239) -> siresult(SIValue(24110.0, SITime.a))
    this += getStr(R.string.time_calc42) -> siresult(SIValue(7500000.0, SITime.a))
    this += getStr(R.string.time_u235) -> siresult(SIValue(703800000.0, SITime.a))
    this += getStr(R.string.time_earth) -> siresult(SIValue(4600000000.0, SITime.a))
    this += getStr(R.string.time_universe) -> siresult(SIValue(13798000000.0, SITime.a))

    super.onCreate(state)
  }

}