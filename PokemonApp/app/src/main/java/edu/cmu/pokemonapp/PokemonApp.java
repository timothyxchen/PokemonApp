package edu.cmu.pokemonapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class PokemonApp extends AppCompatActivity {
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final PokemonApp ma = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText)findViewById(R.id.searchPoke)).getText().toString();
                GetPoke gp = new GetPoke();
                gp.search(searchTerm, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /*
     * This is called by the GetPicture object when the picture is ready.  This allows for passing back the Bitmap picture for updating the ImageView
     */
    public void pokeReady(PokeObject pokemon) {
        TextView errorView = findViewById(R.id.pokeError);
        ImageView pictureView = null;
        TextView resultView =null;
        if(pokemon == null ){
            errorView.setText("Sorry, no pokemon is found");
        }else{
            count++;
            if(count%3 ==1){
                 pictureView = (ImageView)findViewById(R.id.pokeImg1);
                 resultView=(TextView) findViewById(R.id.pokeTxt1);
            }else if(count%3 ==2){
                 pictureView = (ImageView)findViewById(R.id.pokeImg2);
                 resultView=(TextView) findViewById(R.id.pokeTxt2);
            }else{
                 pictureView = (ImageView)findViewById(R.id.pokeImg3);
                 resultView=(TextView) findViewById(R.id.pokeTxt3);
            }

            TextView searchView = (EditText)findViewById(R.id.searchPoke);
            Bitmap picture = pokemon.getPicture();
            String name = pokemon.getName();
            if (picture != null) {
                pictureView.setImageBitmap(picture);
                pictureView.setVisibility(View.VISIBLE);
                resultView.setText(name + " was found and is now in your team!");
            }

            searchView.setText("");
            pictureView.invalidate();
            errorView.setText("");

        }
    }
}
