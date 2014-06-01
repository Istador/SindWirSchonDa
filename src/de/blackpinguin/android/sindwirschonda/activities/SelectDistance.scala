package de.blackpinguin.android.sindwirschonda.activities

import android.widget.Toast
import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.util.GPS

class SelectDistance extends SelectActivity {
  
  override def onCreate(state: android.os.Bundle) = {
    this += getStr(R.string.measure) -> Left{() => Toast.makeText(this, "Measurement... <TODO/>", Toast.LENGTH_SHORT).show }
    //this += "<current>" -> Left{() => GPS.getCurrent{loc => println(GPS.toStr(loc))}(this) }
    this += "Hamburg \u2194 Berlin" -> siresult(SIValue(255.64, SIDistance.km))
    this += "Hamburg \u2194 New York City" -> siresult(SIValue(6129.31, SIDistance.km))
    this += getStr(R.string.dist_moon) -> siresult(SIValue(400000, SIDistance.km))
    this += getStr(R.string.dist_sun) -> siresult(SIValue(1.0, SIDistance.au))
    this += getStr(R.string.dist_mars) -> siresult(SIValue(1.5275, SIDistance.au))
    this += getStr(R.string.dist_nextpsys) -> siresult(SIValue(4.34, SIDistance.lj))
    this += getStr(R.string.dist_nextgalaxy) -> siresult(SIValue(25000, SIDistance.lj))
    super.onCreate(state)
    
  }
  
}