package de.blackpinguin.android.sindwirschonda.views

import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import de.blackpinguin.android.sindwirschonda.si._

//Mediator, um den Zugriff auf das SIValue Objekt zu steuern und zu kapseln  
class SIValueMediator(initValue: Double, findUnit: SIUnitType[_], spinner: Spinner, text: EditText, adapter: ArrayAdapter[CharSequence]) {

  //Instanzvariable
  private var sivalue = SIValue(initValue, findUnit(spinner.getSelectedItem.asInstanceOf[String]))

  //anfangs Wert anzeigen
  text.setText(initValue.toString)

  //Getter
  def value = sivalue

  //Setter
  def value_=(newVal: SIValue): Boolean = {
    if (newVal != SIValue.NaV) {

      //Abk�rzung der Einheit des neuen Wertes
      val abbr = newVal.unit.abbreviation

      //finde die Abk�rzung im Spinner
      for (i <- 0 until adapter.getCount)
        //wenn dies die Abk�rzung ist
        if (abbr.equals(adapter.getItem(i).toString)) {
          //w�hle die Einheit im Spinner aus
          spinner.setSelection(i)
          //�ndere die Instanzvariable
          sivalue = newVal
          //�ndere den angezeigten Wert
          text.setText(sivalue.value.toString)
          return true
        }
    }
    false
  }

  //Wert hat sich ge�ndert, aber nicht die Einheit
  //Wert im EditText hat sich ge�ndert, aktualisiere Wert der Variablen
  def changeValue(input: String) = {
    try {
      //Wandel String zu Double
      val value = input.toDouble //kann Exception werfen, wenn Eingabe keine Zahl
      //wenn der Wert sich ge�ndert hat 
      if (sivalue.value.compare(value) != 0)
        //�ndere die Variable, behalte die Einheit
        sivalue = SIValue(value, sivalue.unit)
    } catch {
      //Fehler beim parsen des String als Zahl
      case _: java.lang.NumberFormatException =>
        //Variable ist keine Zahl, behalte die Einheit
        sivalue = SIValue(Double.NaN, sivalue.unit)
    }
  }

  //Einheit hat sich ge�ndert, aber nicht der Wert - rechne Wert auf neue Einheit um
  //Einheit hat sich im Spinner ge�ndert, aktualisiere Einheit der Variablen
  def changeUnit(abbr: String) = {
    val oldVal = sivalue
    //Finde die Einheit �ber die Abk�rzung
    val unit = findUnit(abbr)
    //wenn sich die Einheit ge�ndert hat
    if (unit != oldVal.unit) {
      //Rechne in die neue Einheit um
      sivalue = oldVal.toUnit(unit)
      //�ndere den angezeigten Wert 
      text.setText(sivalue.value.toString)
    }
  }

}