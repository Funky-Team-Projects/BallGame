package com.mygdx.game.scala

import com.badlogic.gdx.math.Vector2

/**
 * Created by Denis on 02-Mar-15.
 */

/**Additional class to ease work with positions*/
case class Pos(x: Float, y: Float) {

  def +(that: Pos): Pos = Pos(this.x + that.x, this.y + that.y)
  def -(that: Pos): Pos = Pos(this.x - that.x, this.y - that.y)
  def /(num: Float): Pos = Pos(this.x / num, this.y / num)
  def addX(x: Float) = this + Pos(x, 0)
  def addY(y: Float) = this + Pos(0, y)

}

object Pos{
  def apply(vector: Vector2): Pos = apply(vector.x, vector.y)
}
