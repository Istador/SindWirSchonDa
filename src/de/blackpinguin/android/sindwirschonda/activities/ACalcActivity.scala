package de.blackpinguin.android.sindwirschonda.activities

import android.app.Activity
import android.content.Intent
import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.sindwirschonda.views._

abstract class ACalcActivity extends ASimpleActivity {

  //sicher gehen, dass die Konstruktoren der Objekte aufgerufen werden
  SIDistance; SISpeed; SITime

  //die 3 Views, zwei Eingaben und eine Ausgabe
  protected lazy val a = content.getChildAt(0).asInstanceOf[SIValueInput]
  protected lazy val b = content.getChildAt(2).asInstanceOf[SIValueInput]
  protected lazy val c = content.getChildAt(6).asInstanceOf[SIValueOutput]

  //Rechenoperation die bei Änderungen der Eingaben ausgeführt wird
  protected def changed: Unit

  protected override def onCreate(state: android.os.Bundle) = {
    super.onCreate(state)

    //Callbacks setzen
    a.callback = { _ => changed }
    b.callback = a.callback
  }

  //Ergebnis von anderen Activities an das View weitergeben
  protected override def onActivityResult(req: Int, res: Int, data: Intent) =
    if (res == Activity.RESULT_OK)
      SIValueInput(req).foreach { _.onResult(data) }

}