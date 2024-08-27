package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions;

import java.util.NoSuchElementException;

public class CategoryNotFoundByNameException extends NoSuchElementException {
    public CategoryNotFoundByNameException(){ super(); }
}
