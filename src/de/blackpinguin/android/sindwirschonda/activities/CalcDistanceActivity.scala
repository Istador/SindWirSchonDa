package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._

class CalcDistanceActivity extends ACalcActivity {
  
  def changed = c := (a * b) toUnit c
  
  /* zum Vergleich als Java-Code: 
    public void changed() {
      c.setValue(
        a.getValue()
          .multiply( b.getValue() )
          .toUnit( c.getValue().getUnit() )
      );
    }
  */
  
  def getLayoutID = R.layout.activity_calc_distance
  def getContentID = R.id.aCalcDistance
}