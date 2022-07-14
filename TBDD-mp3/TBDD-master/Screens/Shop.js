import React, {Component} from 'react';
import {Text, View, TouchableOpacity, Image, StyleSheet} from 'react-native';
import TabNavigator from 'react-native-tab-navigator';
import Search from './Search.js';
import Cart from './Cart.js';
import Account from './AccountInfo.js';
import Contact from './Contact.js';
import Catagories from './Catagories.js';
import AccNav from './Navigation/AccNav.js';
import CataNav from './Navigation/CataNav.js';
import CartNav from './Navigation/CartNav';

import HomeNav from './Navigation/HomeNav.js';
import home from '../Assest/Icon/home.png';
import home0 from '../Assest/Icon/home0.png';
import acc from '../Assest/Icon/account.png';
import acc1 from '../Assest/Icon/account1.png';
import cara from '../Assest/Icon/cata.png';
import cara1 from '../Assest/Icon/cata1.png';
import search from '../Assest/Icon/caa.jpg';
import search1 from '../Assest/Icon/caa1.jpg';

export default class Shop extends Component {
    openMenu() {
        const {open} = this.props;
        open();
    }

    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);

        this.state = {
            selectedTab: 'homeNav',
        };
    }

    render() {
        return (
            <View style={{flex: 1}}>
                <View style={{flex: 18}}>
                    <TabNavigator>
                        <TabNavigator.Item
                            selected={this.state.selectedTab === 'homeNav'}
                            renderIcon={() => <Image source={home0} style={styles.icon}/>}
                            renderSelectedIcon={() => (
                                <Image source={home} style={styles.icon}/>
                            )}
                            badgeText="1"
                            selectedTitleStyle={{color: '#34B089', fontSize: 11}}
                            onPress={() => this.setState({selectedTab: 'homeNav'})}>
                            <HomeNav/>
                        </TabNavigator.Item>
                        <TabNavigator.Item
                            selected={this.state.selectedTab === 'CataNav'}
                            renderIcon={() => <Image source={cara1} style={styles.icon}/>}
                            renderSelectedIcon={() => (
                                <Image source={cara} style={styles.icon}/>
                            )}
                            onPress={() => this.setState({selectedTab: 'CataNav'})}>
                            <CataNav/>
                        </TabNavigator.Item>
                        <TabNavigator.Item
                            selected={this.state.selectedTab === 'cart'}
                            renderIcon={() => <Image source={search1} style={styles.icon}/>}
                            renderSelectedIcon={() => (
                                <Image source={search} style={styles.icon}/>
                            )}
                            onPress={() => this.setState({selectedTab: 'cart'})}>
                            <CartNav/>
                        </TabNavigator.Item>
                        <TabNavigator.Item
                            selected={this.state.selectedTab === 'accNav'}
                            renderIcon={() => <Image source={acc1} style={styles.icon}/>}
                            renderSelectedIcon={() => (
                                <Image source={acc} style={styles.icon}/>
                            )}
                            onPress={() => this.setState({selectedTab: 'accNav'})}>
                            <AccNav/>
                        </TabNavigator.Item>
                    </TabNavigator>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    icon: {
        height: 30,
        width: 30,
    },
});
