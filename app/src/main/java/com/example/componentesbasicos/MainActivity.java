package com.example.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButton;
    private TextView textResultToggle;

    private EditText editText;
    private Button buttonEditText;
    private TextView textResultEditText;

    private ListView listView;
    private String[] itens = {"Item 1", "Item 2", "Item 3", "Item 4"};

    private String[] frutas = {"Maçã", "Banana", "Laranja", "Limão"};
    private AutoCompleteTextView autoTextView;

    private Spinner spinner;
    private String[] cores = {"Azul", "Preto", "Branco"};

    private RadioButton signoLibras, signoSagitario;
    private RadioGroup radioGroupSignos;
    private TextView textResultRadioButton;

    private Button buttonLongClick;

    private Button buttonAudioPlay;
    private MediaPlayer mediaPlayer;

    private Button buttonDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Toggle Button ----------------------------------------------------------------------------
        toggleButton = (ToggleButton) findViewById(R.id.idToggleButton);
        textResultToggle = (TextView) findViewById(R.id.idTextResultToggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    textResultToggle.setText("Ligado");
                } else {
                    textResultToggle.setText("Desligado");
                }
            }
        });

        //Edit Text---------------------------------------------------------------------------------
        editText = (EditText) findViewById(R.id.idEditText);
        buttonEditText = (Button) findViewById(R.id.idButtonEditText);
        textResultEditText = (TextView) findViewById(R.id.idResultEditText);

        buttonEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textResultEditText.setText(editText.getText());
            }
        });

        //Array, ListView, Toast -------------------------------------------------------------------
        listView = (ListView) findViewById(R.id.idListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String valorClicado = listView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), valorClicado, Toast.LENGTH_SHORT).show();
            }
        });

        //AutoComplete -----------------------------------------------------------------------------
        autoTextView = (AutoCompleteTextView) findViewById(R.id.idAutoComplete);
        ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, frutas);
        autoTextView.setThreshold(1);
        autoTextView.setAdapter(adapterAuto);

        //Spinners ---------------------------------------------------------------------------------
        spinner = (Spinner) findViewById(R.id.idSpinner);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cores);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);

        //Radio Button
        textResultRadioButton = (TextView) findViewById(R.id.idResultRadioButton);
        radioGroupSignos = (RadioGroup) findViewById(R.id.idRadioGroupSignos);

        radioGroupSignos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.idRadioLibras) {
                    textResultRadioButton.setText("Seu signo é Libras!");
                } else if (i == R.id.idRadioSagitario) {
                    textResultRadioButton.setText("Seu signo é Sagitário!");
                }
            }
        });

        // Navegar entre activities ----------------------------------------------------------------
        Button btnMore = (Button) findViewById(R.id.idButtonMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });

        //Clique longo -----------------------------------------------------------------------------
        buttonLongClick = (Button) findViewById(R.id.idButtonLogClick);
        buttonLongClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "Você realizou um clique longo!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //Tocar som --------------------------------------------------------------------------------
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        buttonAudioPlay = (Button) findViewById(R.id.idAudioButton);

        buttonAudioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            }
        });

        // Menu Dropdown/Popup ---------------------------------------------------------------------
        buttonDropdown = (Button) findViewById(R.id.idButtonDropDown);
        buttonDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), buttonDropdown);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Opção clicada : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });
                popup.show();
            }
        });


    }
}
