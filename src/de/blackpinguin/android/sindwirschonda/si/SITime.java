package de.blackpinguin.android.sindwirschonda.si;

import java.util.HashMap;

public class SITime extends SIUnit {
	
	private static HashMap<String, SIUnit> cache = new HashMap<String, SIUnit>();
	
	public static SIUnit find(String abbreviation){
		SIUnit r = cache.get(abbreviation);
		
		if(r == null)
			return NaU;
		else return r;
	}
	
	private SITime(String name, String abbreviation, double baseUnitMultiplier){
		super(name, abbreviation, baseUnitMultiplier);
		cache.put(abbreviation, this);
	}
	
	public static final SITime s = new SITime("second", "s", 1.0);
	public static final SITime m = new SITime("minute", "m", 60.0);
	public static final SITime h = new SITime("hour", "h", m.getBaseUnitMultiplier() * 60.0);
	public static final SITime d = new SITime("day", "d", h.getBaseUnitMultiplier() * 24.0);
	public static final SITime a = new SITime("annus", "a", d.getBaseUnitMultiplier() * 365.25);
	
	protected SIUnit getBaseUnit(){
		return s;
	}
	
	public SIUnit multiply(SIUnit other){
		if(other instanceof SISpeed)
			return SIDistance.m;
		return NaU;
	}
	
	public SIUnit divide(SIUnit other){
		return NaU;
	}
}
