package de.blackpinguin.android.sindwirschonda.activities

import android.app.Activity
import android.content.Intent
import de.blackpinguin.android.sindwirschonda.si.SIValue

abstract class ASelectActivity extends AButtonsActivity {

  //Ergebnisse von sub aktivities (Messung) weiterreichen 
  protected override def onActivityResult(req: Int, res: Int, data: Intent) =
    if (res == Activity.RESULT_OK && req == 0)
      Option(data.getExtras.getSerializable("result")).foreach { x =>
        val y = x.asInstanceOf[SIValue.Serialized].unserialize
        siresult(y).a()
      }

}