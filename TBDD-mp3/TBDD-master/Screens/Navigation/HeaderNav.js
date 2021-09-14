import React, { Component } from 'react'
import {View, Text} from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import Header from '../Header.js';
import Cart from '../Cart.js'


const Appstack= createStackNavigator({
	Cart: Cart,
	Header: Header,
})

const HeaderNav= createAppContainer(Appstack);

export default HeaderNav ;
