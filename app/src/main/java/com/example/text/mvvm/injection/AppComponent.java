package com.example.text.mvvm.injection;

import com.example.text.mvvm.viewModel.StudentViewModel;

import dagger.Component;

/**
 * <p></p >
 * <p></p >
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/17 13:02
 */
@Component
public interface AppComponent {

    void inject(StudentViewModel studentViewModel);
}
