package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends SImage {

  center = Pos(30, 60)
  size = Pos(120, 120)

  val accel: Pos = Pos(1, 0)
  val standart: Pos = Pos(25,0)
  val step: Pos = Pos(0.68f, 0.68f)
  var way: Int = 1

  var rotat: Float = 0
  var colorDiff: Float = 0
  var speed: Pos = standart

  var blockColor: Option[Color] = None


  var lastBagel: TextureDrawable = new TextureDrawable()
  var bagels: List[TextureDrawable] = List(lastBagel)

  add(new TextureDrawable())
  add(new TextureDrawable())
  add(new TextureDrawable())
  add(new TextureDrawable())

  def shiftCalc(first: TextureDrawable, second: TextureDrawable): Float = {
    (first.scale.x*(1.0f-0.2f) - second.scale.x)*size.x/2.0f
  }

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    /**Drawing Psychedelic circle*/

    bagels.foldLeft((Pos(0,0), 1.0f)) { ( t , bagel) => {
        bagel.drawRotated(batch, center + t._1, size, t._2*rotat)
        rotat += (if (glued) 1f else -1f)
        if (rotat == 360) rotat = 1
       (t._1 + bagel.shift, -t._2*1.35f)
      }
    }
  }
  private def colorMatch(c1: Color, c2: Color): Boolean = {
    (c1.r == c2.r) && (c1.g == c2.g) && (c1.b == c2.b)
  }

  def colorMatcher(color: Color): Unit = {

     blockColor = Some(color)
    if (bagels.head.color != color) {
      val coloredBagel: Option[TextureDrawable] = bagels.find(b => colorMatch(b.color,color))
      coloredBagel match {
        case Some(b) => {
          val color = bagels.head.color
          //  val thick = outer.thick

          bagels.head.color = b.color
          //  outer.thick = inner.thick

          b.color = color

          //  inner.thick = thick
        }
        case _ => {
          val optionBagel: Option[TextureDrawable] = bagels.find(b => colorMatch(b.color, Color.WHITE))
          optionBagel match {
            case Some(bagel) => {
              bagel.color = bagels.head.color
              bagels.head.color = new Color(color)

              /*   val thick = bagel.thick
          bagel.thick = bagels.head.thick
          bagels.head.thick = thick*/
            }
            case None =>


           /*   if (!alone)
                colorMatcher(color)*/


          }
        }
      }
     // colorDiff = Math.sqrt(colorDiff).toFloat / 50
    }
  }


  def kill(): Unit = {
    if (!alone) {
      val b = bagels.head
      bagels.head.color = lastBagel.color
      lastBagel.color = b.color

      val bagelTail = bagels.reverse.tail
      lastBagel = bagelTail.head
      bagels = bagelTail.reverse

    }
  }

  def kill(color: Color): Unit = {
    if (!alone) {
      val bagelTail = bagels.reverse.tail
      lastBagel = bagelTail.head
      lastBagel.color = color
      bagels = bagelTail.reverse

    }

  }

  def thickChange(b: Bagel, t: Float): Unit = {
    b.thick += Pos(t, t) / b.scale / size

  }

  def shiftChange(b: Bagel, s: Float): Unit = {
    val r = b.shift.mod

    b.shift *= (r+s)/r

  }

  def wallTo(pix: SPixel): Unit = {
    position = pix.position - size*Pos(1f, 0.5f)
    colorMatcher(pix.color)
    speed = Pos(0,speed.y)
  }

  def stickTo(pix: SPixel):Unit = {
    position = pix.position - size
    colorMatcher(pix.color)
    speed = Pos(speed.x ,0)
  }

  def groundTo(pix: SPixel):Unit ={
    position = pix.position addX -size.x
    colorMatcher(pix.color)
    speed = Pos(speed.x,0)
  }


  override def act(delta: Float) = {
   bagels.tail.foldLeft((if (glued) 1 else -1)*3.0f){
      (r, bagel) => {
        bagel.rotate(r)
        -r*1.5f
      }
    }
  }

  def gift(box: PresentBox) = {
    box.present match {
      case b: TextureDrawable => add(b)
      case _ =>
    }
  }

  def add(b: TextureDrawable) = {
    b.scale = lastBagel.scale*step
    b.shift = Pos(shiftCalc(lastBagel, b),0)
    lastBagel = b
    bagels = bagels ++ List(b)
  }

  def alone: Boolean = bagels.tail.isEmpty

  def fade: Unit = {
    val colorStep = 0.05f
    if (grounded || glued) {
      if (blockColor.isDefined)
        if (colorMatch(blockColor.get, bagels.head.color)) {
          if (bagels.head.color.a < 1)
            bagels.head.color.a += colorStep
        }
        else {
          bagels.head.color.a -= colorStep
          if (bagels.head.color.a <= 0) {
            bagels.head.color.a = 1
            kill
          }
        }
    }

  }

  def move: Unit = {

  //  if (!World.contains(position + speed addX size.x) && !World.contains(position + speed + size)) jump

    center += speed*(if (walled) Pos(0,1) else Pos(1,1))
    fade

   /* val b = World.findIntersection
    if (b.isDefined) {

    }*/
    /*
    if (b.isDefined) {
      val calcG = (Pos(b.get.position.x + b.get.size.x / 2, position.y  + size.y) - (position addX size.x / 2)).mod
      val calcT = (Pos(b.get.position.x + b.get.size.x / 2, position.y) - (position + size * Pos(0.5f, 1f))).mod
      val calcR = (Pos(b.get.position.x, position.y + size.y / 2) - (position + size * Pos(1, 0.5f))).mod

      if ((calcG <= calcT) && (calcG <= calcR)) groundTo(SPixel(Pos(b.get.position.x + b.get.size.x / 2, position.y  + size.y), b.get.bColor, 1))
      else if ((calcT < calcG) && (calcT <= calcR)) stickTo(SPixel(Pos(b.get.position.x + b.get.size.x / 2, position.y), b.get.bColor, 2))
      else if ((calcR < calcT) && (calcR <= calcG)) wallTo(SPixel(Pos(b.get.position.x, position.y + size.y/2), b.get.bColor, 3))
    }*/
   // if (b.isDefined) wallTo(SPixel(Pos(b.get.position.x, position.y + size.y/2), b.get.bColor, 3))


  //  if (!walled) speed = Pos(standart.x, speed.y)
    speed += (if (speed.x < standart.x && !walled) accel else Pos(0,0))
    if (!grounded && !glued) speed += World.acceleration//*(if (walled) way else 1)
   // else if (glued && walled) speed += World.acceleration


  }

  def jump: Unit = if (grounded && !glued) {
    speed += Pos(0, 25)
    if (walled) way = -1
    blockColor = None
  } else if (glued && !grounded) {
    speed += Pos(0, -1)
    if (walled) way = 1
  }

  def containsMiddle(pos: Pos): Boolean = {
    (center - pos).mod < size.x/2
   // (position.x <= pos.x && pos.x <= position.x + size.x) && (position.y < pos.y && pos.y < position.y + size.y)
  }

  def inFly: Boolean = !grounded && !walled && !glued
  def grounded: Boolean = World.contains(position addX size.x - 1) || World.contains(position)
  def walled: Boolean = /*World.contains(position + size*Pos(1,0.5f))*/  World.intersects
  def glued: Boolean = World.contains(position + size addX - 1) || World.contains(position addY size.y)
  def blocked: Boolean = grounded && glued && walled


}


