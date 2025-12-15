package com.vaadin.workshop.views.playground;


import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Playground")
@Route("playground")
@Menu(order = 4, icon = LineAwesomeIconUrl.SLIDERS_H_SOLID)
public class PlaygroundView extends VerticalLayout {

    /**
     * 1. Fundamentals Vaadin + Short exercise
     * 2. Copilot + Exercise
     * 3. Data Binding + Exercise
     * 4. Testing + Exercise
     */

    public PlaygroundView() {

    }
}
