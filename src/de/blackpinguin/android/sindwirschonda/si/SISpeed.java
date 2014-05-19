package de.blackpinguin.android.sindwirschonda.si;

import java.util.HashMap;

public class SISpeed extends SIUnit {
	
	private static HashMap<String, SIUnit> cache = new HashMap<String, SIUnit>();
	
	public static SIUnit find(String abbreviation){
		SIUnit r = cache.get(abbreviation);
		
		if(r == null)
			return NaU;
		else return r;
	}
	
	private SISpeed(String name, String abbreviation, double baseUnitMultiplier){
		super(name, abbreviation, baseUnitMultiplier);
		cache.put(abbreviation, this);
	}
	
	public static final SISpeed km_per_h = new SISpeed("kilometers / hour", "km/h", 1000.0 / (60.0*60.0));
	public static final SISpeed kn = new SISpeed("Knot", "kn", 463.0 / 900.0);
	public static final SISpeed m_per_s = new SISpeed("meters / second", "m/s", 1.0);
	public static final SISpeed km_per_s = new SISpeed("kilometers / second", "km/s", 1000.0);
	
	protected SIUnit getBaseUnit(){
		return m_per_s;
	}
	
	public SIUnit multiply(SIUnit other){
		if(other instanceof SITime)
			return SIDistance.m;
		return NaU;
	}
	
	public SIUnit divide(SIUnit other){
		return NaU;
	}
}
