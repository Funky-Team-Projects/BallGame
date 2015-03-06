package com.mygdx.game.java;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.scala.Ball;
import com.mygdx.game.scala.BallInputProcessor;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    Stage stage;
    Viewport viewport;
    Ball ball;
    BallInputProcessor inputProcessor;

	@Override
	public void create () {
		batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        viewport = new StretchViewport(900, 700); /**Creates a viewport with the responding size*/
        ball = new Ball();

        stage = new Stage(viewport, batch);       /**Creates stage on our viewport with our batch*/
        stage.addActor(ball);                     /**add actor to the stage*/

        inputProcessor = new BallInputProcessor(ball);
        Gdx.input.setInputProcessor(inputProcessor);



	}

	@Override
	public void render () {

        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	//	batch.begin();
        stage.draw();                            /**Draws all actors*/
        stage.act(Gdx.graphics.getDeltaTime());  /**Calls method acts to all actors*/
	//	batch.draw(img, 0, 0);
	//	batch.end();
	}

    @Override
    public void dispose() {
        stage.dispose();                        /**Just a good thing to do*/
    }
}
