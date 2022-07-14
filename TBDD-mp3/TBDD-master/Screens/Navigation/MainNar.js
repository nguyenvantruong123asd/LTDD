import React, {Component} from 'react'
import {View, Text} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import Main from '../Main.js';
import Account from '../AccountInfo.js'

const Appstack = createStackNavigator({
    Main: Main,
    Account: Account,
})

const MainNar = createAppContainer(Appstack);

export default MainNar;
