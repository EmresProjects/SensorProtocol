Dokumentation der KI-Nutzung – Murat

## Prompt 1: Analyse der Protokollstruktur

**Prompt:**  
„Analysiere die Klassen SLPProtocol und SLPMsg und erkläre die Aufgabenverteilung zwischen Protocol-Klasse und Message-Klassen.“

**Zweck:**  
Ich wollte die Architektur des vorhandenen Frameworks verstehen. Besonders wichtig war die Trennung zwischen Nachrichtenerzeugung beziehungsweise Parsing und der Kommunikation über die PHY-Schnittstelle.

**Ergebnis:**  
Die Analyse zeigte, dass die Message-Klassen für das Erstellen, Parsen und Validieren zuständig sind. Die Protocol-Klasse koordiniert dagegen das Senden und Empfangen.Dadurch konnte ich ein besseres Verständnis aneignen und so die Klassen implementieren.

## Prompt 2: CRC32-Integration

**Prompt:**  
„Zeige ein Beispiel für die Verwendung der Java-Klasse CRC32 zur Integritätsprüfung von Nachrichten.“

**Zweck:**  
Ich benötigte Unterstützung bei der korrekten Verwendung von `java.util.zip.CRC32`.

**Ergebnis:**  
Das Beispiel wurde selbständig von mir an das String-basierte Nachrichtenformat angepasst. Die CRC wird ohne das CRC-Feld berechnet und nach dem Parsing überprüft.

## Prompt 3: Nachrichtenformat

**Prompt:**  
„Schlage ein einfaches, erweiterbares String-basiertes Nachrichtenformat für Sensor-Messwerte und ACK-Nachrichten vor.“

**Zweck:**  
Ich wollte ein verständliches Format entwickeln, das Absender, Empfänger, Nutzdaten und CRC enthält.

**Ergebnis:**  
Die Vorschläge wurden geprüft und selbständig an die Aufgabenstellung angepasst. Verwendet werden:

- `sp meas <sender> <receiver> <value> <crc>`
- `sp ack <sender> <receiver> <status> <crc>`

## Eigenleistung

Die KI wurde als Hilfsmittel zum Verständnis des Frameworks und für Beispiele verwendet. Die Vorschläge wurden anhand der Aufgabenstellung und der vorhandenen Klassen geprüft, angepasst und getestet.