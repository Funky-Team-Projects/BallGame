package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends Image {

  /**Initializing your images*/

  def center: Pos = position + size / 2
  def center_=(pos: Pos): Unit = {  position = pos - size / 2 }

  def position: Pos = Pos(getX, getY)
  def position_=(pos: Pos): Unit = {
    setX(pos.x)
    setY(pos.y)
  }
  def size: Pos = Pos(getWidth, getHeight)
  def size_=(pos: Pos): Unit = {
    setWidth(pos.x)
    setHeight(pos.y)
  }

  center = Pos(20, 20)
  size = Pos(120, 120)

  var grounded = true
  var speed: Pos = Pos(0,0)

  val outer = new TextureDrawable("white.png")
  val middle = new TextureDrawable("white.png")
  val inner = new TextureDrawable("white.png")

  middle.scale = Pos(0.5f, 0.5f)
  inner.scale = Pos(0.25f, 0.25f)

  outer.color = new Color(1, 0, 0, 1)
  middle.color = new Color(0, 1, 0, 1)
  inner.color = new Color(0, 0, 1, 1)

  middle.shift = Pos(8, 8)
  inner.shift = Pos(-4, -4)


  override def draw(batch: Batch, parentAlpha: Float) = {

    /**Drawing Psychedelic circle*/

    outer.drawC(batch, center, size)
    middle.drawC(batch, center, size)
    inner.drawC(batch, center + middle.shift, size)

  }

  override def act(delta: Float) = {


    move
    /**Green modifications*/
    middle.rotate(center, 2)
    /**Pink modifications*/
    inner.rotate(center, -4)
    groundCheck

  }

  def move: Unit = {
    center += speed

    if (!grounded) speed -= Pos(0, 1)
  }

  def jump: Unit = if (grounded) {
    speed += Pos(0, 20)
    grounded = false
  }
  def groundCheck = if (position.y <= 20) {
    grounded = true
    position = position addY (21 - position.y)
    speed = speed addY -speed.y
  }




}


