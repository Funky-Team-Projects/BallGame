package com.mygdx.game.scala

import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by Denis on 04-May-15.
 */
trait SDrawable {
  def draw(batch: Batch, position: Pos, size: Pos)

}
