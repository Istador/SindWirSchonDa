package de.blackpinguin.android.sindwirschonda.activities


import de.blackpinguin.android.sindwirschonda.R
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.view.ViewGroup

class MainActivity extends ButtonsActivity {
  
  override def viewHomeAsBack = false
  
  def getCallbacks = Array[E](
      Right(classOf[CalcTimeActivity]),
      Right(classOf[CalcDistanceActivity]),
      Right(classOf[CalcSpeedActivity])
  )
  
  def getLayoutID = R.layout.activity_main
  def getMenuID = R.menu.main
  def getContentID = R.id.aMain
  
}