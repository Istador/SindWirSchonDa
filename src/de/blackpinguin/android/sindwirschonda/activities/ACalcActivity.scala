package de.blackpinguin.android.sindwirschonda.activities

import android.app.Activity
import android.os.Bundle
import android.view.{Menu, MenuItem, ViewGroup}
import de.blackpinguin.android.sindwirschonda.views._
import de.blackpinguin.android.sindwirschonda.si._
import android.content.Intent

abstract class ACalcActivity extends ASimpleActivity {
  
  //make sure that these are created at runtime
  SIDistance ; SISpeed ; SITime

  //die 3 Views, zwei Eingaben und eine Ausgabe
  lazy val a = content.getChildAt(0).asInstanceOf[SIValueInput]
  lazy val b = content.getChildAt(2).asInstanceOf[SIValueInput]
  lazy val c = content.getChildAt(6).asInstanceOf[SIValueOutput]

  //Rechenoperation die bei Änderungen der Eingaben ausgeführt wird
  def changed: Unit

  override def onCreate(state: android.os.Bundle) = {
    super.onCreate(state)
    
    //Callbacks setzen
    a.callback = {_ => changed}
    b.callback = a.callback
  }
  
  //Ergebnis von anderen Activities an das View weitergeben
  override def onActivityResult(req: Int, res: Int, data: Intent) = 
    if(res == Activity.RESULT_OK)
      SIValueInput(req).foreach{_.onResult(data)}

}