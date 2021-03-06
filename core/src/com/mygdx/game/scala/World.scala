package com.mygdx.game.scala


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.{TextureAtlas, SpriteBatch, Batch}
import com.badlogic.gdx.scenes.scene2d.ui.{Table, Skin, Label}
import com.badlogic.gdx.utils.viewport.{FitViewport, FillViewport, StretchViewport, Viewport}
import math.round


/**
 * Created by Denis on 07-Mar-15.
 */
object World {

  val acceleration: Pos = Pos(0, -1)
  val hero: Ball = new Ball

  var bestResult: Int = Parameters.preferences.getInteger("score", 0)

  var level: EndlessLevel = new EndlessLevel(Pos(90, 120))

  def level(level: EndlessLevel): Unit = {
    this.level = level
  }

  def restart: Unit = {
    if (hero.position.x/250 > bestResult) bestResult = (hero.position.x/250).toInt
    Parameters.preferences.putInteger("score", bestResult)
    Parameters.preferences.flush()

    level = new EndlessLevel(Pos(90, 120))

    hero.speed = hero.standart
    hero.center = level.respawn
    level.t = 0
    level.colors = List(Color.TEAL, Color.MAROON, Color.PURPLE, Color.DARK_GRAY)
    hero.reset


  }

  private def colorMatch(c1: Color, c2: Color): Boolean = {
    (c1.r == c2.r) && (c1.g == c2.g) && (c1.b == c2.b)
  }

  def toGray(color: Color): Unit = {
    level.colors = level.colors.filter(c => !colorMatch(c, color))
    level.blocks.foreach(b => if (colorMatch(b.bColor, color)) b.bColor = Color.DARK_GRAY)
  }


  def ridOf: Unit = {
    val b = level.blocks.drop(4).head
    if (hero.position.x > b.position.x + b.size.x) level.inc
  }

  def draw(batch: Batch): Unit = {
    level.draw(batch)

  }

  def move: Unit = {
    if (hero.position.y < -1000 || hero.blocked) {
      restart
    }
    hero.move
  //  presentCheck
    neoPixelChooser(closestPixelFinder)
    ridOf
  }


  def closestPixelFinder: Option[SPixel] = {
    val pixList = findBetter(hero.position, hero.size, hero.speed)
    if (pixList.nonEmpty) {
      val lastOne = pixList.tail.foldLeft(pixList.head)((p1, p2) => if ((p2.position - hero.center).mod < (p1.position - hero.center).mod) p2 else p1 )
      Some(lastOne)
    } else None
  }


  def neoPixelChooser(pixel: Option[SPixel]): Unit = {
    pixel match {
      case Some(p) if p.modificator == 2 => hero.stickTo(p)
    //  case Some(p) if p.modificator == 3 => hero.wallTo(p)
      case Some(p) if p.modificator == 1 => hero.groundTo(p)
      case _ =>
    }

  }


  /** checks if some of the block contains given position */
  def intersects: Boolean = !level.blocks.forall(b => !hero.containsMiddle(b.position addY b.size.y) && !hero.containsMiddle(b.position) /*!hero.containsMiddle(b.position + b.size)*/)
  def findIntersection: Option[Block] = level.blocks.find(b => hero.containsMiddle(b.position) || hero.containsMiddle(b.position addY b.size.y))

  def contains(pos: Pos): Boolean = level.blocks.exists(_.contains(pos))

  /**3 ways of finding the ground*/
  def find(pos: Pos): Option[Block] = level.blocks.find(_.contains(pos))

  def findL(start: Pos, change: Pos) = {
    def step(t: Float): Pos = start + change * t
    def met = for (
      t <- 1 until round(change.mod);
      count = change.mod / 10f;
      result <- find(step(1 / count))
    ) yield result
    met.headOption

  }


  def findBetter(start: Pos, radius: Pos, change: Pos): List[SPixel] = {
    def met: List[List[Option[SPixel]]] = {
      def checkBottom(b: Block): Option[SPixel]= {
        if (change.y != 0) {
          val t1: Float = (b.position.y + b.size.y - start.y) / change.y
          val t2: Float = ((start.x + radius.x) - b.position.x + change.x * t1) / b.size.x
          if ((t1 <= 1 && t1 >= 0) && (t2 <= 1 && t2 >= 0)) Some(SPixel(start + radius*Pos(1,0) + change*t1, b.bColor, 1)) else None
        }
        else None
      }

      def checkTop(b: Block): Option[SPixel] = {
        if (change.y != 0) {
          val t1: Float = (b.position.y - (start.y + radius.y)) / change.y
          val t2: Float = ((start.x + radius.x) - b.position.x + change.x * t1) / b.size.x
          if ((t1 <= 1 && t1 >= 0) && (t2 <= 1 && t2 >= 0)) Some(SPixel(start + radius + change*t1, b.bColor, 2)) else None
        } else None
      }

      for{
        b <- level.blocks
        // additional = if (normal) b.size.y else 0

        t1 = checkBottom(b)
        t2 = checkTop(b)
     //   t3 = checkWall(b)
        t = t1 :: t2 :: List()

      } yield t//SPixel(start + change*t1, b.bColor)
    }
    met.flatten.flatten
  }

  /******************************************/



}
