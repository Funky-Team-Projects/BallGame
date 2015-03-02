package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends Actor {
  val img = new Texture("ball.png")
  val red = new Texture("red.png")
  val green = new Texture("green.png")
  val pink = new Texture("pink.png")

  override def draw(batch: Batch, parentAlpha: Float) = {
    batch.draw(red, 0, 0)
    batch.draw(green, 0, 0)
    batch.draw(pink, 0, 0)
  }
}
