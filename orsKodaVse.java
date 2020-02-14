package com.example.marvelovisuperjunaki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText vrstaInput;
    private EditText steviloInput;
    private TextView countdownDEC;
    private TextView countdownBIN;
    private TextView countdownHEX;
    private TextView countdownOCT;
    private Button nastaviGumb;
    private Button zacniGumb;


    private CountDownTimer countDownTimer;  // Iz knjižnice

    private long casZacetekMilisek; // 10 min
    private long casOstanekMilisek;
    private long casKoncni;

    private boolean timerRunning;   // Za timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vrstaInput = findViewById(R.id.vnesi_vrsto);
        steviloInput = findViewById(R.id.vnesi_stevilo);
        countdownDEC = findViewById(R.id.countdown_dec);
        countdownOCT = findViewById(R.id.countdown_oct);  // Variabla za stevila
        countdownBIN = findViewById(R.id.countdown_bin);
        countdownHEX = findViewById(R.id.countdown_hex);

        nastaviGumb = findViewById(R.id.gumb_nastavi);
        zacniGumb = findViewById(R.id.countdown_button);  // Variabla za gumb


        nastaviGumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String vrsta = vrstaInput.getText().toString();
                String input = steviloInput.getText().toString();

                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Polje ne sme biti prazno!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long milisekInput = Long.parseLong(input) * 1000;
                if (milisekInput == 0) {
                    Toast.makeText(MainActivity.this, "Vnesi pozitivno število!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Če vrstaInput = oct, iz oct v dec, dalje
                switch (vrsta) {
                    case "oct":
                        input = octToDec();
                        milisekInput = Integer.parseInt(input);
                        nastaviCas(milisekInput);
                        break;

                    // Če vrstaInput = bin, iz bin v oct, dalje
                    case "bin":
                        input = binToDec();
                        milisekInput = Integer.parseInt(input);
                        nastaviCas(milisekInput);
                        break;

                    // Če vrstaInput = dec, dalje
                    case "dec":
                        milisekInput = Integer.parseInt(input) * 1000;
                        nastaviCas(milisekInput);
                        break;

                    // Če vrstaInput = hex, dalje
                    case "hex":
                        input = hexToDec();
                        milisekInput = Integer.parseInt(input);
                        nastaviCas(milisekInput);
                        break;
                }

                vrstaInput.setText("");
                steviloInput.setText("");
            }
        });


        zacniGumb.setOnClickListener(new View.OnClickListener() {     // Klik na gumb
            @Override
            public void onClick(View v) {
                startStop();    // Funkcija startStop
            }
        });
    }

    // NASTAVI ČAS
    private void nastaviCas(long milisekInp) {
        casZacetekMilisek = milisekInp;
        resetTimer();
    }


    // PRETVORI IZ OCT V DEC
    public String octToDec() {

        String sekunde = steviloInput.getText().toString(); // Input sekunde v string
        int length = sekunde.length();  // Dolžina stringa sekunde
        int i;
        int rez = 0;    // Rezultat
        int index = 0;  // Za kvadriranje 8

        for (i = length - 1; i >= 0; i--) {
            int stevilka = Integer.parseInt(String.valueOf(sekunde.charAt(i))); // Od zadaj naprej v št
            rez += stevilka * Math.pow(8, index);   // Število * 8^index
            index++;
        }

        rez *= 1000;
        sekunde = String.valueOf(rez);

        return sekunde;
    }


    // PRETVORI IZ BIN V DEC
    public String binToDec() {
        String sekunde = steviloInput.getText().toString(); // Input sekunde v string
        int length = sekunde.length();  // Dolžina stringa sekunde
        int i;
        int rez = 0;    // Rezultat
        int index = 0;  // Za kvadriranje 2

        for (i = length - 1; i >= 0; i--) {
            int stevilka = Integer.parseInt(String.valueOf(sekunde.charAt(i))); // Od zadaj naprej v št
            rez += stevilka * Math.pow(2, index);   // Število * 2^index
            index++;
        }

        rez *= 1000;
        sekunde = String.valueOf(rez);

        return sekunde;
    }


    // PRETVORI IZ HEX V DEC
    public String hexToDec() {
        String sekunde = String.valueOf(steviloInput.getText()); // Input sekunde v string
        int length = sekunde.length();
        int rez = 0;
        int index = 0;
        int i;


        for (i = length - 1; i >= 0; i--) {
            if (sekunde.charAt(i) == 'A') {
                int stevilka = 10;
                rez += stevilka * Math.pow(16, index);
                index++;
            } else if (sekunde.charAt(i) == 'B') {
                int stevilka = 11;
                rez += stevilka * Math.pow(16, index);
                index++;
            } else if (sekunde.charAt(i) == 'C') {
                int stevilka = 12;
                rez += stevilka * Math.pow(16, index);
                index++;
            } else if (sekunde.charAt(i) == 'D') {
                int stevilka = 13;
                rez += stevilka * Math.pow(16, index);
                index++;
            } else if (sekunde.charAt(i) == 'E') {
                int stevilka = 14;
                rez += stevilka * Math.pow(16, index);
                index++;
            } else if (sekunde.charAt(i) == 'F') {
                int stevilka = 15;
                rez += stevilka * Math.pow(16, index);
                index++;
            } else {
                int stevilka = Integer.parseInt(String.valueOf(sekunde.charAt(i)));
                rez += stevilka * Math.pow(16, index);   // Število * 16^index
                index++;
            }
        }

        rez *= 1000;
        sekunde = String.valueOf(rez);

        return sekunde;
    }


    // START, STOP PREVERIŠ
    public void startStop() {
        if (timerRunning) {     // Če timerRunning = true
            stopTimer();    // Funkcija ustavi
        } else {
            startTimer();   // Sicer začni
        }
    }


    // START TIMER
    public void startTimer() {  // Funkcija začni
        casKoncni = System.currentTimeMillis() + casOstanekMilisek;

        countDownTimer = new CountDownTimer(casOstanekMilisek, 1000) {
            @Override
            public void onTick(long milliesUntilFinished) {
                casZacetekMilisek = milliesUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {    // Zaključek štopanja??
                posodobiIzgled();
            }
        }.start();


        zacniGumb.setText("ZAKLJUČI");
        timerRunning = true;    // Boolean = true
    }


    // STOP TIMER
    public void stopTimer() {   // Funkcija končaj
        countDownTimer.cancel();    // Pavziraj
        timerRunning = false;   // Boolean = false
        zacniGumb.setText("ZAČNI");   // Gumb se spremeni v start
    }


    // RESET TIMER
    public void resetTimer() {
        casOstanekMilisek = casZacetekMilisek;
        updateTimer();
    }


    // V OCT ZA ODŠTEVANJE
    public void decToOct () {

        StringBuilder oct_timeLeftText = new StringBuilder();

        int cas = (int) casZacetekMilisek / 1000;
        int ostanek;    // Rezultat v oct

        // Račun
        while (cas != 0) {
            ostanek = cas % 8;  // Ostanek 74
            cas = cas / 8;    // Celota

            oct_timeLeftText.insert(0, ostanek);
        }

        int vstevilko = Integer.parseInt(String.valueOf(oct_timeLeftText)); // rezultat da v integer
        int minute = (vstevilko / 60);  // Dobiš minute
        int sekunde = (vstevilko % 60); // Dobiš sekunde

        // Izpis
        String izpis = minute + ":";
        if (sekunde < 10) izpis += "0";
        izpis += sekunde;

        countdownOCT.setText(izpis);
    }


    // V OCT ZA ODŠTEVANJE
    public void decToBin () {

        StringBuilder bin_minLeftText = new StringBuilder();
        StringBuilder bin_sekLeftText = new StringBuilder();

        int min = (int) casZacetekMilisek / 60000;
        int sek = (int) casZacetekMilisek % 60000 / 1000;


        int ostanekMin;
        int ostanekSek;

        // Račun min
        if (min == 0) {
            bin_minLeftText.insert(0, 0);
        }
        else {
            while (min != 0) {
                ostanekMin = min % 2;   // Ostanek
                min = min / 2;

                bin_minLeftText.insert(0, ostanekMin);
            }
        }

        // Račun sek
        if (sek == 0) {
            bin_sekLeftText.insert(0, 0);
        }
        else {
            while (sek != 0) {
                ostanekSek = sek % 2;  // Ostanek
                sek = sek / 2;    // Celota

                bin_sekLeftText.insert(0, ostanekSek);
            }
        }

        int minute = Integer.parseInt(String.valueOf(bin_minLeftText));  // Rezultat v int
        int sekunde = Integer.parseInt(String.valueOf(bin_sekLeftText)); // Rezultat v int

        // Izpis
        String izpis;

        izpis = "" + minute + ":";
        izpis += sekunde;


        countdownBIN.setText(izpis);
    }

    public void decToHex() {
        String rezultatMin = "";
        String rezultatSek = "";

        int min = (int) casZacetekMilisek / 60000;
        int sek = (int) casZacetekMilisek % 60000 / 1000;

        int ostanekMin;
        int ostanekSek;


        if (min == 0) {
            rezultatMin += 0;
        }
        else {
            while (min != 0) {
                ostanekMin = min % 16;
                min = min / 16;

                if (ostanekMin == 15) {
                    rezultatMin += "F";
                } else if (ostanekMin == 14) {
                    rezultatMin += "E";
                } else if (ostanekMin == 13) {
                    rezultatMin += "D";
                } else if (ostanekMin == 12) {
                    rezultatMin += "C";
                } else if (ostanekMin == 11) {
                    rezultatMin += "B";
                } else if (ostanekMin == 10) {
                    rezultatMin += "A";
                } else {
                    rezultatMin += ostanekMin;
                }
            }
        }

        if (sek == 0) {
            rezultatSek += 0;
        }
        else {
            while (sek != 0) {
                ostanekSek = sek % 16;
                sek = sek / 16;

                if (ostanekSek == 15) {
                    rezultatSek += "F";
                } else if (ostanekSek == 14) {
                    rezultatSek += "E";
                } else if (ostanekSek == 13) {
                    rezultatSek += "D";
                } else if (ostanekSek == 12) {
                    rezultatSek += "C";
                } else if (ostanekSek == 11) {
                    rezultatSek += "B";
                } else if (ostanekSek == 10) {
                    rezultatSek += "A";
                } else {
                    rezultatSek += ostanekSek;
                }
            }
        }

        rezultatMin = new StringBuilder(rezultatMin).reverse().toString();
        rezultatSek = new StringBuilder(rezultatSek).reverse().toString();


        // Izpis
        String izpis;

        izpis = "" + rezultatMin + ":";
        izpis += rezultatSek;

        countdownHEX.setText(izpis);
    }

    // ODŠTEVA
    public void updateTimer() {     // Za spreminjanje časa na sekundo
        int minutes = (int) casZacetekMilisek / 60000;
        int seconds = (int) casZacetekMilisek % 60000 / 1000;

        String timeLeft;
        timeLeft = "" + minutes;
        timeLeft += ":";

        if (seconds < 10) {
            timeLeft += "0";
        }
        timeLeft += seconds;

        decToOct();    // OCT
        decToBin();    // BIN
        decToHex();    // HEX
        countdownDEC.setText(timeLeft); // DEC
    }


    // SPREMENI TO KAR UPORABNIK VIDI
    private void posodobiIzgled() {
        if (timerRunning) {
            vrstaInput.setVisibility(View.INVISIBLE);
            steviloInput.setVisibility(View.INVISIBLE);
            nastaviGumb.setVisibility(View.INVISIBLE);
            zacniGumb.setText("ZAKLJUČI");
        }
        else {
            vrstaInput.setVisibility(View.VISIBLE);
            steviloInput.setVisibility(View.VISIBLE);
            nastaviGumb.setVisibility(View.VISIBLE);
            zacniGumb.setText("START");
        }
    }
}

