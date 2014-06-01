package de.blackpinguin.android.sindwirschonda.activities

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si._
import android.widget.Button
import android.view.ViewGroup.LayoutParams
import android.content.Intent
import android.app.Activity

abstract class SelectActivity extends ButtonsActivity {
      
  def getLayoutID = R.layout.activity_select
  def getMenuID = R.menu.select
  def getContentID = R.id.aSelect

}