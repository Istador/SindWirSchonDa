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

  //IDs notwendig, um unterscheiden zu können für welche der beiden Eingaben  
  //ein Ergebnis von einer anderen Activity kommt.

  //ID Zähler
  private[this] var i = 0

  //Abbildung von ID auf Objekt
  private[this] var map = Map[Int, SIValueInput]()

  //Eintragen in die Map unter einer neuen ID
  private[SIValueInput] def getID(in: SIValueInput) = synchronized {
    i += 1 //inkrementiere ID-Zähler
    map += i -> in //trage in Map ein
    i //ID zutückgeben
  }

  //finde für eine ID das dazugehörige Objekt
  def apply(id: Int) = map.get(id)
}

class SIValueInput(context: Context, attrs: AttributeSet) extends SIValueOutput(context, attrs) {

  //ID dieses Objektes
  val id = SIValueInput.getID(this)

  override def getLayoutID = R.layout.lay_si_value_input

  //Button "..." um Werte auszuwählen
  val button = grid.getChildAt(1).asInstanceOf[Button]

  //welche Actitity bei einem klick auf "..." geöffnet werden soll
  def selectActivity = unitType match {
    case SITime => classOf[SelectTimeActivity]
    case SIDistance => classOf[SelectDistanceActivity]
    case SISpeed => classOf[SelectSpeedActivity]
  }

  button onClick { _ =>
    activity.startActivityForResult(new Intent(context, selectActivity), id)
  }

  //Ergebnis der geöffneten Subactivity
  def onResult(data: Intent) = {
    //nur wenn nicht null
    Option(data.getExtras.getSerializable("result")).foreach { x =>
      val y = x.asInstanceOf[SIValue.Serialized].unserialize
      mediator.value = y
    }
  }

}