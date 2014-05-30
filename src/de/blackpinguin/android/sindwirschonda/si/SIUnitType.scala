package de.blackpinguin.android.sindwirschonda.si

import android.content.Context

object SIUnitType {
    
  var cache = Map[String, SIUnitType[_]]()
  def apply(name: String) = cache.getOrElse(name, null)
  def +=(ut: SIUnitType[_]) = cache += ut.name -> ut
  
}

//Einheitentyp / physikalische Größe
trait SIUnitType[T] {
  
  SIUnitType += this
  
  //ID des Namens
  def nameID: Int
  
  //ID des Arrays mit Einheiten
  def arrayID: Int
  
  //yay, reflection!
  lazy val clazz = Class.forName(((str:String) => str.substring(0, str.length - 1))(getClass.getName))
  
  //Int konstruktor, der auf den String konstruktor zurückgreift
  def apply(index: Int, multiplier: Double):T = {
    val abbr = array(index)
    
    //yay, reflection!
    clazz.getConstructor(classOf[String], classOf[Double])
      .newInstance(Array[Object](abbr, multiplier:java.lang.Double):_*)
      .asInstanceOf[T]
  }
  
  //alle Einheiten dieses Einheitentypes
  private var cache = Map[String, SIUnit]()
  //finde eine Einheit dieses Types über die Abkürzung
  def apply(abbr: String) = cache.getOrElse(abbr, SIUnit.NaU)
  //neue Einheit hinzufügen
  def +=(unit: SIUnit) = cache += unit.abbreviation -> unit
  
  private def res = Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null).asInstanceOf[Context].getResources
  lazy val name = res.getString(nameID)
  lazy val array = res.getStringArray(arrayID)
  lazy val defaultIndex = array.indexWhere{ this(_).baseUnitMultiplier == 1.0 }
  lazy val baseUnit = this(array(defaultIndex))
  
  override def toString = name
  
}