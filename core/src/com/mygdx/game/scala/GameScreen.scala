package com.mygdx.game.scala

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.{FitViewport, FillViewport, StretchViewport, Viewport}
import com.badlogic.gdx.{Gdx, Screen}


/**
 * Created by Denis on 11-May-15.
 */
class GameScreen extends Screen {

  val batch = new SpriteBatch()
  val ball = World.hero

  val camera = new OrthographicCamera()
  camera.position.set(ball.position.x,ball.position.y,0)
  val viewport = new StretchViewport(Parameters.WIDTH, Parameters.HEIGHT, camera)
  World.viewport(viewport)

  val stage = new Stage(viewport, batch)       /**Creates stage on our viewport with our batch*/
  stage.addActor(ball)                    /**add actor to the stage*/

  val inputProcessor = new BallInputProcessor(ball)
//  Gdx.input.setInputProcessor(inputProcessor)
  stage.getViewport.setCamera(camera)

  val music = Gdx.audio.newMusic(Gdx.files.internal("Diablo 2 - Tristram Theme.mp3"))

  override def show(): Unit = {
    Gdx.input.setInputProcessor(inputProcessor)
    music.setLooping(true)
    music.setVolume(0f)
    music.play()
 //   World.draw(batch)
  //  stage.draw
  }

  override def hide(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def dispose(): Unit = {
    stage.dispose()
    batch.dispose()
    music.dispose()
  }

  override def pause(): Unit = {}

  override def render(delta: Float): Unit = {
    World.move
    camera.position.set(ball.position.x + Parameters.WIDTH / 3, ball.position.y, 0)
    World.draw(batch)
    stage.draw()
    stage.act(Gdx.graphics.getDeltaTime)
  }

  override def resume(): Unit = {}
}
