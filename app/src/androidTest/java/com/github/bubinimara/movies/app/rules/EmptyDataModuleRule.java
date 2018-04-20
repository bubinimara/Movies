package com.github.bubinimara.movies.app.rules;

import com.github.bubinimara.movies.app.rules.module.EmptyApplicationDataModule;

/**
 * Created by davide.
 */
public class EmptyDataModuleRule extends DataModuleRule {
    public EmptyDataModuleRule() {
        super(new EmptyApplicationDataModule());
    }
}
