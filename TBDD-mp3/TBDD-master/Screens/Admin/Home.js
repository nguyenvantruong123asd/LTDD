import React, {Component} from 'react';
import {
    View,
    TouchableOpacity,
    Text,
    Image,
    StyleSheet,
    TextInput,
    ScrollView,
} from 'react-native';
import back from '../../Assest/image/back.png';
import Nav from './Nav.js'

export default class Edit extends Component {
    static navigationOptions = {
        header: null,
    };

    render() {
        return (
            <View style={{flex: 1}}>
                <Nav/>
            </View>
        );
    }
}