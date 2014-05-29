package de.blackpinguin.android.sindwirschonda.views

import de.blackpinguin.android.sindwirschonda.R
import android.util.AttributeSet
import android.content.Context

class SIValueInput (context: Context, attrs: AttributeSet) extends SIValueOutput(context, attrs) {
  
  override def getLayoutID = R.layout.lay_si_value_input
  
  val button = grid.getChildAt(1)
  
}