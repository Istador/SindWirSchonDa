package de.blackpinguin.android.util

import android.app.Activity

//Zeitmessung
object Timer {

  //Stringrepräsentation für Benutzer, und Wert + Einheit für SIValue-Objekte
  case class Timestamp(str: String, value: Double, unit: String)

  //Hilfsmethoden
  private[this] def st(in: Int, n: Int): String =
    st(in.toString, n)
  private[this] def st(in: Long, n: Int): String =
    st(in.toString, n)
  private[this] def st(in: String, n: Int): String =
    (for (i <- 0 until n - in.length) yield "0").mkString + in

  //Auswerten einer Zeit zu einem Timestamp
  private[Timer] def analyze(time: Long): Timestamp = {
    //Berechnung einzelner Bestandteile
    var tmp = time
    val ms = tmp % 1000
    tmp = (tmp - ms) / 1000
    val s = tmp % 60
    tmp = (tmp - s) / 60
    val m = tmp % 60
    tmp = (tmp - m) / 60
    val h = tmp

    //Ausgabestring
    val str = st(h, 2) + ":" + st(m, 2) + ":" + st(s, 2) + "." + st(ms, 3)

    //Einheitenauswahl
    if (h > 0) Timestamp(str, time.toDouble / (1000.0 * 60.0 * 60.0), "h")
    else if (m > 0) Timestamp(str, time.toDouble / (1000.0 * 60.0), "m")
    else Timestamp(str, time.toDouble / 1000.0, "s")
  }

}

class Timer(activity: Activity, callback: Timer.Timestamp => Unit) {

  private[this] var startTime: Long = 0

  private[this] var running = false

  private[this] var t: Thread = null

  private[this] val r = new Runnable() {
    def run = {
      running = true
      while (running) {
        Thread.sleep(100)
        update
      }
    }
  }

  def start = synchronized {
    if (!running && (t == null || !t.isAlive)) {
      startTime = System.currentTimeMillis
      t = new Thread(r)
      t.start
    }
  }

  def stop = if (running) {
    running = false
    update
  }

  def update = {
    //Zeit berechnen
    val time = System.currentTimeMillis - startTime
    //Zeit über Callback zurückgeben
    activity.runOnUiThread(new Runnable() { def run = callback(Timer.analyze(time)) })
  }

}