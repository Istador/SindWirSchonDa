package de.blackpinguin.android.sindwirschonda.activities


import de.blackpinguin.android.sindwirschonda.R
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.view.ViewGroup

class MainActivity extends Activity {
  
  val classes = Array( 
      classOf[CalcTimeActivity],
      classOf[CalcDistanceActivity],
      classOf[CalcSpeedActivity]
  )
  
  case class MainClick(index: Int) extends OnClickListener {
    override def onClick(v: View) = {
      val i = new Intent(MainActivity.this, classes(index))
      startActivity(i)
    }
  }
  
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val main = findViewById(R.id.aMain).asInstanceOf[ViewGroup]
    
    for{
      i <- 0 until main.getChildCount
      child = main.getChildAt(i)
      if child.isInstanceOf[Button]
      button = child.asInstanceOf[Button]
    }{
      button.setOnClickListener(MainClick(i))
    }
    
  }
  
  
}