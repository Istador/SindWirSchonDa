package de.blackpinguin.android

import android.widget.{Button, ToggleButton, EditText, Spinner, CompoundButton, AdapterView}
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.AdapterView.OnItemSelectedListener
import android.view.View
import android.view.View.OnClickListener
import android.text.{TextWatcher, Editable}


package object sindwirschonda {
  
  //erweitere alle Views implizit
  implicit class ExtendedView(v: View) {
    def enable = v setEnabled true
    def disable = v setEnabled false
  }

  //erweitere alle Buttons implizit
  implicit class ExtendedButton(b: Button) {
    //Lambda Expression statt Listener als Parameter 
    def onClick(f: View => Unit) =
      b setOnClickListener new OnClickListener {
        override def onClick(v: View) = f(v)
      }
  }

  //erweitere alle ToggleButtons implizit
  implicit class ExtendedToggleButton(b: ToggleButton) {
    //Lambda Expression statt Listener als Parameter
    def onChange(f: (CompoundButton, Boolean) => Unit) =
      b setOnCheckedChangeListener new OnCheckedChangeListener {
        override def onCheckedChanged(b: CompoundButton, down: Boolean) = f(b, down)
      }
  }
  
  //erweitere alle EditTexts implizit
  implicit class ExtendedEditText(et: EditText) {
    //Lambda Expression statt Listener als Parameter
    def onChange(f: String => Unit) =
      et addTextChangedListener new TextWatcher {
        override def afterTextChanged(s: Editable) = f(et.getText.toString)
        override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = {}
        override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = {}
      }
  }
  
  //erweitere alle ToggleButtons implizit
  implicit class ExtendedSpinner(s: Spinner) {
    //Lambda Expression statt Listener als Parameter
    def onSelect(f: (Int, Object) => Unit) =
      s setOnItemSelectedListener new OnItemSelectedListener {
        override def onItemSelected(p: AdapterView[_], v: View, pos: Int, id: Long) = 
          f(pos, s.getItemAtPosition(pos))
        override def onNothingSelected(p: AdapterView[_]) = {}
      }
  }
  
}