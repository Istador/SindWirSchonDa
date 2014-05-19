package de.blackpinguin.android.sindwirschonda.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import de.blackpinguin.android.sdt.R;
import de.blackpinguin.android.sdt.si.SIDistance;
import de.blackpinguin.android.sdt.si.SISpeed;
import de.blackpinguin.android.sdt.si.SITime;
import de.blackpinguin.android.sdt.si.SIUnit;
import de.blackpinguin.android.sdt.si.SIValue;

public class SIValueOutput extends FrameLayout {
	
	protected String title;
	protected SIValue value;
	protected int color;
	
	protected GridLayout grid;
	protected TextView label;
	protected EditText text;
	protected Spinner spinner;
	
	public Runnable callback;
	
	int unitType;
	
	public SIValueOutput(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}
	
	public SIValueOutput(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}
	
	protected int getLayout(){
		return R.layout.lay_si_value_output;
	}
	
	public SIUnit findUnit(String abbr){
		switch(unitType){
			//defined in attrs.xml
			default:
			case R.array.time:
				return SITime.find(abbr);
			case R.array.distance:
				return SIDistance.find(abbr);
			case R.array.speed:
				return SISpeed.find(abbr);
		}
	}
	
	public void changeUnit(String abbr){
		SIValue oldVal = value;
		SIUnit unit = findUnit(abbr);
		value = oldVal.toUnit(unit);
		text.setText(Double.toString(value.getValue()));
	}
	
	private void changeValue(String _value){
		double val;
		try{
			val = Double.valueOf(_value);
		} catch (java.lang.NumberFormatException e){
			val = Double.NaN;
		}
		
		if(Double.compare(value.getValue(), val) != 0)
			value = new SIValue(val, value.getUnit());
	}
	
	public SIValue getValue(){
		return value;
	}
	
	public boolean setValue(SIValue newVal){
		if(newVal != SIValue.NaV){
			String abbr = newVal.getUnit().getAbbr();
			int i = 0;
			int n = spinner.getAdapter().getCount();
			for(i=0; i<n; i++)
				if(abbr.equals((CharSequence)spinner.getAdapter().getItem(i)))
					break;
			if(i < n){
				spinner.setSelection(i);
				value = newVal;
				text.setText(Double.toString(value.getValue()));
				return true;
			}
		}
		return false;
	}
	
	protected void initView(Context context, AttributeSet attrs){
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SIValue, 0, 0);
		try {
			//Attribute
			title = a.getString(R.styleable.SIValue_label);
			float _value = a.getFloat(R.styleable.SIValue_value, 1.0f);
			color = a.getColor(R.styleable.SIValue_backgroundColor, 0);
			unitType = a.getInt(R.styleable.SIValue_unit, 1);
			int unitIndex = a.getInt(R.styleable.SIValue_unitIndex, 0);
			
			
			//Hintergrundfarbe
			setBackgroundColor(color);
			
			//XML-Layout laden
			LayoutInflater.from(context).inflate(getLayout(), this, true);
			
			//Elemente aus Layout laden
			grid = (GridLayout)this.getChildAt(0);
			label = (TextView) grid.getChildAt(0);
			text = (EditText) grid.getChildAt(2);
			spinner = (Spinner) grid.getChildAt(3);
			
			//Elemente in Layout setzen
			label.setText(title);
			text.setText(Float.toString(_value));
			
			//Welche Einheit?
			switch(unitType){
				//defined in attrs.xml
				default:
				case 1: unitType = R.array.time; break;
				case 2: unitType = R.array.distance; break;
				case 3: unitType = R.array.speed; break;
			}
			
			//Spinner befüllen
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, unitType, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setSelection(unitIndex);
			
			//SI Value erstellen
			value = new SIValue(_value, findUnit((String)spinner.getSelectedItem()));
			
			//Spinner Listener
			spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					String abbr = (String) spinner.getItemAtPosition(position);
					changeUnit(abbr);
					if(callback != null)
						callback.run();
				}
				@Override public void onNothingSelected(AdapterView<?> parent) {}
			});
			
			//EditText Listener
			text.addTextChangedListener(new TextWatcher(){
				@Override public void afterTextChanged(Editable s) {
					changeValue(text.getText().toString());
					if(callback != null)
						callback.run();
				}
				@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			a.recycle();
		}
	}

}
