package de.blackpinguin.android.sindwirschonda.activities

import android.widget.Button
import de.blackpinguin.android.sindwirschonda._

abstract class ButtonsActivity extends SimpleActivity {
  
  type E = Either[Unit=>Unit, Class[_]]
  
  //Methoden die bei einem click auf die Buttons ausgeführt werden sollen
  protected def getCallbacks: IndexedSeq[E]
  
  //Alle Buttons im Layout
  lazy val buttons: IndexedSeq[Button] = 
    for { 
      i <- 0 until content.getChildCount
      child = content.getChildAt(i)
      if(child.isInstanceOf[Button])
    } yield child.asInstanceOf[Button]
  
  //Button wurde gedrückt, führe Aktion aus
  private def execute(e: E) = e match {
    case Left(callback) => callback() //callback aufrufen
    case Right(aClass) => openActivity(aClass) //activity starten
  }
  
  //"Konstruktor"
  override def onCreate(state: android.os.Bundle) = {
    super.onCreate(state)
    //jedem Button einen onClick Callback zuweisen
    buttons.zipWithIndex.foreach{ case(button, index) =>
      button onClick { _ => execute(getCallbacks(index))}
    }
  }
  
}