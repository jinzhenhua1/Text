// PersonController.aidl
package com.jzh.aidlservice.aidl;
import com.jzh.aidlservice.aidl.Person;

// Declare any non-default types here with import statements

interface PersonController {

    List<Person> getBookList();

    void addBookInOut(inout Person book);
}
