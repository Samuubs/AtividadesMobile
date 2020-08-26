package com.example.navegacaoentretelas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.navegacaoentretelas.Model.Livro;
import com.example.navegacaoentretelas.Transactions.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdicionar;
    private Button buttonEditar;
    private Button buttonDeletar;

    private EditText textId;

    private ListView listViewLivros;
    ArrayAdapter adapter;

    private ArrayList<Livro> listLivros = new ArrayList<>();

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference livros = ref.child("livros");
        livros.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Livro>> t = new GenericTypeIndicator<ArrayList<Livro>>() {};
                ArrayList<Livro> livrosArray = snapshot.getValue(t);
                if (livrosArray == null) {
                    listLivros = new ArrayList<Livro>();
                } else {
                    listLivros = livrosArray;
                }

                listViewLivros = (ListView) findViewById(R.id.idListView);
                adapter = new ArrayAdapter<Livro>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        listLivros
                );
                listViewLivros.setAdapter(adapter);

                listViewLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String valorClicado = listViewLivros.getItemAtPosition(position).toString();
                        Toast.makeText(getApplicationContext(), valorClicado, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonAdicionar = (Button) findViewById(R.id.idButtonAdicionar);
        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = listLivros.size();
                Intent intent = new Intent(MainActivity.this, AdicionarEditarActivity.class);
                intent.putExtra( "id", id );
                intent.putExtra("request", Integer.toString(Constants.REQUEST_ADD));
                startActivityForResult(intent, Constants.REQUEST_ADD);
            }
        });

        buttonEditar = (Button) findViewById(R.id.idButtonEditar);
        textId = (EditText) findViewById(R.id.idEditTextEditar);

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 0;
                if (textId.getText().length() != 0) {
                    id = Integer.parseInt(textId.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Informe o id do livro!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, AdicionarEditarActivity.class);

                Livro livro = null;
                try {
                    livro = (Livro) listLivros.get(id);
                }
                catch(Exception e) {
                    Log.d("error", e.getMessage());
                }

                if (livro == null) {
                    Toast.makeText(getApplicationContext(), "Esse livro não existe!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent.putExtra( "id", Integer.toString(livro.getId()));
                    intent.putExtra("nome", livro.getTitulo());
                    intent.putExtra("descricao", livro.getDescricao());
                    intent.putExtra("request", Integer.toString(Constants.REQUEST_EDIT));
                    startActivityForResult(intent, Constants.REQUEST_EDIT);
                }
            }
        });

        buttonDeletar = findViewById(R.id.idButtonDeletar);
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 0;
                if (textId.getText().length() != 0) {
                    id = Integer.parseInt(textId.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Informe o id do livro!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, AdicionarEditarActivity.class);

                Livro livro = null;
                try {
                    livro = (Livro) listLivros.get(id);
                }
                catch(Exception e) {
                    Log.d("error", e.getMessage());
                }

                if (livro == null) {
                    Toast.makeText(getApplicationContext(), "Esse livro não existe!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    listLivros.remove(livro);
                    adapter.notifyDataSetChanged();

                    DatabaseReference livros = ref.child("livros");
                    livros.setValue(listLivros);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD ){
            String titulo = (String) data.getExtras().get( "titulo" );
            String descricao = (String) data.getExtras().get( "descricao" );

            Livro livro = new Livro( id, titulo, descricao );
            adapter.notifyDataSetChanged();
            listLivros.add( livro );

            DatabaseReference livros = ref.child("livros");
            livros.setValue(listLivros);

        } else if( requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD ){
            String titulo = (String) data.getExtras().get( "titulo" );
            String descricao = (String) data.getExtras().get( "descricao" );
            String idEditar = (String) data.getExtras().get( "id" );

            if (listLivros.get(Integer.parseInt(idEditar)) == null) {
                Toast.makeText( this,"Esse livro não existe!", Toast.LENGTH_SHORT).show();
            } else {
                for( Livro livro: listLivros ){
                    if( (livro.getId() == Integer.parseInt(idEditar))){
                        livro.setTitulo( titulo );
                        livro.setDescricao( descricao );
                    }
                }
            }

            adapter.notifyDataSetChanged();

            DatabaseReference livros = ref.child("livros");
            livros.setValue(listLivros);
        } else if( resultCode == Constants.RESULT_CANCEL ){
            Toast.makeText( this,"Cancelado", Toast.LENGTH_SHORT).show();
        }
    }
}