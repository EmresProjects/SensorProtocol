# Dokumentation der KI-Nutzung – Enes

## Aufgabenbereich

Erstellung der JUnit- und Mockito-Tests für das Sensor-Protokoll.

## Prompt 1: Tests für Message-Klassen

**Prompt:**  
„Erstelle Beispiele für JUnit-Tests für die Sensor-Protokoll-Nachrichten.“

**Zweck:**  
Ich wollte geeignete Testfälle für das Erstellen, Parsen und Validieren der Nachrichten entwickeln.

**Ergebnis:**  
Es wurden Tests für `SPMsg`, `SPMeasurementMsg` und `SPAckMsg` erstellt. Dabei werden gültige Nachrichten sowie fehlerhafte Header, Formate und CRC-Werte geprüft.

## Prompt 2: Tests für SPProtocol

**Prompt:**  
„Zeige ein Beispiel für Mockito-basierte Tests der send()- und receive()-Methoden von SPProtocol.“

**Zweck:**  
Die Kommunikation mit `PhyProtocol` sollte getestet werden, ohne eine echte Netzwerkverbindung aufzubauen.

**Ergebnis:**  
`PhyProtocol` wird in den Tests durch ein Mock-Objekt ersetzt. Dadurch kann geprüft werden, welche Nachricht gesendet und wie eine empfangene Nachricht verarbeitet wird.

## Prompt 3: Mockito-Konfiguration

**Prompt:**  
„Wie kann Mockito so konfiguriert werden, dass PhyProtocol im vorhandenen Projekt zuverlässig gemockt werden kann?“

**Zweck:**  
Ich benötigte Unterstützung bei der passenden Mockito-Konfiguration für das Projekt.

**Ergebnis:**  
Die Datei `org.mockito.plugins.MockMaker` wurde unter `src/test/resources/mockito-extensions` angelegt und mit `mock-maker-subclass` konfiguriert.

## Eigenleistung

Die KI wurde zur Erstellung von Testbeispielen und zur Erklärung von Mockito verwendet. Die Tests wurden an die tatsächlichen Klassen und Signaturen angepasst und anschließend ausgeführt.