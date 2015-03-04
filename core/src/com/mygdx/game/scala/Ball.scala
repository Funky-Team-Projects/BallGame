package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends Actor {

  /**Initializing your images*/

  val red = SpriteWrapper("ball.png")
  val green = SpriteWrapper("green.png")
  green.center = red.center + Pos(15, 15)

  green.origin = red.center // - (green.center - green.position)
  val pink = SpriteWrapper("pink.png")
//  pink.center = green.center + Pos(-5,-5)

  override def draw(batch: Batch, parentAlpha: Float) = {
    implicit var ballBatch = batch

    /**Drawing Psychedelic circle*/
    red.draw
    green.draw
    pink.draw

  }

  override def act(delta: Float) = {
    green.sprite.rotate(1)

  }
}

class Balll extends Actor {                                   /**So you could understand*/

val elems = List(new SpriteWrapper("ball.png"),
  new SpriteWrapper("green.png"),
  new SpriteWrapper("pink.png"))

  override def draw(batch: Batch, parentAlpha: Float) = {
    implicit var ballBatch = batch

    /**Drawing Psychedelic circle*/
    elems.foreach( _.draw)

  }
}
