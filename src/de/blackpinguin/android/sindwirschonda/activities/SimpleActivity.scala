package de.blackpinguin.android.sindwirschonda.activities

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.{Menu, MenuItem, ViewGroup}
import android.content.res.Configuration
import java.util.Locale
import de.blackpinguin.android.sindwirschonda.si.SIValue
import de.blackpinguin.android.sindwirschonda.R
import android.widget.Toast

abstract class SimpleActivity extends Activity {
  
  //R.id vom layout
  protected def getLayoutID: Int
  
  //R.id vom Content XML Element
  protected def getContentID: Int
  
  //R.id vom Men�
  protected def getMenuID: Int = R.menu.nomenu
  
  //Ob das App Icon oben links als zur�ck button angezeigt werden soll
  protected def viewHomeAsBack = true
  
  //Layout Objekt
  lazy val content = findViewById(getContentID).asInstanceOf[ViewGroup]
  
  //"Konstruktor"
  override def onCreate(state: Bundle) = {
    super.onCreate(state)
    setContentView(getLayoutID)
  }

  //"Konstruktor" vom Men� oben
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(getMenuID, menu)
    
    //neben dem Icon oben Links einen Pfeil nach links f�r "zur�ck"
    getActionBar.setDisplayHomeAsUpEnabled(viewHomeAsBack)
    
    return true
  }
  
  //bei einem Klick auf das Men� oben
  override def onOptionsItemSelected(item: MenuItem): Boolean =
    item.getItemId match {
      //"zur�ck" links neben dem AppIcon
      case android.R.id.home =>
        //diese Activity schlie�en
        finish()
        return true
      case _ =>
        return super.onOptionsItemSelected(item)
    }
  
  //diese Activity neu starten
  def restartActivity = {
    val i = getIntent
    finish
    startActivity(i)
  }
  
  //�ffnen einer anderen Activity
  def openActivity(c: Class[_]) =
    //startActivity(new Intent(this, c))
    startActivityForResult(new Intent(this, c), 0)
 
  //einer aufrufenden Activity ein Ergebnis zur�ckliefern
  def result(s: =>Serializable) = Left{() =>
    val i = new Intent()
    i.putExtra("result", s)
    setResult(Activity.RESULT_OK, i)
    finish
  }
  
  def siresult(s: =>SIValue) = result(s.serialize)
  
  def getStr(id: Int):String = getResources.getString(id)
  
  def alert(str: String) = 
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show
  
}