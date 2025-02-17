package com.example.ticketgui;

public interface IController {

    // TODO : overvej om dette skal laves til en abstrakt klasse
    // TODO : overalt i controllerne er der duplikater - tænk på hvordan dette enten kan implementeres i interfacet el lign
    void initializeComponents(double width, double height);
    void resizeItems(double width, double height);
}
