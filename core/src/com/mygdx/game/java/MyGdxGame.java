package com.mygdx.game.java;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.scala.Parameters;

public class MyGdxGame extends ApplicationAdapter {

	@Override
	public void create () {


        Parameters.now().show();
        //screen.show();
	}

	@Override
	public void render () {
        Parameters.now().render(Gdx.graphics.getDeltaTime());

    }
    @Override
    public void dispose() {
        /**Just a good thing to do*/
 //       stage.dispose();

    }
}
