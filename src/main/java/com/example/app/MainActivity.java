package com.example.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import java.io.IOException;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nome;
    private TextView sobrenome;
    private TextView email;
    private TextView endereco;
    private TextView cidade;
    private TextView estado;
    private TextView username;
    private TextView senha;
    private TextView nascimento;
    private TextView telefone;
    private TextView nomeUser;
    private ImageView foto;
    private ProgressDialog load;
    private ImageView  profilePicture;





    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetJson download = new GetJson();
        Usuario usuario = new Usuario();

        nome = (TextView)findViewById(R.id.textView5);
        sobrenome = (TextView)findViewById(R.id.textView11);
        email = (TextView)findViewById(R.id.textView8);
        endereco = (TextView)findViewById(R.id.textView7);
        cidade = (TextView)findViewById(R.id.textView4);
        estado = (TextView)findViewById(R.id.textView3);
        username = (TextView)findViewById(R.id.textView2);
        senha = (TextView)findViewById(R.id.textView10);
        nascimento = (TextView)findViewById(R.id.textView9);
        telefone = (TextView)findViewById(R.id.textView12);
        foto = (ImageView)findViewById(R.id.image);
        profilePicture = (ImageView)findViewById(R.id.imageProfile);
        nomeUser = (TextView)findViewById(R.id.textView22);

        //Chama Async Task
        download.execute();
        usuario.execute();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        GetJson download = new GetJson();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.refresh) {
            download.execute();
        } else if (id == R.id.sair) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GetJson extends AsyncTask<Void, Void, Pessoa> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Pessoa doInBackground(Void... params) {
            Perfil perfil = new Perfil();
            return perfil.getInformacao("https://randomuser.me/api/");
        }

        @Override
        protected void onPostExecute(Pessoa pessoa){
            nome.setText(pessoa.getNome().substring(0,1).toUpperCase()+pessoa.getNome().substring(1));
            sobrenome.setText(pessoa.getSobrenome().substring(0,1).toUpperCase()+pessoa.getSobrenome().substring(1));
            email.setText(pessoa.getEmail());
            endereco.setText(pessoa.getEndereco());
            cidade.setText(pessoa.getCidade().substring(0,1).toUpperCase()+pessoa.getCidade().substring(1));
            estado.setText(pessoa.getEstado());
            username.setText(pessoa.getUsername());
            senha.setText(pessoa.getSenha());
            nascimento.setText(pessoa.getNascimento());
            telefone.setText(pessoa.getTelefone());
            foto.setImageBitmap(pessoa.getFoto());
            load.dismiss();
        }
    }
    private class Usuario extends AsyncTask<Void, Void, User> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected User doInBackground(Void... params) {
            User user = new User();
            user.pic("100001907883529", user);
            return user;
        }
        @Override
        protected void onPostExecute(User user) {
            profilePicture.setImageBitmap(user.getFoto());
            nomeUser.setText("Eduardo Lima");
    }
    }
}
