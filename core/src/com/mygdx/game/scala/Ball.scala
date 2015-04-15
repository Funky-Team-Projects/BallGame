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

  val outer = new TextureDrawable("ball.png")
  val outerBack = new TextureDrawable("ball.png")

  val middle = new TextureDrawable("ball.png")
  val middleBack = new TextureDrawable("ball.png")

  val inner = new TextureDrawable("ball.png")
  val innerBack = new TextureDrawable("ball.png")

  outerBack.scale = Pos(0.8f, 0.8f)
  middleBack.scale = Pos(0.4f, 0.4f)
  innerBack.scale = Pos(0.2f, 0.2f)

  middle.scale = Pos(0.5f, 0.5f)
  inner.scale = Pos(0.25f, 0.25f)

  outerBack.color = new Color(0, 0, 0, 1)
  middleBack.color = new Color(0, 0, 0, 1)
  innerBack.color = new Color(0, 0, 0, 1)

  outer.color = new Color(1, 0, 0, 1f)
  middle.color = new Color(0, 1, 0, 1f)
  inner.color = new Color(0, 0, 1, 1)


  def shift(first: TextureDrawable, second: TextureDrawable, size: Pos): Pos ={
    Pos(((first.scale - second.scale)*size/2.0f).x,0)
  }

  middle.shift = shift(outerBack, middle, size)
  inner.shift = shift(middleBack, inner, size)

  middleBack.shift = middle.shift
  innerBack.shift = inner.shift


  override def draw(batch: Batch, parentAlpha: Float) = {
    /**Drawing Psychedelic circle*/
    outer.drawC(batch, center, size)
    outerBack.drawC(batch, center, size)

    middle.drawC(batch, center, size)
    middleBack.drawC(batch, center, size)

    inner.drawC(batch, center + middle.shift, size)
    innerBack.drawC(batch, center + middle.shift, size)

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
    middleBack.rotate(-2)
    inner.rotate(4)
    innerBack.rotate(4)
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


