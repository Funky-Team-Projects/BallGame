package com.mygdx.game.scala



import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{TextureRegion, Batch}

/**
 * Created by Denis on 02-Mar-15.
 */
class Ball extends SImage {

  center = Pos(30, 60)
  size = Pos(120, 120)

  val step: Pos = Pos(0.68f, 0.68f)

  var rotat: Float = 0
  var colorDiff: Float = 0
  var speed: Pos = Pos(10, 0)

  var lastBagel: TextureDrawable = new TextureDrawable("ring.jpg")
  var bagels: List[TextureDrawable] = List(lastBagel)

  add(new TextureDrawable("ring.jpg"))
  add(new TextureDrawable("ring.jpg"))

  def shiftCalc(first: TextureDrawable, second: TextureDrawable): Float = {
    (first.scale.x*(1.0f-0.2f) - second.scale.x)*size.x/2.0f
  }

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    /**Drawing Psychedelic circle*/

    bagels.foldLeft((Pos(0,0), 1)) { ( t , bagel) => {
        bagel.drawRotated(batch, center + t._1, size, t._2*(if(glued) rotat else -rotat))
        rotat += 0.5f
        if (rotat == 360) rotat = 1
       (t._1 + bagel.shift, -t._2)
      }
    }
  }

  def colorMatcher(color: Color): Unit = {
    /*
    def dist(c1: Color, c2: Color) = {
      (c1.r - c2.r)*(c1.r - c2.r) + (c1.g - c2.g)*(c1.g - c2.g) + (c1.b - c2.b)*(c1.b - c2.b)
    }

    val out = dist(outer.circle.color, color)
    val mid = dist(middle.circle.color, color)
    val inn = dist(inner.circle.color, color)

    colorDiff = out
    if (inn < mid && inn < out) {
      colorDiff = inn

      val color = outer.circle.color
      val thick = outer.thick

      outer.circle.color = inner.circle.color
      outer.thick = inner.thick

      inner.circle.color = color
      inner.thick = thick
    }
    if (mid < inn && mid < out) {
      colorDiff = mid

      val color = outer.circle.color
      val thick = outer.thick

      outer.circle.color = middle.circle.color
      outer.thick = middle.thick

      middle.circle.color = color
      middle.thick = thick
    }

    shiftChange(middle, -middle.shift.mod - shiftCalc(outer, middle, size))
    shiftChange(inner, -inner.shift.mod - shiftCalc(middle, inner, size))
*/
    if (bagels.head.color != color) {
      val coloredBagel: Option[TextureDrawable] = bagels.find(b => b.color == color)
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
          val optionBagel: Option[TextureDrawable] = bagels.find(b => b.color == Color.WHITE)
          optionBagel match {
            case Some(bagel) => {
              bagel.color = bagels.head.color
              bagels.head.color = color

              /*   val thick = bagel.thick
          bagel.thick = bagels.head.thick
          bagels.head.thick = thick*/
            }
            case None => {
              kill(color)
              if (!alone)
                colorMatcher(color)
            }
          }
        }
      }
     // colorDiff = Math.sqrt(colorDiff).toFloat / 50
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

  def stickTo(pix: SPixel):Unit ={
    position = pix.position - size
    colorMatcher(pix.color)
    speed = Pos(speed.x,0)
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

  def move: Unit = {
  //  if (!World.contains(position + speed addX size.x) && !World.contains(position + speed + size)) jump
    center += speed
/*
    if (outer.thick.x < 0.2f) {
      thickChange(outer, Pos(0.001f, 0.001f).mod*2)
      shiftChange(middle, -0.001f)
    }
    if (middle.thick.x < 0.2f) {
      thickChange(middle, Pos(0.001f, 0.001f).mod*2)
      shiftChange(inner, -0.001f)
    }
    if (inner.thick.x < 0.2f) thickChange(inner, 0.001f)
    if (grounded || glued) {
      if (outer.thick.x > 0) thickChange(outer, -Pos(colorDiff, colorDiff).mod * 2)

      shiftChange(middle, colorDiff)
    }
    */
    if (!grounded && !glued) speed += World.acceleration

  }

  def jump: Unit = if (grounded) {
    speed += Pos(0, 25)
  } else if (glued) speed += Pos(0, -1)



  def grounded: Boolean = World.contains(position addX size.x/2)
  def glued: Boolean = World.contains(position + Pos(0.5f, 1)*size)


}


