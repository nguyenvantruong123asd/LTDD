import React, {Component} from 'react';
import {View, Text} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import ShowProduct from './ShowProduct';
import Edit from './Edit';
import Create from './createPro.js'

const Appstack = createStackNavigator({
  ShowProduct: ShowProduct,
  Edit: Edit,
  Create: Create,
});

const Nav = createAppContainer(Appstack);

export default Nav;
