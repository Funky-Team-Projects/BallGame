package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 22-Apr-15.
 */
class Bagel {

  val circle = new TextureDrawable("ball.png")
  val back = new TextureDrawable("ball.png")

  var thick = Pos(0.2f,0.2f)

  circle.color = new Color(1,1,1,1)
  back.color = new Color(0,0,0,1)

  def scale: Pos = circle.scale
  def scale_=(pos: Pos): Unit = {
    circle.scale = pos
    back.scale = pos
  }

  def shift: Pos = circle.shift
  def shift_=(pos: Pos): Unit = {
    circle.shift = pos
    back.shift = pos
  }

  def rotate(degree: Float): Unit = {
    circle.rotate(degree)
    back.rotate(degree)
  }




  def draw(batch: Batch, position: Pos, size: Pos): Unit = {
    circle.drawC(batch, position, size)
    back.drawC(batch, position, size*(Pos(1,1) - thick))

  }

}
