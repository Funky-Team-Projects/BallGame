package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * Created by Denis on 02-Mar-15.
 */


class SpriteWrapper {
  val sprite: Sprite = new Sprite()

  def center: Pos = Pos(sprite.getX() + sprite.getWidth() / 2.0f, sprite.getY() + sprite.getHeight() / 2.0f)
  def center_=(pos: Pos): Unit = sprite.setCenter(pos.x, pos.y)

}
