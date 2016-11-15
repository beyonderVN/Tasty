package com.vnwarriors.tasty.ui.adapter.vmfactory;

import com.vnwarriors.tasty.ui.adapter.viewmodel.SectionVM;
import com.vnwarriors.tasty.ui.adapter.viewmodel.SimpleHorizontalVM;
import com.vnwarriors.tasty.ui.adapter.viewmodel.SimpleVerticalVM;

/**
 * Created by Long on 11/10/2016.
 */
public interface ViewTypeFactory {

    int getType(SimpleVerticalVM simpleVerticalVM);

    int getType(SectionVM sectionVM);

    int getType(SimpleHorizontalVM simpleHorizontalVM);
}
