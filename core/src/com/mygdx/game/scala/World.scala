package com.mygdx.game.scala


import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Batch}
import math.round

/**
 * Created by Denis on 07-Mar-15.
 */
object World {

  val acceleration = Pos(0, -1)
  val hero = new Ball
 // val respawn = Pos(45,200)

  var level: Level = new Level

  def level(level: Level): Unit = {
    this.level = level
  }

  def draw(batch: Batch): Unit = {
    level.draw(batch)
  }

  def move = {
    if (hero.position.y < 0) {
      hero.speed = Pos(10, 0)
      hero.center = level.respawn
    }
    hero.move
    presentCheck
    pixelChooser(pixelFinder)
  }

  def presentCheck: Unit = {
    val present = level.presents.find(_.contains(hero.position + Pos(1, 0.5f)*hero.size))
    present match {
      case Some(p) => {
        level.remove(p)
        hero.gift(p)
      }
      case _ =>
    }
  }

  def pixelFinder: (Option[SPixel], Option[SPixel]) = (findP(hero.position addX hero.size.x, hero.speed), findP(hero.position + hero.size, hero.speed, false))

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
    } yield SPixel(start + change*t1, b.bColor)

    if (change.y != 0)
      met.headOption
    else None

  }
  /******************************************/

}
