import React, {Component} from 'react'
import {View, Text} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import ChangeInfo from '../ChangeInfo.js'
import Account from '../AccountInfo.js'
import Authentication from '../Authentication.js'
import LoginFB from '../LoginFB.js'
import Order from '../OrderItem/Order'
import Deli from '../OrderItem/Deli'
import OrderHistory from '../OrderItem/OrderHistory'


const Appstack = createStackNavigator({
    Account: Account,
    ChangeInfo: ChangeInfo,
    Authentication: Authentication,
    Order: Order,
    OrderHistory: OrderHistory,
    Deli: Deli,
    LoginFB: LoginFB,
})

const AccNav = createAppContainer(Appstack);

export default AccNav;
