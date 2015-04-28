package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends SImage {

  val outer = new Bagel
  val middle = new Bagel
  val inner = new Bagel

  var colorDiff: Float = 0
  var speed: Pos = Pos(0,-10)

  center = Pos(130, 5060)
  size = Pos(120, 120)

  middle.scale = Pos(0.5f, 0.5f)
  inner.scale = Pos(0.25f, 0.25f)

  outer.circle.color = new Color(1, 0, 0, 1)
  middle.circle.color = new Color(0, 1, 0, 1)
  inner.circle.color = new Color(0, 0, 1, 1)

  def shiftCalc(first: Bagel, second: Bagel, size: Pos): Float ={
    (first.scale.x*first.thick.x - second.scale.x)*size.x/2.0f
  }

  middle.shift = Pos(shiftCalc(outer, middle, size),0)
  inner.shift = Pos(shiftCalc(middle, inner, size),0)

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

    shiftChange(middle, -middle.shift.mod - shiftCalc(outer, middle, size))
    shiftChange(inner, -inner.shift.mod - shiftCalc(middle, inner, size))


    colorDiff = Math.sqrt(colorDiff).toFloat / 50
  }

  def thickChange(b: Bagel, t: Float): Unit = {
    b.thick += Pos(t, t) / b.scale / size

  }

  def shiftChange(b: Bagel, s: Float): Unit = {
    val r = b.shift.mod

    b.shift *= (r+s)/r

  }

  def stickTo(pix: SPixel):Unit ={
    position = pix.position addX -size.x
    colorMatcher(pix.color)
    speed = Pos(0,speed.y)
  }

  def groundTo(pix: SPixel):Unit ={
    position = pix.position
    colorMatcher(pix.color)
    speed = Pos(0, speed.y)
  }

  override def act(delta: Float) = {
    middle.rotate(-2)
    inner.rotate(4)
  }

  def move: Unit = {
    if (!World.contains(position + speed) && !World.contains(position + speed addX size.x)) jump
    center += speed
    if (grounded || glued) {
      if (outer.thick.x > 0) thickChange(outer, -Pos(colorDiff, colorDiff).mod * 2)
      if (middle.thick.x < 0.2f) {
        thickChange(middle, Pos(0.001f, 0.001f).mod*2)
        shiftChange(inner, -0.001f)
      }
      if (inner.thick.x < 0.2f) thickChange(inner, 0.001f)
      shiftChange(middle, colorDiff)
    }
    if (!grounded && !glued) speed += World.acceleration

  }

  def jump: Unit = if (grounded) {
    speed += Pos(-10, 0)
  } else if (glued) speed += Pos(10, 0)


  def grounded: Boolean = World.contains(center addX size.x/2)
  def glued: Boolean = World.contains(center addX -size.x/2)


}


