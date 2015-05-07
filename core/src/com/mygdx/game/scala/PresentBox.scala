package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 04-May-15.
 */
case class PresentBox(position: Pos, size: Pos) {

  val present: SDrawable = new TextureDrawable("ring.jpg")
  def draw(batch: Batch): Unit = present.draw(batch, position, size)
  def contains(pos: Pos): Boolean =
    (position.x <= pos.x && pos.x <= position.x + size.x) &&
    (position.y <= pos.y && pos.y <= position.y + size.y)
}
