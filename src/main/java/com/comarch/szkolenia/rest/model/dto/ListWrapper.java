package com.comarch.szkolenia.rest.model.dto;

import com.comarch.szkolenia.rest.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListWrapper<T> {
    private List<T> objects;
}
