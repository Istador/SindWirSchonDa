package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.util.GPS
import de.blackpinguin.android.util.GPS._

class SelectDistanceActivity extends ASelectActivity {
  
  override def onCreate(state: android.os.Bundle) = {
    
    val hh = GPS(53.550556, 9.993333)
    val ber = GPS(52.518611, 13.408056)
    val nyc = GPS(40.712778, -74.005833)
    
    this += getStr(R.string.measure) -> Right{classOf[MeasureDistanceActivity]}
    
    this += "Hamburg \u2194 Berlin" -> siresult(SIValue(hh.to(ber) / 1000.0, SIDistance.km))
    this += "Hamburg \u2194 New York City" -> siresult(SIValue(hh.to(nyc) / 1000.0, SIDistance.km))
    this += getStr(R.string.dist_moon) -> siresult(SIValue(400000, SIDistance.km))
    this += getStr(R.string.dist_sun) -> siresult(SIValue(1.0, SIDistance.au))
    this += getStr(R.string.dist_mars) -> siresult(SIValue(1.5275, SIDistance.au))
    this += getStr(R.string.dist_nextpsys) -> siresult(SIValue(4.34, SIDistance.lj))
    this += getStr(R.string.dist_nextgalaxy) -> siresult(SIValue(25000, SIDistance.lj))
    
    super.onCreate(state)
  }
  
}