package de.blackpinguin.android

package object util {
  import scala.language.implicitConversions
  
  implicit def UnitToRunnable(f: ()=>Unit): Runnable = 
    new Runnable(){
      def run = f()
    }
}