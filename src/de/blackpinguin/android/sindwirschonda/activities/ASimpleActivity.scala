package de.blackpinguin.android.sindwirschonda.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import de.blackpinguin.android.sindwirschonda.R
import de.blackpinguin.android.sindwirschonda.si.SIValue

abstract class ASimpleActivity extends Activity {

  protected implicit lazy val activity: Activity = this
  protected implicit lazy val context: Context = this

  //R.id vom layout
  protected def getLayoutID: Int

  //R.id vom Content XML Element
  protected def getContentID: Int

  //R.id vom Menü
  protected def getMenuID: Int = R.menu.nomenu

  //Ob das App Icon oben links als zurück button angezeigt werden soll
  protected def viewHomeAsBack = true

  //Layout Objekt
  protected lazy val content = findViewById(getContentID).asInstanceOf[ViewGroup]

  //"Konstruktor"
  protected override def onCreate(state: Bundle) = {
    super.onCreate(state)
    setContentView(getLayoutID)
  }

  //"Konstruktor" vom Menü oben
  protected override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(getMenuID, menu)

    //neben dem Icon oben Links einen Pfeil nach links für "zurück"
    getActionBar.setDisplayHomeAsUpEnabled(viewHomeAsBack)

    return true
  }

  //bei einem Klick auf das Menü oben
  protected override def onOptionsItemSelected(item: MenuItem): Boolean =
    item.getItemId match {
      //"zurück" links neben dem AppIcon
      case android.R.id.home =>
        //diese Activity schließen
        finish()
        return true
      case _ =>
        return super.onOptionsItemSelected(item)
    }

  //diese Activity neu starten
  protected def restartActivity = {
    val i = getIntent
    finish
    startActivity(i)
  }

  //Öffnen einer anderen Activity
  protected def openActivity(c: Class[_]) =
    //startActivity(new Intent(this, c))
    startActivityForResult(new Intent(this, c), 0)

  //einer aufrufenden Activity ein Ergebnis zurückliefern
  protected def result(s: => Serializable) = Left { () =>
    val i = new Intent()
    i.putExtra("result", s)
    setResult(Activity.RESULT_OK, i)
    finish
  }

  protected def siresult(s: => SIValue) = result(s.serialize)

  protected def getStr(id: Int): String = getResources.getString(id)

  protected def alert(str: String) =
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show

}