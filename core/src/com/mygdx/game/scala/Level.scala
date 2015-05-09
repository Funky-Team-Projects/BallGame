package com.mygdx.game.scala

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Texture, GL20, Color}
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 13-Apr-15.
 */


abstract class Level {

  val respawn: Pos

  val back = new TextureDrawable("lava2.jpg")
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
    back.draw(batch, Pos(World.viewport.getCamera.position.x*0.98f, World.viewport.getCamera.position.y*0.9f) - Parameters.SIZE*Pos(0.85f,1.2f), Parameters.SIZE*Pos(1.8f, 2.1f))
    blocks.foreach(_.draw(batch))
    presents.foreach(_.draw(batch))
    batch.end()
  }

}


class EndlessLevel(val respawn: Pos) extends Level {

  def randomColor: Color = {
    val c: Int = (Math.random()*7).toInt
    c match {
      case 1 => Color.TEAL
      case 2 => Color.MAROON
      case 3 => Color.ORANGE
      case 4 => Color.PURPLE
      case 5 => new Color(0.2f, 0.5f, 0, 1)
      case 6 => Color.NAVY
      case _ => Color.MAGENTA
    }
  }

  var t: Int = 10
  lazy val blockStream: Stream[Block] = new Block(new Pos(0,0), new Pos(1220, 40), Color.TEAL) #:: generate(0)
  def blocks = blockStream.take(t).toList
  //def blocks(bounds: Pos): List[Block] = blockStream.filter(b => bounds.x <= b.position.x && b.position.x <= bounds.y).take(10).toList
  def generate(sh: Float): Stream[Block] = {
    new Block(Pos(Math.random()*1000.0 + 200.0f + sh, Math.random()*500.0f - 200.0f), Pos(Math.random()*700 + 500, 40), randomColor) #:: generate(sh + 900)
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
