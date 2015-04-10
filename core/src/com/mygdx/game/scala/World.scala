package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Batch}
import math.round
import scala.collection.immutable.IndexedSeq

/**
 * Created by Denis on 07-Mar-15.
 */
object World {

  val acceleration = Pos(0, -1)
  val hero = new Ball
  val respawn = Pos(45,200)

  var blocks: List[Block] = List()

  def draw(batch: Batch): Unit = {
    batch.begin()
    blocks.foreach(_.draw(batch))
    batch.end()
  }

  def move = {
    hero.move
    collisionCheck
    if (hero.position.y < 0)
      hero.center = respawn
  }

  def collisionCheck(): Unit =  {
    val posCheck: (Option[Pos], Option[Pos]) = (findP(hero.position addX hero.size.x, hero.speed), findP(hero.position + hero.size, hero.speed, false))
    posCheck match {
      case (Some(pos),None) => hero groundTo pos
      case (None, Some(pos)) => hero stickTo pos
      case (Some(pos1), Some(pos2)) if (pos1 - hero.center).mod < (pos2 - hero.center).mod => hero groundTo pos1
      case (Some(pos1), Some(pos2)) if (pos1 - hero.center).mod >= (pos2 - hero.center).mod => hero stickTo pos2
      case (None, None) =>
    }
  }

  /** checks if some of the block contains given position */
  def contains(pos: Pos): Boolean = blocks.exists(_.contains(pos))

  /**3 ways of finding the ground*/
  def find(pos: Pos): Option[Block] = blocks.find(_.contains(pos))

  def findL(start: Pos, change: Pos) = {
    def step(t: Float): Pos = start + change * t
    def met = for (
      t <- 1 until round(change.mod.toFloat);
      count = change.mod.toFloat / 10f;
      result <- find(step(1 / count))
    ) yield result
    met.headOption

  }

  def findP(start: Pos, change: Pos, normal: Boolean = true) = {
    def met = for{
      b <- blocks
      additional = if (normal) b.size.y else 0
      t1: Float = ((b.position.y + additional) - start.y) / change.y
      t2: Float = (start.x - b.position.x + change.x*t1)/b.size.x
      if (t1 <= 1 && t1 >= 0) && (t2 <= 1 && t2 >= 0)
    } yield start + change*t1

    if (change.y != 0)
      met.headOption
    else None

  }
  /******************************************/

  def add(b: Block): Unit = blocks = b :: blocks

}
