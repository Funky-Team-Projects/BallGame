package com.mygdx.game.scala

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Texture, GL20, Color}
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 13-Apr-15.
 */
class Level(val respawn: Pos) {

  def this() = {
    this(new Pos(0,0))
  }
  val back = new TextureDrawable("map.jpg")
  var background: Color = new Color(0, 0, 0, 1)
  var blocks: List[Block] = List()
  var presents: List[PresentBox] = List()

  def add(b: Block): Unit = blocks = b :: blocks
  def add(p: PresentBox): Unit = presents = p :: presents
  def remove(p: PresentBox): Unit = presents = presents.filter(pr => pr != p)

  def draw(batch: Batch): Unit ={

    Gdx.gl.glClearColor(background.r, background.g, background.b, background.r)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.begin()
    back.draw(batch, Pos(World.viewport.getCamera.position.x, World.viewport.getCamera.position.y*0.5f) - Parameters.SIZE*Pos(0.7f,1.2f), Parameters.SIZE*Pos(0.8f, 2.5f))
    blocks.foreach(_.draw(batch))
    presents.foreach(_.draw(batch))
    batch.end()
  }

}
