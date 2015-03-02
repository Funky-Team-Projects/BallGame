package com.mygdx.game.scala

import org.scalatest.FunSuite


/**
 * Created by Denis on 02-Mar-15.
 */
class SpriteWrapperTest extends FunSuite {
  val sprite: SpriteWrapper = new SpriteWrapper()
  sprite.center = Pos(1,1)

  test("Center must work") {
    assert(sprite.center === Pos(1,1), "center doesn't return position")
  }


}
