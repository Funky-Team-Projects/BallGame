package com.mygdx.game.scala


import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Table, Skin, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.{Screen, Gdx}
import com.badlogic.gdx.graphics.{OrthographicCamera, GL20}
import com.mygdx.game.java.MyGdxGame

/**
 * Created by Denis on 11-May-15.
 */
class MainMenu extends Screen {
  private val stage: Stage = new Stage()
  private val table: Table = new Table()

 // private val skin: Skin = new Skin(Gdx.files.internal("uiSkin2.json"))
//  skin.addRegions(new TextureAtlas("uiskin2.atlas"))

  private val buttonPlay: TextButton = new TextButton("Play", Parameters.uiSkin)
  private val buttonExit: TextButton = new TextButton("Exit", Parameters.uiSkin)


  buttonPlay.addListener(new ClickListener {
    override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
      Parameters.now = Parameters.gameScreen
      Parameters.gameScreen.show()
      Parameters.mainMenu.hide()
    }
  })

  buttonExit.addListener(new ClickListener {
    override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
      Gdx.app.exit()
     // android.os.Process.killProcess(android.os.Process.myPid());
    }
  })

  table.add(buttonPlay).width(300).height(100).row()
  table.add(buttonExit).width(300).height(100).row()

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.act()
    stage.draw()
  }


  override def resize(width: Int, height: Int): Unit = {

   // table.setSize(width, height)
  /*  buttonPlay.setSize(100,100)
    buttonPlay.setScale(1)
    buttonPlay.setTransform(true)*/
  }


  override def show(): Unit = {
    //The elements are displayed in the order you add them.
    //The first appear on top, the last at the bottom.


    table.setFillParent(true)
    stage.addActor(table)


    Gdx.input.setInputProcessor(stage)
  }


  override def hide(): Unit = {
    //dispose()
  }


  override def pause(): Unit = {
  }

  override def resume(): Unit = {
  }

  override def dispose(): Unit = {
//    stage.dispose()
    //skin.dispose()
  }

}
