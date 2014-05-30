package de.blackpinguin.android.sindwirschonda.views

import de.blackpinguin.android.sindwirschonda._
import de.blackpinguin.android.sindwirschonda.si._
import android.widget.{FrameLayout, GridLayout, TextView, EditText, Spinner, ArrayAdapter}
import android.util.AttributeSet
import android.content.Context
import android.view.LayoutInflater
import android.app.Activity

class SIValueOutput(context: Context, attrs: AttributeSet) extends FrameLayout(context, attrs) {

  //Instanzvariable
  var callback: Unit => Unit = null
  
  //Callback ausführen sofern nicht null
  def doCallback: Unit = if (callback != null) callback() 
  
  protected def getLayoutID = R.layout.lay_si_value_output

  //GUI Elemente
  protected lazy val activity = context.asInstanceOf[Activity]
  protected lazy val grid = this.getChildAt(0).asInstanceOf[GridLayout]
  protected lazy val label = grid.getChildAt(0).asInstanceOf[TextView]
  protected lazy val text = grid.getChildAt(2).asInstanceOf[EditText]
  protected lazy val spinner = grid.getChildAt(3).asInstanceOf[Spinner]

  import scala.language.existentials //to allow SIUnitType[_] as a return value
  
  protected val (mediator, unitType) = {

    //Argumente
    val argarr = context.getTheme.obtainStyledAttributes(attrs, R.styleable.SIValue, 0, 0)
    val color = argarr.getColor(R.styleable.SIValue_backgroundColor, 0)
    val unit = argarr.getString(R.styleable.SIValue_unit)
    val initValue = argarr.getFloat(R.styleable.SIValue_value, 1.0f).toDouble
    argarr.recycle

    //Hintergrundfarbe setzen
    setBackgroundColor(color)

    //XML-Layout laden
    LayoutInflater.from(context).inflate(getLayoutID, this)

    //Titel setzen
    label.setText(unit)

    //Welche Einheit?
    val unitType = SIUnitType(unit)

    //Spinner befüllen
    val adapter = ArrayAdapter.createFromResource(context, unitType.arrayID, android.R.layout.simple_spinner_item)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.setAdapter(adapter)
    spinner.setSelection(unitType.defaultIndex)

    //Mediator erstellen
    val m = new SIValueMediator(initValue, unitType, spinner, text, adapter)

    //Mediator zurückgeben
    (m, unitType)
  }

  //bei Spinner auswahl
  spinner onSelect { (pos, obj) =>
    mediator.changeUnit(obj.asInstanceOf[String])
    doCallback
  }

  //beim Ändern des Wertes
  text onChange { str =>
    mediator.changeValue(str)
    doCallback
  }

  def value = mediator.value
  def value_=(newVal: SIValue) = { this := newVal }
  def :=(newVal: SIValue) = { mediator.value = newVal }
  def *(other: SIValue) = value * other
  def /(other: SIValue) = value / other
}