package de.blackpinguin.android

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.ToggleButton

package object sindwirschonda {

  //erweitere alle Views implizit
  implicit class ExtendedView(v: View) {
    def enable = v setEnabled true
    def disable = v setEnabled false
  }

  //erweitere alle Buttons implizit
  implicit class ExtendedButton(b: Button) {
    //Lambda Expression statt Listener als Parameter 
    def onClick(f: Unit => Unit) =
      b setOnClickListener new OnClickListener {
        override def onClick(v: View) = f()
      }
  }

  //erweitere alle ToggleButtons implizit
  implicit class ExtendedToggleButton(tb: ToggleButton) {
    //Lambda Expression statt Listener als Parameter
    def onChange(f: Boolean => Unit) =
      tb setOnCheckedChangeListener new OnCheckedChangeListener {
        override def onCheckedChanged(b: CompoundButton, down: Boolean) =
          if (b == tb) f(down)
      }
  }

  //erweitere alle EditTexts implizit
  implicit class ExtendedEditText(et: EditText) {
    //Lambda Expression statt Listener als Parameter
    def onChange(f: String => Unit) =
      et addTextChangedListener new TextWatcher {
        override def afterTextChanged(s: Editable) =
          f(et.getText.toString)
        override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = {}
        override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = {}
      }
  }

  //erweitere alle SeekBars implizit
  implicit class ExtendedSeekBar(sb: SeekBar) {
    //Lambda Expression statt Listener als Parameter
    def onChange(f: Int => Unit) =
      sb setOnSeekBarChangeListener new SeekBar.OnSeekBarChangeListener {
        override def onProgressChanged(s: SeekBar, prog: Int, fromUser: Boolean) =
          if (fromUser && s == sb) f(prog)
        override def onStartTrackingTouch(s: SeekBar) = {}
        override def onStopTrackingTouch(s: SeekBar) = {}
      }
  }

  //erweitere alle Spinner implizit
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