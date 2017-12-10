import javax.swing.event.*;
import javax.swing.JSlider;


public class SliderListener implements ChangeListener {

  private Core core;

  public SliderListener() {
    core = Core.getInstance();
  }

  public void stateChanged(ChangeEvent e) {
      JSlider source = (JSlider)e.getSource();

      if (source.getValueIsAdjusting()) {
        int zoom = (int)source.getValue();
        core.changeZoom(zoom);
      }
  }
}
