package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends Actor {

  /**Initializing your images*/

  val red = SpriteWrapper("ball.png")
  val green = SpriteWrapper("green.png")
  green.center = red.center + Pos(4, 4)
  val pink = SpriteWrapper("pink.png")
  pink.center = green.center + Pos(-6, -6)

  val v: Vector2 = new Vector2((green.center - red.center).x, (green.center - red.center).y)
  val v2: Vector2 = new Vector2((pink.center - green.center).x, (pink.center - green.center).y)

  override def draw(batch: Batch, parentAlpha: Float) = {
    implicit var ballBatch = batch

    /**Drawing Psychedelic circle*/
    red.draw
    green.draw
    pink.draw

  }

  override def act(delta: Float) = {
    v.rotate(1)
    green.center = red.center + new Pos(v)
    v.set((green.center - red.center).x, (green.center - red.center).y)
    v2.rotate(-2)
    pink.center = green.center + new Pos(v2)
    v2.set((pink.center - green.center).x, (pink.center - green.center).y)

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
