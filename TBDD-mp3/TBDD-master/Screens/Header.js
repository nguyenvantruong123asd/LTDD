import React, {Component} from 'react';
import {
    Text,
    View,
    TouchableOpacity,
    Image,
    TextInput,
    StyleSheet,
} from 'react-native';
import Cart from './Cart.js'
import Cart1 from '../Assest/Icon/cart.png';

export default class Header extends Component {
    render() {
        return (
            <View style={{height: 51, backgroundColor: '#76A466', padding: 10}}>
                <View style={styles.wrapper}>

                    <TextInput style={styles.input} placeholder="Tìm kiếm"/>
                    <TouchableOpacity onPress={this.props.onOpen}
                                      onPress={() => this.props.navigation.push('Cart')}>
                        <Image style={styles.icon} source={Cart1}/>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    wrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    icon: {
        height: 25,
        width: 25,
    },
    input: {
        height: 30,
        fontSize: 12,
        padding: 1,
        width: '80%',
        backgroundColor: 'white',
        borderRadius: 5,
        fontFamily: 'Quicksand-Medium',
    },
});
