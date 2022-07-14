import React, {Component} from 'react';
import {Text, View} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import Home from './Home.js';
import ListProduct from './ListProduct.js';

const Appstack = createStackNavigator({
    Home: Home,
    ListProduct: ListProduct,
});

const BigHome = createAppContainer(Appstack);

export default BigHome;
