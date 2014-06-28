package de.blackpinguin.android.sindwirschonda.views

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.Button
import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.activities._
import de.blackpinguin.android.sindwirschonda.si._

object SIValueInput {

  //IDs notwendig, um unterscheiden zu k�nnen f�r welche der beiden Eingaben  
  //ein Ergebnis von einer anderen Activity kommt.

  //ID Z�hler
  private[this] var i = 0

  //Abbildung von ID auf Objekt
  private[this] var map = Map[Int, SIValueInput]()

  //Eintragen in die Map unter einer neuen ID
  private[SIValueInput] def getID(in: SIValueInput) = synchronized {
    i += 1 //inkrementiere ID-Z�hler
    map += i -> in //trage in Map ein
    i //ID zut�ckgeben
  }

  //finde f�r eine ID das dazugeh�rige Objekt
  def apply(id: Int) = map.get(id)
}

class SIValueInput(context: Context, attrs: AttributeSet) extends SIValueOutput(context, attrs) {

  //ID dieses Objektes
  val id = SIValueInput.getID(this)

  override def getLayoutID = R.layout.lay_si_value_input

  //Button "..." um Werte auszuw�hlen
  val button = grid.getChildAt(1).asInstanceOf[Button]

  //welche Actitity bei einem klick auf "..." ge�ffnet werden soll
  def selectActivity = unitType match {
    case SITime => classOf[SelectTimeActivity]
    case SIDistance => classOf[SelectDistanceActivity]
    case SISpeed => classOf[SelectSpeedActivity]
  }

  button onClick { _ =>
    activity.startActivityForResult(new Intent(context, selectActivity), id)
  }

  //Ergebnis der ge�ffneten Subactivity
  def onResult(data: Intent) = {
    //nur wenn nicht null
    Option(data.getExtras.getSerializable("result")).foreach { x =>
      val y = x.asInstanceOf[SIValue.Serialized].unserialize
      mediator.value = y
    }
  }

}