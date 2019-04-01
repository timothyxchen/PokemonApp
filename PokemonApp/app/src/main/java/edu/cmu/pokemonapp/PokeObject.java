package edu.cmu.pokemonapp;

import android.graphics.Bitmap;

public class PokeObject {

    private String name;
    private Bitmap picture;

    public PokeObject(String name, Bitmap picture){
        this.name = name;
        this.picture = picture;
    }

    public String getName(){
        return this.name;
    }

    public Bitmap getPicture() {
        return this.picture;
    }


}
