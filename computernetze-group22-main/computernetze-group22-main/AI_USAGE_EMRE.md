# Dokumentation der KI-Nutzung – Emre

## Aufgabenbereich

Entwicklung der Client- und Server-Anwendung für das Sensor-Protokoll.

## Prompt 1: Aufbau von Client und Server

**Prompt:**  
„Zeige mir, wie ein Client und ein Server mit dem vorhandenen PHY-Framework und dem Sensor-Protokoll aufgebaut werden können.“

**Zweck:**  
Ich wollte verstehen, wie `PhyProtocol` und `SPProtocol` innerhalb einer ausführbaren Anwendung verwendet werden.

**Ergebnis:**  
Die vorgeschlagene Struktur wurde an die vorhandenen Klassen und Methodensignaturen des Projekts angepasst.

## Prompt 2: SensorClient

**Prompt:**  
„Erstelle ein Beispiel für einen Sensor-Client, der regelmäßig Messwerte sendet und anschließend auf eine ACK-Nachricht wartet.“

**Zweck:**  
Ich benötigte Unterstützung beim Aufbau der Sendeschleife und bei der Verarbeitung der Serverantwort.

**Ergebnis:**  
Der Client erzeugt simulierte Messwerte, versendet `SPMeasurementMsg`-Nachrichten und verarbeitet empfangene `SPAckMsg`-Nachrichten.

## Prompt 3: SensorServer

**Prompt:**  
„Erstelle ein Beispiel für einen Server, der Sensormesswerte empfängt und eine ACK-Nachricht an den richtigen Client zurücksendet.“

**Zweck:**  
Ich wollte sicherstellen, dass der Server den Absender einer Messung erkennt und die Antwort an dessen PHY-Adresse sendet.

**Ergebnis:**  
Der Server empfängt Messwertnachrichten, gibt deren Inhalt aus und sendet ein ACK an den jeweiligen Sensor zurück.

## Eigenleistung

Die KI wurde als Hilfsmittel für Strukturvorschläge und Beispiele eingesetzt. Der Code wurde an das vorhandene Framework angepasst, überprüft und getestet.