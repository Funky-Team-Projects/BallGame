package com.mygdx.game.scala

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{GL20, Color}
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 13-Apr-15.
 */
class Level(val respawn: Pos) {

  def this() = {
    this(new Pos(0,0))
  }

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
    blocks.foreach(_.draw(batch))
    presents.foreach(_.draw(batch))
    batch.end()
  }

}
