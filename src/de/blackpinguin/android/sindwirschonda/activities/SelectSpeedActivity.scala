package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.sindwirschonda.R

class SelectSpeedActivity extends ASelectActivity {
  
  val c = 299792.458
  
  override def onCreate(state: android.os.Bundle) = {
    this += getStr(R.string.measure) -> Left{() => alert("Measurement... <TODO/>") }
    
    this += getStr(R.string.speed_foot) -> siresult(SIValue(5.0, SISpeed.km_per_h))
    this += getStr(R.string.speed_bike) -> siresult(SIValue(15.0, SISpeed.km_per_h))
    this += getStr(R.string.speed_car) -> siresult(SIValue(50.0, SISpeed.km_per_h))
    this += getStr(R.string.speed_autobahn) -> siresult(SIValue(130.0, SISpeed.km_per_h))
    this += getStr(R.string.speed_airplane) -> siresult(SIValue(945.0, SISpeed.km_per_h))
    this += getStr(R.string.speed_mach1) -> siresult(SIValue(343.0, SISpeed.m_per_s))
    this += getStr(R.string.speed_rocket) -> siresult(SIValue(20.0, SISpeed.km_per_s))
    this += getStr(R.string.speed_warp1) -> siresult(SIValue(c, SISpeed.km_per_s))
    this += getStr(R.string.speed_warp2) -> siresult(SIValue(c*13, SISpeed.km_per_s))
    this += getStr(R.string.speed_warp3) -> siresult(SIValue(c*39, SISpeed.km_per_s))
    this += getStr(R.string.speed_warp4) -> siresult(SIValue(c*100, SISpeed.km_per_s))
    this += getStr(R.string.speed_warp9) -> siresult(SIValue(c*834, SISpeed.km_per_s))
    this += getStr(R.string.speed_warp10) -> siresult(SIValue(Double.PositiveInfinity, SISpeed.km_per_s))
    
    super.onCreate(state)
  }
}