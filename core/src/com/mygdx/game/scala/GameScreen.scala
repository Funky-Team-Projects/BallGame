package com.mygdx.game.scala

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Table, Label}
import com.badlogic.gdx.utils.viewport.{FitViewport, FillViewport, StretchViewport, Viewport}
import com.badlogic.gdx.{Gdx, Screen}


/**
 * Created by Denis on 11-May-15.
 */
class GameScreen extends Screen {

  val batch = new SpriteBatch()
  val ball = World.hero

  val label = new Label("0 meters", Parameters.uiSkin)
  label.setColor(1,1,1,1)
  label.setPosition(Parameters.WIDTH - label.getText.length*50, Parameters.HEIGHT - 100)
  label.setFontScale(3f)

  val resultLabel = new Label("0 meters", Parameters.uiSkin)
  resultLabel.setColor(1,1,1,1)
  resultLabel.setPosition(Parameters.WIDTH - resultLabel.getText.length*50, Parameters.HEIGHT - 100)
  resultLabel.setFontScale(3f)

  val table = new Table()
  table.add(resultLabel).row()
  table.add(label)

  Parameters.camera.position.set(ball.position.x,ball.position.y,0)
  //World.viewport(Parameters.viewport)

  val stage = new Stage(Parameters.viewport, batch)       /**Creates stage on our viewport with our batch*/
  stage.addActor(ball)                    /**add actor to the stage*/

  val inputProcessor = new BallInputProcessor(ball)
  stage.getViewport.setCamera(Parameters.camera)

  val music = Gdx.audio.newMusic(Gdx.files.internal("Diablo 2 - Tristram Theme.mp3"))
  music.setLooping(true)
  music.setVolume(0.6f)

  override def show(): Unit = {
    World.restart
    Gdx.input.setInputProcessor(inputProcessor)
    music.play()

  }

  override def hide(): Unit = {
    music.stop()
  }

  override def resize(width: Int, height: Int): Unit = {

  }

  override def dispose(): Unit = {
    stage.dispose()
    batch.dispose()
    music.dispose()

  }

  override def pause(): Unit = {}

  override def render(delta: Float): Unit = {
    World.move
    Parameters.camera.position.set(ball.position.x + Parameters.WIDTH / 3, ball.position.y, 0)
    World.draw(batch)
    stage.draw()
    stage.act(Gdx.graphics.getDeltaTime)

    resultLabel.setText(World.bestResult.toString ++ " meters")
    resultLabel.setWidth(Parameters.WIDTH - resultLabel.getText.length*100)
    label.setText((World.hero.position.x/250).toInt.toString ++ " meters")
    label.setWidth(Parameters.WIDTH - label.getText.length*100)
    //label.setPosition(hero.position.x + Parameters.WIDTH/2, hero.position.y + Parameters.HEIGHT/2)
    table.setPosition(World.hero.position.x - 200, World.hero.position.y + Parameters.HEIGHT/2 - 50)
    batch.begin()
    table.draw(batch, 1)
    // hero.draw(batch)
    batch.end()
  }

  override def resume(): Unit = {}
}
