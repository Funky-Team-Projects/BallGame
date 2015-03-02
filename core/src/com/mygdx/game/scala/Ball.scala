package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends Actor {                                   /**So you coold understand*/
  val img = new Texture("ball.png")                          /**Initializing your imagaes*/
  val red = new Texture("red.png")
  val green = new Texture("green.png")
  val pink = new Texture("pink.png")

  override def draw(batch: Batch, parentAlpha: Float) = {

    batch.draw(red, 0, 0, 60, 60)                           /**Drawing Red circle at given position (0,0) and given size (60,60)*/
    batch.draw(green, 0, 0, 50, 50)                         /**Drawing Green circle at given position (0,0) and given size (50,50)*/
    batch.draw(pink, 0, 0, 40, 40)                          /**Drawing Pink circle at given position (0,0) and given size (40,40)*/
                                                            /**Size parameters are somehow connected to the texture size*/
  }
}
