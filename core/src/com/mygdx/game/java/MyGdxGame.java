package com.mygdx.game.java;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.scala.Ball;
import com.mygdx.game.scala.BallInputProcessor;
import com.mygdx.game.scala.Block;
import com.mygdx.game.scala.Level;
import com.mygdx.game.scala.Pos;
import com.mygdx.game.scala.PresentBox;
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
    Level level;

	@Override
	public void create () {
		batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        ball = World.hero();

        level = new Level(new Pos(90, 120));
        block = new Block(new Pos(0,0), new Pos(1220, 40),new Color(0, 0.5f, 0.7f, 1));
        level.add(block);
        level.add(new Block(new Pos(1440,0), new Pos(1220, 40),new Color(0.2f, 0.8f, 0, 1)));
        level.add(new Block(new Pos(1640,350), new Pos(1220, 40),new Color(0.6f, 0, 0.4f, 1)));
        level.add(new Block(new Pos(2840, 0), new Pos(1220, 40),new Color(0.51f, 0.49f, 0f, 1)));
        level.add(new Block(new Pos(3840, 250), new Pos(1220, 40),new Color(0.49f, 0.51f, 0f, 1)));
        World.level(level);

        level.add(new PresentBox(new Pos(1940,100), new Pos(100, 100)));
        level.add(new PresentBox(new Pos(4000,150), new Pos(100, 100)));

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

     //   Gdx.gl.glClearColor(0, 0, 0, 1);
	//	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(ball.position().x() + WIDTH/3,ball.position().y(), 0);

        World.draw(batch);
        World.move();
        stage.getViewport().setCamera(camera);
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());  /**Calls method acts to all actors*/

	}

    @Override
    public void dispose() {
        stage.dispose();                        /**Just a good thing to do*/
    }
}
