package com.example.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class User {
    private Bitmap foto;
    private String nome;

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto){
        this.foto = foto;
    }

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome=nome;}

    public User pic(String u, User user){
     try{
         String url = "https://graph.facebook.com/" + u + "/picture?type=large";
        user.setFoto(baixarProfile(url));
         user.setNome(nome(u));
        return user;
    }catch (Exception e){
        e.printStackTrace();
        return null;
    }
    }

    public static Bitmap baixarProfile(String url) {
        try{
            URL endereco;
            InputStream inputStream;
            Bitmap imagem;
            endereco = new URL(url);
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String nome(String userID){
        String url = "https://graph.facebook.com/" + userID + "?name";
        String nome;
        try{
            URL endereco;
            InputStream inputStream;
            endereco = new URL(url);
            inputStream = endereco.openStream();
            nome = inputStream.toString();
            inputStream.close();
            return nome;

        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
