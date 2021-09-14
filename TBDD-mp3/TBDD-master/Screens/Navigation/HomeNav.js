import React, { Component } from 'react'
import {View, Text} from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import Home from '../Home.js';
import ListProduct from '../ListProduct.js';
import ProductDetail from '../ProductDetail.js'
import Cart from '../Cart.js';
import Header from '../Header.js'
import Advertise from '../Advertise.js'
import Search from '../Search.js'


const Appstack= createStackNavigator({
	Home : Home,
	Advertise: Advertise,
	ProductDetail: ProductDetail,
	Cart: Cart,
	Header: Header,
	Search: Search,
})

const HomeNav= createAppContainer(Appstack);

export default HomeNav ;
