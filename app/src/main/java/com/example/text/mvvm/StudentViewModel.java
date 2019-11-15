package com.example.text.mvvm;

import androidx.databinding.ObservableChar;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class StudentViewModel {
    public ObservableField<String> studentName = new ObservableField<>("");
    public ObservableField<String> className = new ObservableField<>("");
    public ObservableInt age = new ObservableInt();



}
