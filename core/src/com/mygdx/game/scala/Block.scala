package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * Created by Denis on 06-Mar-15.
 */
class Block extends SImage {

  val drawable: TextureDrawable = new TextureDrawable("square.png")


  def bColor: Color = drawable.color
  def bColor_=(color: Color) = drawable.color = color

  def top = (position + size).y
  def bottom = position.y

  override def draw(batch: Batch, parentalAlpha: Float ) {

    drawable.draw(batch, position, size)
  }

  /**check if this block contains given position*/
  def contains(pos: Pos): Boolean =
    (pos.x >= position.x && pos.x <= position.x + size.x) &&
    (pos.y >= position.y && pos.y <= position.y + size.y)


}
