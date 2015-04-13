package com.mygdx.game.scala

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Batch}
import math.round
import scala.collection.immutable.IndexedSeq

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
    hero.move
    pixelChooser(pixelFinder)
    if (hero.position.y < 0)
      hero.center = level.respawn
  }

  def pixelFinder: (Option[SPixel], Option[SPixel]) = (findP(hero.position addX hero.size.x, hero.speed), findP(hero.position + hero.size, hero.speed, false))
/*
  def posGetter(pix: (Option[SPixel], Option[SPixel])): (Option[Pos], Option[Pos]) = {
    pix match{
      case (Some(SPixel(pos1, col1)), Some(SPixel(pos2, col2))) => (Some(pos1), Some(pos2))
      case (Some(SPixel(pos1, col1)), None) => (Some(pos1), None)
      case (None, Some(SPixel(pos1, col1))) => (None, Some(pos1))
      case (None, None) => (None, None)
    }
  }
*/
  /*def distributor(): Unit = {
    val info: Option[(SPixel, Int)] = pixelChooser(pixelFinder)
    info match {
      case Some((pix, 1)) => {
        hero.groundTo(pix.position)
        hero.colorMatcher(pix.color)
      }
      case Some((pix, 2)) => {
        hero.stickTo(pix.position)
        hero.colorMatcher(pix.color)
      }
      case None =>
    }

  }
*/

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
      t <- 1 until round(change.mod.toFloat);
      count = change.mod.toFloat / 10f;
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
