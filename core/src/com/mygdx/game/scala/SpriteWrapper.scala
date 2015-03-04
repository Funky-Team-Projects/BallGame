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

  def center: Pos = Pos(position.x + sprite.getWidth() / 2.0f, position.y + sprite.getHeight() / 2.0f)
  def center_=(pos: Pos): Unit = sprite.setCenter(pos.x, pos.y)

  def position: Pos = Pos(sprite.getX(), sprite.getY())
  def position_=(pos: Pos): Unit = sprite.setPosition(pos.x, pos.y)

  def origin: Pos = Pos(sprite.getOriginX(), sprite.getOriginY())
  def origin_=(pos: Pos): Unit = sprite.setOrigin(pos.x, pos.y)

  def draw(implicit batch: Batch) = sprite.draw(batch)


}

object SpriteWrapper{
  def apply() = new SpriteWrapper()
  def apply(texture: Texture) = new SpriteWrapper(texture)
  def apply(texturePath: String) = new SpriteWrapper(texturePath)
}
