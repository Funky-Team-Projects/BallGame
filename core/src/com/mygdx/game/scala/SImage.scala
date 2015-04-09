package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * Created by Denis on 08-Mar-15.
 */
class SImage extends Image {
  def center: Pos = position + size / 2
  def center_=(pos: Pos): Unit = {  position = pos - size / 2 }

  def position: Pos = Pos(getX, getY)
  def position_=(pos: Pos): Unit = {
    setX(pos.x)
    setY(pos.y)
  }

  def size: Pos = Pos(getWidth, getHeight)
  def size_=(pos: Pos): Unit = {
    setWidth(pos.x)
    setHeight(pos.y)
  }

  def draw(batch: Batch): Unit = draw(batch, 0)

}
