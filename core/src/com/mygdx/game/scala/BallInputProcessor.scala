package com.mygdx.game.scala

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputProcessor

/**
 * Created by Denis on 05-Mar-15.
 */
class BallInputProcessor(ball: Ball) extends InputProcessor{

  override def keyDown(keycode: Int): Boolean = {
    keycode match {
      case Keys.UP => ball.jump
    }
    true
  }

  override def keyTyped(character: Char): Boolean = false

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean = {
    /*keycode match {
      case Keys.UP => ball.jump
    }*/
    true
  }

  override def scrolled(amount: Int): Boolean = false

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
}
