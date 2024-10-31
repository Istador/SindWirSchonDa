# English

Are we there yet? is an Android-App that was created in the summer semester 2014 at the [Mobile Systems](https://rcl.blackpinguin.de/haw/bms/14ss/MOSY/) compulsory elective course of [B.Sc. Media Systems (B-MS)](https://rcl.blackpinguin.de/haw/bms/).

The task was to develop a native app for an arbitrary mobile operating system, acting as a measuring device that uses its embedded sensors. An Android-App was made to try to answer the age-old question from bored kids on the backseat: Are we there yet?

It answers this question at three aspects: distance, speed and time[1], calculating one of them with the remaining two. Besides entering values manually or choosing from some predefined constants, it's possible to measure each quantity manually. Time by the internal clock, distance by the device's location provider, and speed as the combination of both.

It has a german and an english user interface, which is selected by the system language[2]. It was developed in Scala, uses custom Views and an own implementation for units[3] and values.

Together with projects from other students of this course, the App was exhibited on 2014-06-24. The APK of Are we there yet? for Android 4.0 or higher can be downloaded by [clicking here](https://github.com/Istador/SindWirSchonDa/releases/download/v1.0.0/SindWirSchonDa.apk).

|                  |                                                                                                        |
| ---------------- | ------------------------------------------------------------------------------------------------------ |
| __Languages__    | Scala, UML                                                                                             |
| __Technologies__ | Android, AndroidScalaProguard, Google Play Fused Location Provider, Activities, Custom Views, SI-Units |
| __Tools__        | Dia                                                                                                    |
| __IDE__          | ADT Eclipse with Scala IDE                                                                             |
| __Participants__ | 1                                                                                                      |

### Footnotes

- [1]	How far is it away?
  
  How fast are we moving?
  
  How long until we are there?
- [2]	Changing the system language while it's running results in a restart of the App. Its internal state will be lost.
- [3]	The user can change between units of the same type (distance, velocity and time) at will. The values will be converted from unit to unit for display purposes, but the calculations are done in the corresponding SI units. Errors of accuracy are to be expected.

# Deutsch

Sind wir schon da? ist eine Android-App, die im Sommersemester 2014 im Wahlpflichtfach [Mobile Systeme](https://rcl.blackpinguin.de/haw/bms/14ss/MOSY/de.html) des Studienganges [Bachelor Media Systems (B-MS)](https://rcl.blackpinguin.de/haw/bms/de.html) erstellt wurde.

Aufgabe war es, eine native App für ein beliebiges mobiles Betriebssystem zu entwickeln, die als Messgerät dienen soll und auf die eingebauten Sensoren des Gerätes zugreift. Es wurde eine Android-App erstellt, die versucht, die uralte Frage von gelangweilten Kindern auf dem Rücksitz zu beantworten: Sind wir schon da?

Beantwortet wird die Frage in drei Aspekten: Entfernung, Geschwindigkeit und Zeit[1]. Einer dieser Werte wird dabei jeweils aus den verbleibenden beiden Werten berechnet. Neben der Möglichkeit, Werte manuell einzugeben oder aus vorgegebenen Konstanten zu wählen, ist es auch möglich, jeden Wert manuell zu messen: Zeit durch die interne Uhr, Entfernung durch den Location Provider des Gerätes und die Geschwindigkeit aus der Kombination von Beidem.

Die App hat sowohl ein deutsches als auch ein englisches Benutzerinterface, welches durch die Systemsprache ausgewählt wird[2]. Entwickelt wurde sie in Scala, verwendet selbst erstellte Views und eine eigene Implementation für Einheiten[3] und Werte.

Zusammen mit den Projekten der anderen Studierenden dieses Kurses wurde die App am 24.06.2014 ausgestellt. Die APK von Sind wir schon da? für Android 4.0 oder höher kann [direkt hier](https://github.com/Istador/SindWirSchonDa/releases/download/v1.0.0/SindWirSchonDa.apk) heruntergeladen werden.

|                  |                                                                                                            |
| ---------------- | ---------------------------------------------------------------------------------------------------------- |
| __Sprachen__     | Scala, UML                                                                                                 |
| __Technologien__ | Android, AndroidScalaProguard, Google Play Fused Location Provider, Activities, eigene Views, SI-Einheiten |
| __Tools__        | Dia                                                                                                        |
| __IDE__          | ADT Eclipse mit Scala IDE                                                                                  |
| __Beteiligte__   | 1                                                                                                          |

### Fußnoten

- [1]	Wie weit ist es noch?
  
  Wie schnell bewegen wir uns?
  
  Wie lange dauert es noch?
- [2]	Die Systemsprache zu ändern, während die App noch läuft, führt zu einem Neustart der App. Der interne Zustand der App geht dabei verloren.
- [3]	Der Benutzer kann von einer in die andere Einheit wechseln. Die Werte werden dabei umgerechnet, die Rechnungen finden aber in SI-Einheiten statt. Genauigkeitsfehler sind dabei zu erwarten.
