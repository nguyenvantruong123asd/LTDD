import React, { Component } from 'react'
import {View, Text} from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import ProductDetail from '../ProductDetail.js'
import Cart from '../Cart.js';

const Appstack= createStackNavigator({
	Cart: Cart ,
	ProductDetail	: ProductDetail,
})

const CartNav= createAppContainer(Appstack);

export default CartNav ;