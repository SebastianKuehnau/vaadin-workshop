package com.vaadin.workshop.views.playground;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Playground")
@Route("playground")
@Menu(order = 4, icon = LineAwesomeIconUrl.SLIDERS_H_SOLID)
public class PlaygroundView extends VerticalLayout {

    public PlaygroundView() {

    }
}
