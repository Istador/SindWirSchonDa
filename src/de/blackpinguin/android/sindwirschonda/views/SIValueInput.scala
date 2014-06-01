package de.blackpinguin.android.sindwirschonda.views

import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.si._
import de.blackpinguin.android.sindwirschonda.activities._
import android.util.AttributeSet
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.app.Activity


object SIValueInput {
  private[this] var i = 0
  private[this] var map = Map[Int, SIValueInput]()
  private[SIValueInput] def getID(in: SIValueInput) = synchronized {
    i += 1
    map += i -> in
    i
  }
  
  def apply(id: Int) = map.get(id)
}

class SIValueInput (context: Context, attrs: AttributeSet) extends SIValueOutput(context, attrs) {
  
  val id = SIValueInput.getID(this)
  
  override def getLayoutID = R.layout.lay_si_value_input
  
  val button = grid.getChildAt(1).asInstanceOf[Button]
  
  import de.blackpinguin.android.sindwirschonda.activities.MainActivity
  def selectActivity = unitType match {
    //TODO Klassen
    case SITime => classOf[SelectTime]
    case SIDistance => classOf[SelectDistance]
    case SISpeed => classOf[SelectSpeed]
  }
  
  button onClick { _ =>
    activity.startActivityForResult(new Intent(context, selectActivity), id)
  }
  
  def onResult(data: Intent) = {
    Option(data.getExtras.getSerializable("result")).foreach{ x => 
      val y = x.asInstanceOf[SIValue.Serialized].unserialize
      mediator.value = y
    }
  }
  
}