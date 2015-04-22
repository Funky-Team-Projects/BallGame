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

  val outer = new Bagel
  val middle = new Bagel
  val inner = new Bagel

  var speed: Pos = Pos(10,0)

  center = Pos(30, 60)
  size = Pos(120, 120)

  middle.scale = Pos(0.5f, 0.5f)
  inner.scale = Pos(0.25f, 0.25f)

  outer.circle.color = new Color(1, 0, 0, 1)
  middle.circle.color = new Color(0, 1, 0, 1)
  inner.circle.color = new Color(0, 0, 1, 1)

  def shiftCalc(first: Bagel, second: Bagel, size: Pos): Pos ={
    Pos(((first.scale*first.thick - second.scale)*size/2.0f).x,0)
  }

  middle.shift = shiftCalc(outer, middle, size)
  inner.shift = shiftCalc(middle, inner, size)

  override def draw(batch: Batch, parentAlpha: Float) = {
    /**Drawing Psychedelic circle*/
    outer.draw(batch, center, size)

    middle.draw(batch, center, size)

    inner.draw(batch, center + middle.shift, size)
  }

  def colorMatcher(color: Color): Unit = {
    def dist(c1: Color, c2: Color) = {
      (c1.r - c2.r)*(c1.r - c2.r) + (c1.g - c2.g)*(c1.g - c2.g) + (c1.b - c2.b)*(c1.b - c2.b)
    }

    val out = dist(outer.circle.color, color)
    val mid = dist(middle.circle.color, color)
    val inn = dist(inner.circle.color, color)

    if (inn < mid && inn < out) {
      val temp = outer.circle.color
      outer.circle.color = inner.circle.color
      inner.circle.color = temp
    }
    if (mid < inn && mid < out) {
      val temp = outer.circle.color
      outer.circle.color = middle.circle.color
      middle.circle.color = temp
    }

  }

  def stickTo(pix: SPixel):Unit ={
    position = pix.position - size
    colorMatcher(pix.color)
    speed = Pos(speed.x,0)
  }

  def groundTo(pix: SPixel):Unit ={
    position = pix.position.addX(-size.x)
    colorMatcher(pix.color)
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


