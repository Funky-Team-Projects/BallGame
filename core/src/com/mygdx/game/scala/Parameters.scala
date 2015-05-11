package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.scenes.scene2d.ui.Skin

/**
 * Created by Denis on 05-May-15.
 */
object Parameters {
  final val WIDTH: Float = 2000
  final val HEIGHT: Float = 1150
  final val SIZE = Pos(WIDTH, HEIGHT)
  final val uiSkin = new Skin(Gdx.files.internal("uiskin.json"))
  uiSkin.addRegions(new TextureAtlas("uiskin.atlas"))

  val mainMenu = new MainMenu
  val gameScreen = new GameScreen

  var now: Screen = mainMenu
}
