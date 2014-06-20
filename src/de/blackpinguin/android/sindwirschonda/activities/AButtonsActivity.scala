package de.blackpinguin.android.sindwirschonda.activities

import scala.Array.canBuildFrom

import android.view.ViewGroup.LayoutParams
import android.widget.Button
import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.R

abstract class AButtonsActivity extends ASimpleActivity {

  type E = Either[Function0[Any], Class[_]]

  private var n = 0

  //Namen der Buttons
  def getNames = names
  private var names = Array[String]()

  //Methoden die bei einem click auf die Buttons ausgeführt werden sollen
  def getCallbacks = callbacks
  private var callbacks = Array[E]()

  //hinzufügen von Buttons für die Subklassen
  protected def +=(kv: (Any, E)): Unit = {
    if (kv._1.isInstanceOf[String])
      names :+= kv._1.asInstanceOf[String]
    else if (kv._1.isInstanceOf[Int])
      names :+= getResources.getString(kv._1.asInstanceOf[Int])
    else return
    callbacks :+= kv._2
    n += 1
  }

  //Alle Buttons im Layout
  lazy val buttons: IndexedSeq[Button] =
    for {
      i <- 0 until content.getChildCount
      child = content.getChildAt(i)
      if (child.isInstanceOf[Button])
    } yield child.asInstanceOf[Button]

  //Button wurde gedrückt, führe Aktion aus
  private def execute(e: E) = e match {
    case Left(callback) => callback() //callback aufrufen
    case Right(aClass) => openActivity(aClass) //activity starten
  }

  //"Konstruktor"
  protected override def onCreate(state: android.os.Bundle) = {
    super.onCreate(state)

    //Buttons als Views hinzufügen
    getNames.foreach { name =>
      val b = new Button(this)
      b.setText(name)
      val lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
      content.addView(b, lp)
    }

    //jedem Button einen onClick Callback zuweisen
    buttons.zipWithIndex.foreach {
      case (button, index) =>
        button onClick { _ => execute(getCallbacks(index)) }
    }
  }

  protected def getLayoutID = R.layout.activity_buttons
  protected def getContentID = R.id.aButtons

}