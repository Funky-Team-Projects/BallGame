package com.mygdx.game.java;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.scala.Ball;
import com.mygdx.game.scala.BallInputProcessor;
import com.mygdx.game.scala.Block;
import com.mygdx.game.scala.Pos;
import com.mygdx.game.scala.World;

public class MyGdxGame extends ApplicationAdapter {

    final int WIDTH = 3200;
    final int HEIGHT = 1900;

	SpriteBatch batch;
	Texture img;
    Stage stage;
    Viewport viewport;
    Ball ball;
    Block block;
    BallInputProcessor inputProcessor;
    OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        ball = World.hero();

        block = new Block(new Pos(0,0), new Pos(1220,40));
        World.add(block);
        World.add(new Block(new Pos(1320,40), new Pos(1220,40)));
        World.add(new Block(new Pos(1320,320), new Pos(1220,40)));

        camera = new OrthographicCamera();
        camera.position.set(ball.getX(),ball.getY(),0);
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        stage = new Stage(viewport, batch);       /**Creates stage on our viewport with our batch*/
        stage.addActor(ball);                     /**add actor to the stage*/

        inputProcessor = new BallInputProcessor(ball);
        Gdx.input.setInputProcessor(inputProcessor);



	}

	@Override
	public void render () {

        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(ball.getX() + WIDTH/3,ball.center().y(),0);

        World.draw(batch);
        stage.getViewport().setCamera(camera);
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());  /**Calls method acts to all actors*/

	}

    @Override
    public void dispose() {
        stage.dispose();                        /**Just a good thing to do*/
    }
}
