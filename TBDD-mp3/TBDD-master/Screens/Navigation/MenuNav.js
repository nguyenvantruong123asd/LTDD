import React, { Component } from 'react'
import {View, Text} from 'react-native';
import {Router, Scene} from 'reace-native-router-flux';
import Menu from '../Menu.js';
import Account from '../AccountInfo.js';

const MenuNav=()=>{
	return (
		<Router>
			<Scene key="root">
				<Scene 
					key="Menu"
					component={Menu}
					title="Menu"
					initial
				/>
				<Scene 
					key="Account"
					component={Account}
					title="Account"
				/>
			</Scene>
		</Router>
	);
}

export default MenuNav ;
