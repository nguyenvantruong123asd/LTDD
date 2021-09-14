import React, { Component } from 'react'
import {View, Text} from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import Welcome from './Screens/Welcome.js';
import Main from './Screens/Main.js';
import Authentication from './Screens/Authentication.js'
import ListProduct from './Screens/ListProduct.js'
import ChangeInfo from './Screens/ChangeInfo.js';
import CartView from './Screens/Cart.js';
import Account from './Screens/AccountInfo.js';
import ProductDetail from './Screens/ProductDetail';
import Shop from './Screens/Shop.js'
import NotLogin from './Screens/NotLogin.js'
import LoginFB from './Screens/LoginFB'
import Home from './Screens/Admin/Home'


const Appstack= createStackNavigator({
	Home: Home, 
	Welcome: Welcome,
	Main: Main,
 	Welcome: Welcome,
	ProductDetail: ProductDetail,
	Account: Account,
	CartView: CartView,		 
	Authentication: Authentication,
	ChangeInfo: ChangeInfo,
	ListProduct: ListProduct,
	LoginFB: LoginFB,
})

const App= createAppContainer(Appstack);

export default App ;
