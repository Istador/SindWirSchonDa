package de.blackpinguin.android.sindwirschonda.views

import de.blackpinguin.android.sindwirschonda._
import android.util.AttributeSet
import android.content.Context
import android.widget.Button
import android.app.Activity
import android.content.Intent
import android.widget.Toast

object SIValueInput {
  private[this] var i = 0
  private[this] var map = Map[Int, SIValueInput]()
  private[SIValueInput] def getID(in: SIValueInput) = {
    i += 1
    map += i -> in
    i
  }
  
  def apply(id: Int) = map(i)
}

class SIValueInput (context: Context, attrs: AttributeSet) extends SIValueOutput(context, attrs) {
  
  val id = SIValueInput.getID(this)
  
  override def getLayoutID = R.layout.lay_si_value_input
  
  val button = grid.getChildAt(1).asInstanceOf[Button]
  
  import de.blackpinguin.android.sindwirschonda.activities.MainActivity
  def selectActivity = unitType match {
    //TODO Klassen
    case R.array.time => classOf[MainActivity]
    case R.array.distance => classOf[MainActivity]
    case R.array.speed => classOf[MainActivity]
  }
  
  button onClick { _ =>
    Toast.makeText(context, "test", Toast.LENGTH_SHORT).show
    //activity.startActivityForResult(new Intent(context, selectActivity), id)
  }
  
}