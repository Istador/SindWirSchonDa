package de.blackpinguin.android.sindwirschonda.si

import android.content.Context

//�ber dieses Objekt lassen sich Einheitentypen finden (z.B. Geschwindigkeit, Zeit, Entfernung)
object SIUnitType {
  private[this] var cache = Map[String, SIUnitType[_]]()
  protected[SIUnitType] def +=(ut: SIUnitType[_]) = cache += ut.name -> ut
  def apply(name: String) = cache.getOrElse(name, null)

}

//Trait
//f�r alle Einheitentypen, f�gt sie automatisch dem Cache des Begleitobjektes hinzu
trait SIUnitType[+T] {

  //f�ge konkrete Subklassen von SIUnitType in den Cache des Objektes 
  SIUnitType += this

  //(abstrakt) ID des Namens
  def nameID: Int

  //(abstrakt) ID des Arrays mit Einheiten
  def arrayID: Int

  //yay, reflection!
  //hier notwendig, um auf die Klasse zum Begleitobjekt zugreifen zu k�nnen 
  lazy val clazz = Class.forName(((str: String) => str.substring(0, str.length - 1))(getClass.getName))

  //Int konstruktor, der auf den String konstruktor zur�ckgreift
  //�ber den Index wird der String im Array ausgew�hlt
  def apply(index: Int, multiplier: Double): T = {
    val abbr = array(index)

    //yay, reflection!
    clazz.getConstructor(classOf[String], classOf[Double])
      .newInstance(Array[Object](abbr, multiplier: java.lang.Double): _*)
      .asInstanceOf[T]
  }

  //alle Einheiten dieses Einheitentypes
  private var cache = Map[String, SIUnit]()

  //finde eine Einheit dieses Types �ber die Abk�rzung
  def apply(abbr: String) = cache.getOrElse(abbr, SIUnit.NaU)

  //neue Einheit hinzuf�gen (nur f�r das package si aufrufbar)
  private[si] def +=(unit: SIUnit) = cache += unit.abbreviation -> unit

  private def res = Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null).asInstanceOf[Context].getResources
  lazy val name = res.getString(nameID)
  lazy val array = res.getStringArray(arrayID)
  lazy val defaultIndex = array.indexWhere { this(_).baseUnitMultiplier == 1.0 }
  lazy val baseUnit = this(array(defaultIndex))

  override def toString = name

}