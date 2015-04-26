package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends SImage {

  /**Initializing your images*/

  val outer = new Bagel
  val middle = new Bagel
  val inner = new Bagel

  var colorDiff: Float = 0
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

    colorDiff = out
    if (inn < mid && inn < out) {
      colorDiff = inn

      val color = outer.circle.color
      val thick = outer.thick

      outer.circle.color = inner.circle.color
      outer.thick = inner.thick

      inner.circle.color = color
      inner.thick = thick
    }
    if (mid < inn && mid < out) {
      colorDiff = mid

      val color = outer.circle.color
      val thick = outer.thick

      outer.circle.color = middle.circle.color
      outer.thick = middle.thick

      middle.circle.color = color
      middle.thick = thick
    }

    middle.shift = shiftCalc(outer, middle, size)
    inner.shift = shiftCalc(middle, inner, size)
    colorDiff = Math.sqrt(colorDiff).toFloat / 100
  }

  def thickChange(t: Float): Unit = {
    outer.thick += Pos(t, t) / outer.scale / size

  }

  def shiftChange(s: Float): Unit = {
    val r = middle.shift.mod

    middle.shift *= (r+s)/r

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
    if (grounded || glued) {
      thickChange(-Pos(colorDiff, colorDiff).mod * 2)
      shiftChange(colorDiff)
    }
    if (!grounded && !glued) speed += World.acceleration
  }

  def jump: Unit = if (grounded) {
    speed += Pos(0, 20)
  }

  def grounded: Boolean = World.contains(position addX size.x/2)
  def glued: Boolean = World.contains(position + size*Pos(0.5f, 1))


}


