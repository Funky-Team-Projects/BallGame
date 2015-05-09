package com.mygdx.game.scala


import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Batch}
import com.badlogic.gdx.utils.viewport.{StretchViewport, Viewport}
import math.round

/**
 * Created by Denis on 07-Mar-15.
 */
object World {

  val acceleration = Pos(0, -1)
  val hero = new Ball
  var viewport: Viewport = new StretchViewport(0, 0, new OrthographicCamera())
 // val respawn = Pos(45,200)

  var level: EndlessLevel = new EndlessLevel(Pos(90, 120))

  def viewport(viewport: Viewport): Unit = {
    this.viewport = viewport
  }

  /*def level(level: StandartLevel): Unit = {
    this.level = level
  }*/

  //def bounds: Pos = hero.position + Pos(-1,1)*2000

  def ridOf: Unit = {
    val b = level.blocks.head
    if (hero.position.x > b.position.x) level.inc
  }

  def draw(batch: Batch): Unit = {
    level.draw(batch)
  }

  def move = {
    if (hero.position.y < -400) {
      level = new EndlessLevel(Pos(90, 120))
      hero.speed = hero.standart
      hero.center = level.respawn
      level.t = 10
      if (hero.alone) {
        hero.add(new TextureDrawable())
        hero.add(new TextureDrawable())
        hero.add(new TextureDrawable())
        hero.add(new TextureDrawable())

      }
    }
    hero.move
    presentCheck
    neoPixelChooser(closestPixelFinder)
    ridOf
  }

  def presentCheck: Unit = {
    val present = level.presents.find(x => x.contains(hero.position + hero.size) || x.contains(hero.position addX hero.size.x))
    present match {
      case Some(p) => {
        level.remove(p)
        hero.gift(p)
      }
      case _ =>
    }
  }

  def closestPixelFinder: Option[SPixel] = {
    val pixList = findBetter(hero.position, hero.size, hero.speed)
    if (pixList.nonEmpty) {
      val lastOne = pixList.tail.foldLeft(pixList.head)((p1, p2) => if ((p2.position - hero.center).mod < (p1.position - hero.center).mod) p2 else p1 )
      Some(lastOne)
    } else None
  }


  def neoPixelChooser(pixel: Option[SPixel]) = {
    pixel match {
      case Some(p) if p.modificator == 2 => hero.stickTo(p)
      case Some(p) if p.modificator == 3 => hero.wallTo(p)
      case Some(p) if p.modificator == 1 => hero.groundTo(p)
      case None =>
    }

  }

  def pixelFinder: (Option[SPixel], Option[SPixel]) = (findP(hero.position addX hero.size.x/2, hero.speed), findP(hero.position + Pos(0.5f, 1)*hero.size, hero.speed, false))

  def pixelChooser(posVariants: (Option[SPixel], Option[SPixel])): Unit=  {
    posVariants match {
      case (Some(pix),None) => hero.groundTo(pix)
      case (None, Some(pix)) => hero.stickTo(pix)
      case (Some(pix1), Some(pix2)) if (pix1.position - hero.center).mod < (pix2.position - hero.center).mod => hero.groundTo(pix1)
      case (Some(pix1), Some(pix2)) if (pix1.position - hero.center).mod >= (pix2.position - hero.center).mod => hero.stickTo(pix2)
      case (None, None) => None
    }
  }


  /** checks if some of the block contains given position */
  def intersects: Boolean = !level.blocks.forall(b => !hero.containsMiddle(b.position addY b.size.y) && !hero.containsMiddle(b.position) /*!hero.containsMiddle(b.position + b.size)*/)

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

  def findP(start: Pos, change: Pos, normal: Boolean = true): Option[SPixel] = {
    def met = for{
      b <- level.blocks
      additional = if (normal) b.size.y else 0
      t1: Float = ((b.position.y + additional) - start.y) / change.y
      t2: Float = (start.x - b.position.x + change.x*t1)/b.size.x
      if (t1 <= 1 && t1 >= 0) && (t2 <= 1 && t2 >= 0)
    } yield SPixel(start + change*t1, b.bColor, 0)

    if (change.y != 0)
      met.headOption
    else None

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
      def checkWall(b: Block): Option[SPixel] = {
        if (change.x != 0) {
          val t1: Float = (b.position.x - (start.x + radius.x)) / change.x
          val t2: Float = ((start.y + radius.y/2.0f) - b.position.y + change.y * t1) / b.size.y
          if ((t1 <= 1 && t1 >= 0) && (t2 <= 1 && t2 >= 0)) Some(SPixel(start + radius*Pos(1, 0.5f) + change*t1, b.bColor, 3)) else None
        }
        else None
      }
      for{
        b <- level.blocks
        // additional = if (normal) b.size.y else 0

        t1 = checkBottom(b)
        t2 = checkTop(b)
        t3 = checkWall(b)
        t = t1 :: t2 :: t3 :: List()

      } yield t//SPixel(start + change*t1, b.bColor)
    }
    met.flatten.flatten
  }
  /******************************************/

}
