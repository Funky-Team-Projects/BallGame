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
  var jump = false

  //var speed: Pos = Pos(0,0)

  val red = SpriteWrapper("white.png")
  red.sprite.setSize(120, 120)
  red.sprite.setColor(1, 0, 0, 1)
  val green = SpriteWrapper("white.png")
  green.sprite.setColor(0, 1, 0, 1)
  green.sprite.setSize(70, 70)
  green.center = red.center + Pos(4, 4)
  val pink = SpriteWrapper("white.png")
  pink.sprite.setSize(40, 40)
  pink.sprite.setColor(0,0,1,1)
  pink.center = green.center + Pos(-3, -3)

  val v: Vector2 = new Vector2((green.center - red.center).x, (green.center - red.center).y)
  val v2: Vector2 = new Vector2((pink.center - green.center).x, (pink.center - green.center).y)

  override def draw(batch: Batch, parentAlpha: Float) = {
    implicit var ballBatch = batch

    /**Drawing Psychedelic circle*/

    red.draw(batch)

    green.draw(batch)

    pink.draw(batch)

  }

  override def act(delta: Float) = {

    if (jump) {
      red.center = red.center addY 10
      green.center = green.center addY 10
      pink.center = pink.center addY 10
    }
    /**Green modifications*/
    green.rotate(red.center, 2)
    /**Pink modifications*/
    pink.rotate(red.center, 2)
    pink.rotate(green.center, -4)

  }

}


