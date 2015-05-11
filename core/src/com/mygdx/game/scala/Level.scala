package com.mygdx.game.scala

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Texture, GL20, Color}
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 13-Apr-15.
 */


abstract class Level {

  val respawn: Pos

  val back = new TextureDrawable("lava.jpg")

  var background: Color = new Color(0, 0, 0, 1)

  var presents: List[PresentBox] = List()

  def add(p: PresentBox): Unit = presents = p :: presents
  def remove(p: PresentBox): Unit = presents = presents.filter(pr => pr != p)

}

class StandartLevel(val respawn: Pos) extends Level {


  def this() = {
    this(new Pos(0,0))
  }

  var blocks: List[Block] = List()

  def add(b: Block): Unit = blocks = b :: blocks

  def draw(batch: Batch): Unit ={

    Gdx.gl.glClearColor(background.r, background.g, background.b, background.r)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.begin()
    back.draw(batch, Pos(World.viewport.getCamera.position.x*0.98f, World.viewport.getCamera.position.y*0.9f) - Parameters.SIZE*Pos(0.85f,1.2f), Parameters.SIZE*Pos(1.4f, 1.9f))
    blocks.foreach(_.draw(batch))
    presents.foreach(_.draw(batch))
    batch.end()
  }

}


class EndlessLevel(val respawn: Pos) extends Level {

  var colors: List[Color] = List(Color.TEAL, Color.MAROON, Color.PURPLE, Color.DARK_GRAY)

  def randomColor: Color = {
    val c: Int = (Math.random()*colors.length).toInt
    colors.drop(c).head
  }

  var t: Int = 0
  lazy val blockStream: Stream[Block] = new Block(new Pos(0,0), new Pos(1220, 25), Color.TEAL) #::  generate(600)
  def blocks = blockStream.drop(t).take(15).toList
  def generate(sh: Float): Stream[Block] = {
    new Block(Pos(Math.random()*600.0 + sh, Math.random()*300.0f - 350.0f), Pos(Math.random()*600 + 400, 25), randomColor)#::
    new Block(Pos(Math.random()*600.0 + 300 + sh, Math.random()*300.0f), Pos(Math.random()*600 + 400, 25), randomColor)#::
    new Block(Pos(Math.random()*600.0 + 600 + sh, Math.random()*300.0f + 350.0f), Pos(Math.random()*600 + 400, 25), randomColor)#:: generate(sh + 1100)
  }

  def inc: Unit = {
    t += 1
  }

  def draw(batch: Batch): Unit ={

    Gdx.gl.glClearColor(background.r, background.g, background.b, background.r)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.begin()
    back.draw(batch, Pos(World.viewport.getCamera.position.x*0.99f, World.viewport.getCamera.position.y*0.5f) - Parameters.SIZE*Pos(0.85f,1.2f), Parameters.SIZE*Pos(1.8f, 2.1f))
    blocks.foreach(_.draw(batch))
    presents.foreach(_.draw(batch))
    batch.end()
  }
}
