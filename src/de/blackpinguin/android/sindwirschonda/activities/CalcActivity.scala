package de.blackpinguin.android.sindwirschonda.activities

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import de.blackpinguin.android.sindwirschonda.views._

abstract class CalcActivity extends Activity {

  lazy val lay = findViewById(getLayoutId).asInstanceOf[ViewGroup]
  lazy val a = lay.getChildAt(0).asInstanceOf[SIValueInput]
  lazy val b = lay.getChildAt(2).asInstanceOf[SIValueInput]
  lazy val c = lay.getChildAt(6).asInstanceOf[SIValueOutput]

  val r = new Runnable() {
    override def run = changed
  }

  def changed: Unit
  def getMenu: Int
  def getLayout: Int
  def getLayoutId: Int

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(getLayout)

    a.callback = r
    b.callback = r
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean =
    item.getItemId match {
      case android.R.id.home =>
        finish()
        return true
      case _ =>
        return super.onOptionsItemSelected(item)
    }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(getMenu, menu)
    getActionBar.setDisplayHomeAsUpEnabled(true)
    return true
  }

}