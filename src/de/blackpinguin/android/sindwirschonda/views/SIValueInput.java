package de.blackpinguin.android.sindwirschonda.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import de.blackpinguin.android.sdt.R;

public class SIValueInput extends SIValueOutput {

	Button button;
	
	public SIValueInput(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SIValueInput(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override protected int getLayout(){
		return R.layout.lay_si_value_input;
	}
	
	@Override protected void initView(Context context, AttributeSet attrs){
		super.initView(context, attrs);
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SIValue, 0, 0);
		try {
			button = (Button) grid.getChildAt(1);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			a.recycle();
		}
	}

}
