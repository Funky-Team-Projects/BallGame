package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 06-Mar-15.
 */
class Block extends SImage {

  val drawable: TextureDrawable = new TextureDrawable("square.png")

  def this(size: Pos) = {
    this()
    this.size = size
  }

  def this(width: Float, height: Float) = {
    this(Pos(width, height))
  }

  def this(position: Pos, size: Pos) = {
    this(size)
    this.position = position
  }

  def this(position: Pos, size: Pos, color: Color){
    this(position, size)
    bColor = color
  }


  def bColor: Color = drawable.color
  def bColor_=(color: Color): Unit = drawable.color = color

  //def top = position.y + size.y
  //def bottom = position.y

  override def draw(batch: Batch, parentalAlpha: Float): Unit = {

    drawable.draw(batch, position, size)
  }

  /**check if this block contains given position*/
  def contains(pos: Pos): Boolean =
    (position.x <= pos.x && pos.x <= position.x + size.x) &&
    (position.y <= pos.y && pos.y <= position.y + size.y)


}
