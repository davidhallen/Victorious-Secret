package bodega.timeline_alfa;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Piotri on 5/6/2015.
 */
public class MenuButton extends Button {
    private Boolean active = false;

    public MenuButton(Context context) {
        super(context);

    }

    public void setActiveState(Boolean active) {
        this.active = active;
    }

    public Boolean getActiveState() {
        return active;
    }
}
