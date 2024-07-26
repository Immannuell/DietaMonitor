package com.example.projeto;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ProgressBar progressBarProteina;
    Button incrementButton;
    Button decrementButton;
    EditText textoinput;
    EditText inputproteina;
    int valor= 0;
    int valorProteinas = 0;
    TextView textView;
    TextView textProteina;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseManager = new DatabaseManager(this);

        progressBar = findViewById(R.id.progressBar2);
        progressBarProteina = findViewById(R.id.progressBar3);
        textView = findViewById(R.id.textView2);
        textoinput = findViewById(R.id.editTextNumber);
        textProteina = findViewById(R.id.textView5);
        inputproteina = findViewById(R.id.editTextNumber2);

        incrementButton = findViewById(R.id.button5); // Certifique-se de que o ID do botão está correto
        decrementButton = findViewById(R.id.button4);
        incrementButton.setOnClickListener(v -> Aumentar());
        decrementButton.setOnClickListener(v -> Diminuir());

        displayData();

    }

    public void Aumentar() {

        int inputValue = parseEditTextValue(textoinput);
        int inproteina = parseEditTextValue(inputproteina);
        valor += inputValue;
        valorProteinas += inproteina;
        progressBar.setProgress(valor);

        textoinput.setText("");
        inputproteina.setText("");

        databaseManager.insertData(valor, valorProteinas);
        displayData();
    }
    public void Diminuir(){
        int inputValue = parseEditTextValue(textoinput);
        int inproteina = parseEditTextValue(inputproteina);
        if(valor>0 && valor-inputValue>=0){
            valor -= inputValue;



        }else{
            valor = 0;
        }
        if(valorProteinas>0 && valorProteinas-inproteina>=0){
            valorProteinas -= inproteina;

        }else{
            valorProteinas = 0;
        }
        progressBar.setProgress(valor);
        databaseManager.insertData(valor, valorProteinas);
        displayData();
    }
    private void displayData() {
        Cursor cursor = databaseManager.getData();
        if (cursor.moveToLast()) {
            int caloriesColumnIndex = cursor.getColumnIndex("calorias");
            int proteinsColumnIndex = cursor.getColumnIndex("proteinas");

            if (caloriesColumnIndex != -1 && proteinsColumnIndex != -1) {
                valor = cursor.getInt(caloriesColumnIndex);
                valorProteinas = cursor.getInt(proteinsColumnIndex);
                progressBar.setProgress(valor);
                textView.setText(String.valueOf(valor));
                progressBarProteina.setProgress(valorProteinas);
                textProteina.setText(String.valueOf(valorProteinas));
            }
        }
        cursor.close();
    }
    private int parseEditTextValue(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            return 0; // Retorna 0 se o campo estiver vazio
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // Retorna 0 se a conversão falhar
        }
    }
    public void AbrirTela(){
        Intent segundaTela = new Intent(this, Tela2.class);
        startActivity(segundaTela);
    }
}
