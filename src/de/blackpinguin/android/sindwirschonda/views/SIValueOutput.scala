package de.blackpinguin.android.sindwirschonda.views

import de.blackpinguin.android.sindwirschonda.R
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Spinner
import de.blackpinguin.android.sindwirschonda.si._
import android.util.AttributeSet
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import de.blackpinguin.android.sindwirschonda._

class SIValueOutput(context: Context, attrs: AttributeSet) extends FrameLayout(context, attrs) {
  
  var callback: Unit=>Unit = null 
  
  protected def getLayoutID = R.layout.lay_si_value_output
  
  
  //GUI Elemente
  protected lazy val grid = this.getChildAt(0).asInstanceOf[GridLayout]
  protected lazy val label = grid.getChildAt(0).asInstanceOf[TextView]
  protected lazy val text = grid.getChildAt(2).asInstanceOf[EditText]
  protected lazy val spinner = grid.getChildAt(3).asInstanceOf[Spinner]
  
  //Argumente
  protected val argarr = context.getTheme.obtainStyledAttributes(attrs, R.styleable.SIValue, 0, 0)
  private val _value = argarr.getFloat(R.styleable.SIValue_value, 1.0f)
  
  //Hintergrundfarbe setzen
  setBackgroundColor(argarr.getColor(R.styleable.SIValue_backgroundColor, 0))
  
  //XML-Layout laden
  LayoutInflater.from(context).inflate(getLayoutID, this)
  
  //Titel setzen
  label.setText(argarr.getString(R.styleable.SIValue_label))
  
  //anfangs Wert setzen
  text.setText(_value.toString)
  println("init: " + _value.toString)
  
  //Welche Einheit?
  protected val unitType = argarr.getInt(R.styleable.SIValue_unit, 1) match {
    //defined in attrs.xml
    case 1 => R.array.time
    case 2 => R.array.distance
    case 3 => R.array.speed
  }
  
  //Spinner befüllen
  private val adapter = ArrayAdapter.createFromResource(context, unitType, android.R.layout.simple_spinner_item)
  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
  spinner.setAdapter(adapter)
  spinner.setSelection(argarr.getInt(R.styleable.SIValue_unitIndex, 0))
  
  //SI Value erstellen
  var sivalue = SIValue(_value, findUnit(spinner.getSelectedItem.asInstanceOf[String]))
  println("sival: " + sivalue.toString)
  
  //bei Spinner auswahl
  spinner onSelect { (pos, obj) => 
    changeUnit(obj.asInstanceOf[String])
    if(callback != null) callback()
  }
  
  text onChange { str =>
    changeValue(str)
    if(callback != null) callback()
  }
  
  
  
  
  private def findUnit(abbr: String) = unitType match {
    case R.array.time => SITime(abbr)
    case R.array.distance => SIDistance(abbr)
    case R.array.speed => SISpeed(abbr)
  }
  
  def value: SIValue = sivalue
  
  def :=(newVal: SIValue): Boolean = value_=(newVal)
  def *(other: SIValue) = value * other
  def /(other: SIValue) = value / other
  
  def value_=(newVal: SIValue): Boolean = {
    println("set: " + newVal.toString)
    if(newVal != SIValue.NaV){
		val abbr = newVal.unit.abbreviation
		
		for(i <- 0 until adapter.getCount)
			if(abbr.equals(adapter.getItem(i).asInstanceOf[CharSequence])){
			  spinner.setSelection(i)
			  sivalue = newVal
			  text.setText(sivalue.value.toString)
			  return true
		}
	}
	false
  } 
  
  def changeValue(_val: String) = {
    println("change: " + _val + ", "+ sivalue.value)
    try{
      val tmp = _val.toDouble
      if(sivalue.value == Double.NaN || sivalue.value.compare(tmp) != 0)
        sivalue = SIValue(tmp, sivalue.unit)
    } catch {
      case _: java.lang.NumberFormatException =>
        sivalue = SIValue(Double.NaN, sivalue.unit)
    }
  }
  
  def changeUnit(abbr: String) = {
    val oldVal = sivalue
    val unit = findUnit(abbr)
    sivalue = oldVal.toUnit(unit)
    text.setText(sivalue.value.toString)
  }
  
  argarr.recycle
}