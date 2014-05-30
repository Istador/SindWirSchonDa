package de.blackpinguin.android.sindwirschonda.views

import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si._
import android.widget.{EditText, Spinner, ArrayAdapter}

class SIValueMediator(initValue: Double, unitType: Int, spinner: Spinner, text: EditText, adapter: ArrayAdapter[CharSequence]) {

  //Finde über die Abkürzung das passende SIUnit Objekt
  private def findUnit(abbr: String) = unitType match {
    case R.array.time => SITime(abbr)
    case R.array.distance => SIDistance(abbr)
    case R.array.speed => SISpeed(abbr)
  }

  //Instanzvariable
  private var sivalue = SIValue(initValue, findUnit(spinner.getSelectedItem.asInstanceOf[String]))

  //anfangs Wert anzeigen
  text.setText(initValue.toString)

  //Getter
  def value = sivalue

  //Setter
  def value_=(newVal: SIValue): Boolean = {
    if (newVal != SIValue.NaV) {
      //Abkürzung der Einheit
      val abbr = newVal.unit.abbreviation
      //finde die Abkürzung
      for (i <- 0 until adapter.getCount)
        if (abbr.equals(adapter.getItem(i).asInstanceOf[CharSequence])) {
          //wähle die Einheit aus
          spinner.setSelection(i)
          //ändere die Variable
          sivalue = newVal
          //ändere den angezeigten Wert
          text.setText(sivalue.value.toString)
          return true
        }
    }
    false
  }

  //Wert im EditText hat sich geändert, aktualisiere Wert der Variablen
  def changeValue(input: String) = {
    try {
      //Wandel String zu Double
      val value = input.toDouble //kann Exception werfen, wenn Eingabe keine Zahl
      //wenn der Wert sich geändert hat 
      if (sivalue.value.compare(value) != 0)
        //ändere die Variable, behalte die Einheit
        sivalue = SIValue(value, sivalue.unit)
    } catch {
      //Fehler beim parsen des String als Zahl
      case _: java.lang.NumberFormatException =>
        //Variable ist keine Zahl, behalte die Einheit
        sivalue = SIValue(Double.NaN, sivalue.unit)
    }
  }

  //Einheit hat sich im Spinner geändert, aktualisiere Einheit der Variablen
  def changeUnit(abbr: String) = {
    val oldVal = sivalue
    //Finde die Einheit über die Abkürzung
    val unit = findUnit(abbr)
    //wenn sich die Einheit geändert hat
    if (unit != oldVal.unit) {
      //Rechne in die neue Einheit um
      sivalue = oldVal.toUnit(unit)
      //Ändere den angezeigten Wert 
      text.setText(sivalue.value.toString)
    }
  }

}