package com.example.navegacaoentretelas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navegacaoentretelas.Transactions.Constants;

import org.w3c.dom.Text;

public class AdicionarEditarActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDescricao;

    private Button buttonConcluir;
    private Button buttonCancelar;

    private TextView textViewId;

    String request = "";
    String idLivro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar);

        editTextTitulo = (EditText) findViewById(R.id.idTitulo);
        editTextDescricao = (EditText) findViewById(R.id.idDescricao);
        buttonCancelar = (Button) findViewById(R.id.idButtonCancelar);
        buttonConcluir = (Button) findViewById(R.id.idButtonConcluir);
        textViewId = (TextView) findViewById(R.id.idLivro);


        if( getIntent().getExtras() != null ){
            request = (String) getIntent().getExtras().get( "request" );
            if (request.equals(Integer.toString(Constants.REQUEST_ADD))) {
                textViewId.setText("Adicionar livro");
            } else if (request.equals(Integer.toString(Constants.REQUEST_EDIT))) {
                String titulo = (String) getIntent().getExtras().get( "nome" );
                String descricao = (String) getIntent().getExtras().get( "descricao" );
                idLivro = (String) getIntent().getExtras().get( "id" );

                textViewId.setText(idLivro);
                editTextTitulo.setText(titulo);
                editTextDescricao.setText(descricao);
            }

        }

        buttonConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                String titulo = editTextTitulo.getText().toString();
                String descricao = editTextDescricao.getText().toString();

                intent.putExtra( "titulo", titulo );
                intent.putExtra( "descricao", descricao );

                if(request.equals(Integer.toString(Constants.REQUEST_EDIT))) {
                    intent.putExtra( "id", idLivro);
                }

                setResult( Constants.RESULT_ADD, intent );
                finish();
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult( Constants.RESULT_CANCEL );
                finish();
            }
        });


    }
}