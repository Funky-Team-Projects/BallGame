package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends SImage {

  center = Pos(30, 60)
  size = Pos(120, 120)


  val standart: Pos = Pos(20,0)

  var rotat: Float = 0
  var speed: Pos = standart

  var blockColor: Option[Color] = None

  var lastBagel: TextureDrawable = new TextureDrawable(Color.PURPLE)
  var bagels: List[TextureDrawable] = List(lastBagel)

  reset
  def reset: Unit = {
    lastBagel = new TextureDrawable(Color.PURPLE)
    bagels = List(lastBagel)
    add(new TextureDrawable(Color.TEAL))
   // add(new TextureDrawable(Color.ORANGE))
    add(new TextureDrawable(Color.MAROON))
  //  add(new TextureDrawable(new Color(0.2f, 0.5f, 0, 1)))
  }

  def shiftCalc(first: TextureDrawable, second: TextureDrawable): Float = {
    (first.scale.x*0.8f - second.scale.x)*size.x/2.0f
  }

    override def draw(batch: Batch, parentAlpha: Float): Unit = {
    /**Drawing Psychedelic circle*/
    bagels.foldLeft((Pos(0,0), 1.0f)) { ( t , bagel) => {
        bagel.drawRotated(batch, center + t._1, size, t._2*rotat)
        rotat += (if (glued) 1f else -1f)
       (t._1 + bagel.shift, -t._2*1.35f)
      }
    }
  }
  private def colorMatch(c1: Color, c2: Color): Boolean = {
    (c1.r == c2.r) && (c1.g == c2.g) && (c1.b == c2.b)
  }

  def colorMatcher(color: Color): Unit = {

    blockColor = Some(color)
    if (color != Color.DARK_GRAY) {
      if (bagels.head.color != color) {
        val coloredBagel: Option[TextureDrawable] = bagels.find(b => colorMatch(b.color, color))
        coloredBagel match {
          case Some(b) => {
            val color = bagels.head.color

            bagels.head.color = b.color

            b.color = color


          }
          case _ => {
            val optionBagel: Option[TextureDrawable] = bagels.find(b => colorMatch(b.color, Color.WHITE))
            optionBagel match {
              case Some(bagel) => {
                bagel.color = bagels.head.color
                bagels.head.color = new Color(color)
              }

              case None =>
            }
          }
        }
      }
    }
  }


  def kill(): Unit = {
    if (!alone) {
      val b = bagels.head
      World.toGray(b.color)
      bagels.head.color = lastBagel.color
      lastBagel.color = b.color

      val bagelTail = bagels.reverse.tail
      lastBagel = bagelTail.head
      bagels = bagelTail.reverse

    }
    if (alone && bagels.head.color.a <= 0) World.restart
  }
/*
  def kill(color: Color): Unit = {
    if (!alone) {
      val bagelTail = bagels.reverse.tail
      lastBagel = bagelTail.head
      lastBagel.color = color
      bagels = bagelTail.reverse

    }

  }
*/
  def wallTo(pix: SPixel): Unit = {
    position = pix.position - size*Pos(1f, 0.5f)
    colorMatcher(pix.color)
    speed = Pos(0,speed.y)
  }

  def stickTo(pix: SPixel): Unit = {
    position = pix.position - size
    colorMatcher(pix.color)
    speed = Pos(speed.x ,0)
  }

  def groundTo(pix: SPixel): Unit ={
    position = pix.position addX -size.x
    colorMatcher(pix.color)
    speed = Pos(speed.x,0)
  }


  override def act(delta: Float): Unit = {
   bagels.tail.foldLeft((if (glued) 1 else -1)*3.0f){
      (r, bagel) => {
        bagel.rotate(r)
        -r*1.5f
      }
    }
  }
/*
  def gift(box: PresentBox) = {
    box.present match {
      case b: TextureDrawable => add(b)
      case _ =>
    }
  }
*/
  def add(b: TextureDrawable): Unit = {
    val step: Pos = Pos(0.6f, 0.6f)
    b.scale = lastBagel.scale*step
    b.shift = Pos(shiftCalc(lastBagel, b),0)
    lastBagel = b
    bagels = bagels ++ List(b)
  }

  def alone: Boolean = bagels.tail.isEmpty

  def fade: Unit = {
    val colorStep = 0.025f
    if (grounded || glued) {
      if (blockColor.isDefined)
        if (colorMatch(blockColor.get, bagels.head.color)) {
          if (bagels.head.color.a < 1)
            bagels.head.color.a += colorStep
        }
        else {
          bagels.head.color.a -= colorStep
          if (bagels.head.color.a <= 0) {
            kill
            bagels.head.color.a = 1
          }
        }
    }

  }

  def smartShift(pos: Pos): Unit = {
    val distY: Float = pos.y - center.y
    val pointX: Float = center.x + Math.sqrt((size.x / 2) * (size.x / 2) - distY * distY).toFloat
    center = Pos(center.x - (pointX - pos.x), center.y)

  }

  def move: Unit = {

    center += speed*(if (walled) Pos(0,1) else Pos(1,1))
    fade

    val b = World.findIntersection
    if (b.isDefined) {
      if (b.get.position.y > center.y && (b.get.position.y != position.y + size.y)) {
        smartShift(b.get.position)
      } else if (b.get.position.y + b.get.size.y < center.y && (b.get.position.y + b.get.size.y != position.y)) {
        smartShift(b.get.position addY b.get.size.y)
      } else {
        wallTo(SPixel(Pos(b.get.position.x, position.y + size.y / 2), b.get.bColor, 3))
      }
    }

    if (speed.x < standart.x && !walled)
      speed = speed addX 1
    if (!grounded && !glued) speed += World.acceleration//*(if (walled) way else 1)


  }

  def jump: Unit = if (grounded && !glued) {
    speed += Pos(0, 25)
    blockColor = None
  } else if (glued && !grounded) {
    speed += Pos(0, -1)
    blockColor = None
  }

  def containsMiddle(pos: Pos): Boolean = {
    (center - pos).mod < size.x/2
  }

  def inFly: Boolean = !grounded && !walled && !glued
  def grounded: Boolean = World.contains(position addX size.x/2) || World.contains(position)
  def walled: Boolean = /*World.contains(position + size*Pos(1,0.5f))*/  World.intersects
  def glued: Boolean = World.contains(position + size addX -size.x/2) || World.contains(position addY size.y)
  def blocked: Boolean = grounded && glued && walled


}


