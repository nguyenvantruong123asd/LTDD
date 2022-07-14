import React, {Component} from 'react'
import {View, Text} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import Catagories from '../Catagories.js';
import ListProduct from '../ListProduct.js';
import ProductDetail from '../ProductDetail.js'
import Cart from '../Cart.js';

const Appstack = createStackNavigator({
    Catagories: Catagories,
    ListProduct: ListProduct,
    ProductDetail: ProductDetail,
    Cart: Cart,
})

const CataNav = createAppContainer(Appstack);

export default CataNav;