# Sensor Protocol (SP)

## Teammitglieder

| Name | Matrikelnummer | Studiengang | Aufgabenbereich |
|---|---:|---|---|
| Murat Süleyman Özbek | 819482 | Wirtschaftsinformatik | Protokoll und Nachrichtenklassen |
| Emre Soylu | 819489 | Wirtschaftsinformatik | Client und Server |
| Muhammed Enes Cetin | 819861 | Wirtschaftsinformatik| JUnit- und Mockito-Tests |

## Projektbeschreibung

Dieses Projekt implementiert ein einfaches, String-basiertes Sensor-Protokoll auf Grundlage des bereitgestellten Java-Frameworks.

Ein Sensor sendet regelmäßig einen Messwert an einen Server. Nach erfolgreichem Empfang und gültiger CRC32-Prüfung sendet der Server eine ACK-Nachricht an den richtigen Client zurück.

## Projektstruktur

- `demo/src/main/java/sp`: Sensor-Protokoll und Nachrichtenklassen
- `demo/src/main/java/apps`: Client- und Server-Anwendungen
- `demo/src/test/java/sp`: JUnit- und Mockito-Tests
- `demo/src/test/resources`: Mockito-Konfiguration

## Nachrichtenformat

Alle Nachrichten beginnen mit dem festen Header `sp `.

### Messwertnachricht

```text
sp meas <sender> <receiver> <value> <crc>
```

### Verantwortlichkeiten

| Klasse | Aufgabe |
|---|---|
| `SPMsg` | Prüft den Header, erkennt den Nachrichtentyp und enthält die gemeinsame CRC32-Logik |
| `SPMeasurementMsg` | Erstellt und parst Messwertnachrichten |
| `SPAckMsg` | Erstellt und parst ACK-Nachrichten |
| `SPProtocol` | Verbindet Nachrichtenobjekte mit der PHY-Schicht |
| `SensorClient` | Erzeugt und sendet periodisch Messwerte |
| `SensorServer` | Empfängt Messwerte und sendet ACKs zurück |

Die Message-Klassen sind für Erzeugung, Parsing und Validierung zuständig. `SPProtocol` koordiniert den Versand und Empfang über `PhyProtocol`.
