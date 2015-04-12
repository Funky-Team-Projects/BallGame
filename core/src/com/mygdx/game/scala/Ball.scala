package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import sun.org.mozilla.javascript.internal.ast.Block

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends SImage {

  /**Initializing your images*/

  center = Pos(30, 60)
  size = Pos(120, 120)

  var speed: Pos = Pos(10,0)

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

  def colorMatcher(color: Color): Unit = {
    def dist(c1: Color, c2: Color) = {
      (c1.r - c2.r)*(c1.r - c2.r) + (c1.g - c2.g)*(c1.g - c2.g) + (c1.b - c2.b)*(c1.b - c2.b)
    }

    val out = dist(outer.color, color)
    val mid = dist(middle.color, color)
    val inn = dist(inner.color, color)

    if (inn < mid && inn < out) {
      val temp = outer.color
      outer.color = inner.color
      inner.color = temp
    }
    if (mid < inn && mid < out) {
      val temp = outer.color
      outer.color = middle.color
      middle.color = temp
    }

  }

  def stickTo(pos: Pos):Unit ={
    position = pos - size
    speed = Pos(speed.x,0)
  }

  def groundTo(pos: Pos):Unit ={
    position = pos.addX(-size.x)
    speed = Pos(speed.x,0)
  }

  override def act(delta: Float) = {
    middle.rotate(-2)
    inner.rotate(4)
  }

  def move: Unit = {
    center += speed
    if (!grounded && !glued) speed += World.acceleration
  }

  def jump: Unit = if (grounded) {
    speed += Pos(0, 20)
  }

  def grounded: Boolean = World.contains(position addX size.x)
  def glued: Boolean = World.contains(position + size)


}


