#include <SoftwareSerial.h> //for bluetooth serial communication

int measurement = 0;

//for bluetooth, we need to use the object of class software serial
SoftwareSerial mySerial(12, 13);//Rx Tx ; the Rx Tx pin cannot be used for the bluetooth, don't know why
char command;
String string;
boolean ledon = false;
String tmp;


void setup()
{
  Serial.begin(9600); //to monitor the the bluetooth received signal
  mySerial.begin(9600);
}

void loop()
{
  
  if (mySerial.available() > 0) 
  {
    string = "";
  }
  
  //strOld = string;
  while(mySerial.available() > 0)
  {
    command = ((byte)mySerial.read());
    if(command == ':')
    {
      break;
    }
    
    else
    {
      string += command;
    }
    
    delay(1);
  }
  Serial.println(string);
  //if(strOld != string)
  {  
    if(string == "MD")
    {
      measurement=random(40,90);
      mySerial.print('b'+String(measurement)+'e');
      string = "";
    }

    Serial.println(measurement);
  }
}
 
