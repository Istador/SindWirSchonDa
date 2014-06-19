package de.blackpinguin.android.sindwirschonda.views

import android.view.View
import android.content.Context
import scala.collection.mutable.Queue
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.graphics.Color

object ChartView {
  case class Point(x: Double, y:Double){}
}

class ChartView(context: Context, attrs: AttributeSet) extends View(context, attrs) {
  import ChartView.Point
  
  //Wieviele Messstriche
  private[this] val ny: Int = 3 //links
  private[this] val nx: Int = 6 //unten
  
  //Achsenbeschriftung
  private[this] val labelX = "t"
  private[this] val labelY = "v"
  
  //Punkte
  private[this] val n = 7 //maximale anzahl punkte anzeigen
  private[this] val points = Queue[Point]()
  
  
  private[this] val pLines = new Paint() //dickere linien
  pLines.setStrokeWidth(2f) //2 px
  pLines.setAntiAlias(true)
  
  private[this] val pData = new Paint(pLines) //farbig
  pData.setColor(0xFF5555FF) //bläulich
  
  private[this] val pTextLeft = new Paint()
  pTextLeft.setTextAlign(Paint.Align.LEFT) //Linksbündig
  pTextLeft.setTextSize(24f) //Textgröße
  
  private[this] val pTextBottom = new Paint(pTextLeft)
  pTextBottom.setTextAlign(Paint.Align.RIGHT) //Rechtsbündig
  

    
  override def onDraw(c: Canvas) = {
    //linke linie
    c.drawLine(5f, 5f, 5f, getHeight-5f, pLines)
    
    //untere Linie
    c.drawLine(5f, getHeight-5f, getWidth-5, getHeight-5f, pLines)
    
    //Messstriche
    for(i <- 0 until ny){
      //auf der linken linie
      val h = 5f+i*(getHeight-10f)/ny
      c.drawLine(0f, h, 10f, h, pLines)
    }
    for(i <- 1 to nx){  
      //auf der unteren linie
      val w = 5f+i*(getWidth-10f)/nx
      c.drawLine(w, getHeight-10f, w, getHeight, pLines)
    }
    
    //Labels
    c.drawText(labelY, 12f, 15f, pTextLeft)
    c.drawText(labelX, getWidth-5f, getHeight-17f, pTextBottom)
    
    //Minimum- und Maximumwert ermitteln
    
    val (minX, minY, maxX, maxY) = {
      if(points.length >= 2)
        (points.minBy(_.x).x, 0.0, points.maxBy(_.x).x, points.maxBy(_.y).y)
      else if(points.length == 1)
        (0.0, 0.0, points(0).x, points(0).y)
      else
        (0.0, 0.0, 0.0, 0.0)
    }
    
    //skalierungsfaktoren
    val sX = if(minX == maxX) 0f else (getWidth-10.0) / (maxX - minX)
    val sY = if(minY == maxY) 0f else (getHeight-10.0) / (maxY - minY)
    
    //Hilfsmethode: um eine Linie zwischen zwei Punkten zu zeichnen
    def line(a: Point, b: Point) = {
      c.drawLine(
          (5.0 + (a.x - minX) * sX).toFloat,
          (getHeight - 5.0 - (a.y - minY) * sY).toFloat,
          (5.0 + (b.x - minX) * sX).toFloat,
          (getHeight - 5.0 - (b.y - minY) * sY).toFloat,
          pData
      )
    }
    
    //Hilfsmethode: um einen Punkt zeichnen
    def point(p: Point) = {
      c.drawCircle(
          (5.0 + (p.x - minX) * sX).toFloat, 
          (getHeight - 5.0 - (p.y - minY) * sY).toFloat, 
          5f, pData)
    }
    
    //Linien zum verbinden von Punkten
    if(points.length >= 2)
    points reduceLeft { (a, b) =>
      line(a, b)
      b
    }
    //nur ein Punkt vorhanden, verbinde mit 0Punkt
    else if(points.length == 1)
      line( Point(0f, 0f), points(0) )
    
    //Punkte zeichnen
    points foreach point _
    
  }
  

  
  def clear {
    points.clear
    invalidate
  }
  
  def +(p: Point){
    //maximale Anzahl erreicht
    if(points.length >= n)
      points.dequeue //entfernen
    
    //einfügen
    points.enqueue(p)
    
    //neu zeichnen
    invalidate
  }
  
}