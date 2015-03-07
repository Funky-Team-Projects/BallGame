package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 07-Mar-15.
 */
class World {
  val acceleration = Pos(0,-1)

  var blocks: List[Block] = List()

  def draw(batch: Batch): Unit = blocks.foreach(_.draw(batch, 0))

  /**checks if some of the block contains given position*/
  def contains(pos: Pos): Boolean = blocks.exists(_.contains(pos))

  def add(b: Block): Unit = blocks = b :: blocks

}
