package com.mygdx.game.scala



import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.{FillViewport, FitViewport, StretchViewport}
import com.badlogic.gdx.{Preferences, Gdx, Screen}
import com.badlogic.gdx.scenes.scene2d.ui.Skin

/**
 * Created by Denis on 05-May-15.
 */
object Parameters {
  final val WIDTH: Float = 2000
  final val HEIGHT: Float = 1150
  final val SIZE = Pos(WIDTH, HEIGHT)
  final val RATIO = WIDTH/HEIGHT
  final val uiSkin = new Skin(Gdx.files.internal("uiskin.json"))

  val preferences: Preferences = Gdx.app.getPreferences("ball-prefs")

  val camera = new OrthographicCamera
  val viewport = new FitViewport(Parameters.WIDTH, Parameters.HEIGHT, Parameters.camera)

  uiSkin.addRegions(new TextureAtlas("uiskin.atlas"))

  val mainMenu = new MainMenu
  val gameScreen = new GameScreen

  var now: Screen = mainMenu


  def dispose = {
    mainMenu.dispose()
    gameScreen.dispose()
    uiSkin.dispose()
  }
}
