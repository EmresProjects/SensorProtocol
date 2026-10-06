# Aufgabenverteilung

## Murat Süleyman Özbek

**Matrikelnummer:** 819482

**Aufgabenbereich:** Implementierung des Sensor-Protokolls und der Nachrichtenklassen

Bearbeitete Klassen:

- `SPMsg`
- `SPMeasurementMsg`
- `SPAckMsg`
- `SPProtocol`

Zu den Aufgaben gehörten die String-basierte Nachrichtenerzeugung, das Parsing, die CRC32-Berechnung und die CRC-Validierung.

## Emre Soylu

**Matrikelnummer:** 819489

**Aufgabenbereich:** Entwicklung der Client- und Server-Anwendung

Bearbeitete Klassen:

- `SensorClient`
- `SensorServer`

Der Client erzeugt und versendet Sensormesswerte. Der Server empfängt die Messwerte und sendet ACK-Nachrichten an den jeweiligen Client zurück.

## Muhammed Enes Cetin

**Matrikelnummer:** 819861

**Aufgabenbereich:** Erstellung der JUnit- und Mockito-Tests

Bearbeitete Testklassen:

- `SPMsgTest`
- `SPMeasurementMsgTest`
- `SPAckMsgTest`
- `SPProtocolTest`

Die Tests überprüfen das Erstellen und Parsen von Nachrichten, ungültige Formate, fehlerhafte CRC-Werte sowie das Senden und Empfangen über `SPProtocol`.