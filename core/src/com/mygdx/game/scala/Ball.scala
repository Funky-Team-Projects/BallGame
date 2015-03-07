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


  var grounded = true
  var speed: Pos = Pos(0,0)

  val outer = SpriteWrapper("white.png")
  outer.position = Pos(20,20)
  outer.size = Pos(120,120)
  outer.sprite.setColor(1, 0, 0, 1)

  val middle = SpriteWrapper("white.png")
  middle.sprite.setColor(0, 1, 0, 1)
  middle.size = Pos(70, 70)
  middle.center = outer.center + Pos(4, 4)

  val inner = SpriteWrapper("white.png")
  inner.size = Pos(40, 40)
  inner.sprite.setColor(0,0,1,1)
  inner.center = middle.center + Pos(-3, -3)


  override def draw(batch: Batch, parentAlpha: Float) = {
    implicit var ballBatch = batch

    /**Drawing Psychedelic circle*/

    outer.draw(batch)
    middle.draw(batch)
    inner.draw(batch)

  }

  override def act(delta: Float) = {


    move
    /**Green modifications*/
    middle.rotate(outer.center, 2)
    /**Pink modifications*/
    inner.rotate(outer.center, 2)
    inner.rotate(middle.center, -4)
    groundCheck

  }

  def move: Unit = {
    outer.center += speed
    middle.center += speed
    inner.center += speed

    if (!grounded) speed -= Pos(0, 1)
  }

  def jump: Unit = if (grounded) {
    speed += Pos(0, 20)
    grounded = false
  }
  def groundCheck = if (outer.position.y <= 20) {
    grounded = true
    inner.position = inner.position addY (21 - outer.position.y)
    middle.position = middle.position addY (21 - outer.position.y)
    outer.position = Pos(outer.position.x, 21)
    speed = speed addY -speed.y
  }




}


