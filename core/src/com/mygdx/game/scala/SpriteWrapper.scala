package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}

/**
 * Created by Denis on 02-Mar-15.
 */


class SpriteWrapper(val sprite: Sprite) {

  def this(){
    this(new Sprite())
  }

  def this(texture: Texture) {
    this(new Sprite(texture))
  }

  def this(texturePath: String){
    this(new Texture(texturePath))
  }

  def center: Pos = Pos(sprite.getX() + sprite.getWidth() / 2.0f, sprite.getY() + sprite.getHeight() / 2.0f)
  def center_=(pos: Pos): Unit = sprite.setCenter(pos.x, pos.y)

  def draw(implicit batch: Batch) = sprite.draw(batch)


}

object SpriteWrapper{
  def apply() = new SpriteWrapper()
  def apply(texture: Texture) = new SpriteWrapper(texture)
  def apply(texturePath: String) = new SpriteWrapper(texturePath)
}
