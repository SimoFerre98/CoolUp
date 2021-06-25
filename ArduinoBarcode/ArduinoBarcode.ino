#include <LiquidCrystal.h>
#include <SoftwareSerial.h>
LiquidCrystal lcd(7,6,5,4,3,2);
String ricevuto;
SoftwareSerial BTserial(10, 11); // Trasmissione del bluetooth
SoftwareSerial GM(0, 1); // Trasmissione del GM65
#define GM__BAUD 9600 //9600
#define BT__BAUD 9600 //38400
String Barcode = "";

void setup() {
  Serial.begin(9600);
  BTserial.begin(9600);
  GM.begin(GM__BAUD);   
  lcd.begin(16,2);
  lcd.setCursor(0,0);
  lcd.print("Accensione...");
  Serial.println("Accensione...");
  delay(1000);
  lcd.clear();
}

void loop() {
  if(GM.available())
  {
     lcd.clear();
     Barcode = GM.readString();
     Serial.println(Barcode );
     lcd.setCursor(0,0);
     lcd.print(Barcode);
     if(Serial.available() > 0)
     {
       BTserial.println(Barcode);
       Barcode = "";
     }
     delay(100);
  }
}
