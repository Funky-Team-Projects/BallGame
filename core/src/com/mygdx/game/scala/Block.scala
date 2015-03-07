package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by Denis on 06-Mar-15.
 */
class Block extends Actor {

  val sprite: SpriteWrapper = SpriteWrapper("square.png")


  def size: Pos = sprite.size
  def size_=(pos: Pos): Unit = sprite.size = pos

  def position: Pos = sprite.position
  def position_=(pos: Pos): Unit = sprite.position = pos

  def bColor: Color = sprite.sprite.getColor
  def bColor_=(color: Color) = sprite.sprite.setColor(color)

  def top = (position + size).y
  def bottom = position.y

  override def draw(batch: Batch, parentalAlpha: Float ) {
    sprite.draw(batch)
  }

  /**check if this block contains given position*/
  def contains(pos: Pos): Boolean =
    (pos.x >= sprite.position.x && pos.x <= sprite.position.x + size.x) &&
    (pos.y >= sprite.position.y && pos.y <= sprite.position.y + size.y)


}
