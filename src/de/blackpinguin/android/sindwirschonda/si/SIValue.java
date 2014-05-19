package de.blackpinguin.android.sindwirschonda.si;

public class SIValue {
	
	
	
	/*
	 * Subklasse
	 */
	public static class NotAValue extends SIValue{
		private NotAValue(){
			super(Double.NaN, SIUnit.NaU);
		}
	}
	
	public static final NotAValue NaV = new NotAValue();
	
	
	
	/*
	 * Instanzvariablen
	 */
	private final double value;
	private final SIUnit unit;
	
	public double getValue(){return value;}
	public SIUnit getUnit(){return unit;}
	
	
	/*
	 * Konstruktor
	 */
	public SIValue(double value, SIUnit unit){
		this.value = value;
		this.unit = unit;
	}
	
	
	
	/*
	 * Konvertierung
	 */
	private SIValue _asBaseUnit = null;
	public SIValue toBaseUnit(){
		if(_asBaseUnit == null)
			if(unit == unit.getBaseUnit())
				_asBaseUnit = this;
			else
				_asBaseUnit = new SIValue(unit.toBaseUnit(value).getValue(), unit.getBaseUnit()); 
		return _asBaseUnit;
	}
	
	public SIValue toUnit(SIUnit newUnit){
		if(this.getUnit().getBaseUnit() != newUnit.getBaseUnit())
			return NaV;
		return newUnit.fromBaseUnit(this.toBaseUnit().getValue());
	}
	
	
	
	/*
	 * Operatoren
	 */
	public SIValue add(SIValue other){
		if(this.getUnit().getBaseUnit() != other.getUnit().getBaseUnit())
			return NaV;
		
		double result = this.toBaseUnit().getValue() + other.toBaseUnit().getValue();
		
		return this.getUnit().fromBaseUnit(result);
	}
	
	public SIValue multiply(SIValue other){
		SIUnit bunit = this.getUnit().multiply(other.getUnit());
		if(bunit == SIUnit.NaU)
			return NaV;
		return new SIValue(this.toBaseUnit().getValue() * other.toBaseUnit().getValue(), bunit);
	}
	
	public SIValue divide(SIValue other){
		SIUnit bunit = this.getUnit().divide(other.getUnit());
		if(bunit == SIUnit.NaU)
			return NaV;
		return new SIValue(this.toBaseUnit().getValue() / other.toBaseUnit().getValue(), bunit);
	}
	
	
	
	
	
}