// XML IZGLED

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginStart="110dp"
        android:layout_marginTop="8dp"
        android:text="@string/alex_brence_andrej_ka_tivnik"
        android:textColor="@android:color/black" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="44dp"
        android:layout_marginTop="50dp"
        android:text="@string/oct"
        android:textColor="@android:color/black"
        android:textSize="30sp"
    />

    <TextView
        android:id="@+id/countdown_oct"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="38dp"
        android:layout_marginTop="102dp"
        tools:text="@string/tools"
        android:textSize="30sp"
    />


    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="51dp"
        android:layout_marginTop="190dp"
        android:text="@string/bin"
        android:textColor="@android:color/black"
        android:textSize="30sp"
    />

    <TextView
        android:id="@+id/countdown_bin"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="38dp"
        android:layout_marginTop="246dp"
        tools:text="@string/tools"
        android:textSize="30sp"
    />


    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="295dp"
        android:layout_marginTop="50dp"
        android:text="@string/dec"
        android:textColor="@android:color/black"
        android:textSize="30sp"
    />

    <TextView
        android:id="@+id/countdown_hex"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="288dp"
        android:layout_marginTop="246dp"
        tools:text="@string/tools"
        android:textSize="30sp"
    />


    <EditText
        android:id="@+id/vnesi_vrsto"
        android:layout_width="150sp"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="263dp"
        android:hint="@string/vrsta"
        android:ellipsize="start"
        android:gravity="center_horizontal"
        android:inputType="text"
        android:maxLength="3"
    />


    <EditText
        android:id="@+id/vnesi_stevilo"
        android:layout_width="150sp"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="208dp"
        android:hint="@string/sekunde"
        android:ellipsize="start"
        android:gravity="center_horizontal"
        android:maxLength="10"
    />

    <Button
        android:id="@+id/gumb_nastavi"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="140dp"
        android:text="@string/nastavi" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="296dp"
        android:layout_marginTop="195dp"
        android:text="@string/hex"
        android:textColor="@android:color/black"
        android:textSize="30sp"
    />

    <TextView
        android:id="@+id/countdown_dec"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="286dp"
        android:layout_marginTop="104dp"
        tools:text="@string/tools"
        android:textSize="30sp"
    />

    <Button
        android:id="@+id/countdown_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginStart="162dp"
        android:layout_marginTop="562dp"
        android:text="@string/zacni"
        android:textSize="20sp" />

</RelativeLayout>


