package de.blackpinguin.android.sindwirschonda.si;

public abstract class SIUnit {
	
	private final String name;
	private final String abbreviation;
	private final double baseUnitMultiplier;
	
	protected SIUnit(String name, String abbreviation, double baseUnitMultiplier){
		this.name = name;
		this.abbreviation = abbreviation;
		this.baseUnitMultiplier = baseUnitMultiplier;
	}
	
	public SIValue fromBaseUnit(double value){
		return new SIValue(value / baseUnitMultiplier, this);
	}
	
	public SIValue toBaseUnit(double value){
		return new SIValue(value * baseUnitMultiplier, getBaseUnit());
	}
	
	@Override public String toString(){
		return getAbbr();
	}
	
	public String getName(){return name;}
	public String getAbbr(){return abbreviation;}
	public double getBaseUnitMultiplier(){return baseUnitMultiplier;}
	
	protected abstract SIUnit getBaseUnit();
	
	public static final NotAUnit NaU = new NotAUnit();
	
	public static class NotAUnit extends SIUnit {
		private NotAUnit(){
			super("Not a Unit", "NaU", Double.NaN);
		}
		
		public SIUnit getBaseUnit(){return this;}
		
		public SIUnit multiply(SIUnit other){return this;}
		public SIUnit divide(SIUnit other){return this;}
	}
	
	public abstract SIUnit multiply(SIUnit other);
	public abstract SIUnit divide(SIUnit other);
}
