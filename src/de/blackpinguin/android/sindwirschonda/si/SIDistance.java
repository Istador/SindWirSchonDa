package de.blackpinguin.android.sindwirschonda.si;

import java.util.HashMap;

public class SIDistance extends SIUnit {
	
	private static HashMap<String, SIUnit> cache = new HashMap<String, SIUnit>();
	
	public static SIUnit find(String abbreviation){
		SIUnit r = cache.get(abbreviation);
		
		if(r == null)
			return NaU;
		else return r;
	}
	
	private SIDistance(String name, String abbreviation, double baseUnitMultiplier){
		super(name, abbreviation, baseUnitMultiplier);
		cache.put(abbreviation, this);
	}
	
	public static final SIDistance ft = new SIDistance("feet", "ft", 0.3048);
	public static final SIDistance m = new SIDistance("meter", "m", 1.0);
	public static final SIDistance km = new SIDistance("kilometer", "km", 1000.0);
	public static final SIDistance mi = new SIDistance("mile", "mi", 1609.344);
	public static final SIDistance au = new SIDistance("astronomical unit", "AU", 149597870700.0);
	public static final SIDistance lj = new SIDistance("light year", "ly", 9460730472580800.0);
	public static final SIDistance pc = new SIDistance("parsec", "pc", au.getBaseUnitMultiplier() * (648000.0 / Math.PI ));
	
	protected SIUnit getBaseUnit(){
		return m;
	}
	
	public SIUnit multiply(SIUnit other){
		return NaU;
	}
	
	public SIUnit divide(SIUnit other){
		if(other instanceof SISpeed)
			return SITime.s;
		if(other instanceof SITime)
			return SISpeed.m_per_s;
		return NaU;
	}
	
}
