import React, {Component} from 'react';
import {Text, View, TouchableOpacity} from 'react-native';
import Menu from './Menu.js';
import Shop from './Shop.js';
import Drawer from 'react-native-drawer';

export default class Main extends Component {
    static navigationOptions = {
        header: null,
    };

    closeControlPanel = () => {
        this.drawer.close();
    };
    openControlPanel = () => {
        this.drawer.open();
    };

    render() {
        return (
            <Drawer
                ref={ref => {
                    this.drawer = ref;
                }}
                content={<Menu/>}
                openDrawerOffset={0.4}
                tapToClose>
                <Shop open={this.openControlPanel.bind(this)}/>
            </Drawer>
        );
    }
}
