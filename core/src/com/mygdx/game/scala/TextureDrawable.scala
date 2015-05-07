package com.mygdx.game.scala

import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{Batch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Created by Denis on 07-Mar-15.
 */
class TextureDrawable extends TextureRegionDrawable with SDrawable{

  var scale: Pos = Pos(1,1)
  var shift: Pos = Pos(0,0)
  var color: Color = new Color(1, 1, 1, 1)

  def this(region: TextureRegion) {
    this()
    setRegion(region)
  }
  def this(texture: Texture) = this(new TextureRegion(texture))
  def this(texturePath: String) = this(new Texture(texturePath))

  def draw(batch: Batch, position: Pos, size: Pos): Unit = {
    batch.setColor(color)
    super.draw(batch, position.x + shift.x, position.y + shift.y, size.x*scale.x, size.y*scale.y)
  }

  def drawC(batch: Batch, position: Pos, size: Pos): Unit ={
    draw(batch, position - size*scale/2, size)
  }

  def drawRotated(batch: Batch, position: Pos, size: Pos, rotation: Float): Unit = {
    batch.setColor(color)
    super.draw(batch, position.x + shift.x - size.x/2, position.y + shift.y - size.y/2, size.x/2, size.y/2, size.x, size.y, scale.x, scale.x, rotation)
  }

  def rotate(degree: Float): Unit = {
    shift = shift.rotate(degree)
  }
}
