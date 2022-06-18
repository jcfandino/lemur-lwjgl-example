package jc;

import static com.jme3.math.ColorRGBA.Yellow;

import com.jme3.app.SimpleApplication;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.event.DefaultRawInputListener;
import com.simsilica.lemur.style.BaseStyles;

public class LemurInputBugDemo extends SimpleApplication {

  public static void main(String[] args) {
    new LemurInputBugDemo().start();
  }

  private Label label;
  private Geometry box;

  @Override
  public void simpleInitApp() {
    GuiGlobals.initialize(this);
    BaseStyles.loadGlassStyle();
    GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
    flyCam.setEnabled(false);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    material.setColor("Color", Yellow);
    box = new Geometry("box", new Box(1, 1, 1));
    box.setMaterial(material);
    rootNode.attachChild(box);

    Container container = new Container();
    container.setPreferredSize(new Vector3f(400, 100, 0));
    container.setLocalTranslation(cam.getWidth() / 2 - 200, cam.getHeight() / 2, 0);
    container.addChild(new TextField("type here"));
    container.addChild(new TextField("type here"));
    label = container.addChild(new Label(""));

    Container buttonBar = container.addChild(new Container(new SpringGridLayout(Axis.X, Axis.Y)));
    buttonBar.addChild(new Button("Ok"));
    Button cancel = buttonBar.addChild(new Button("Cancel"));
    cancel.addClickCommands(b -> stop());

    guiNode.attachChild(container);

    getInputManager().addRawInputListener(new KeyListener());
  }

  private class KeyListener extends DefaultRawInputListener {
    @Override
    public void onKeyEvent(KeyInputEvent evt) {
      if (!evt.isConsumed() && evt.isPressed()) {
        box.getMaterial().setColor("Color", ColorRGBA.randomColor());
        label.setText(evt.toString());
      }
    }
  }

}
