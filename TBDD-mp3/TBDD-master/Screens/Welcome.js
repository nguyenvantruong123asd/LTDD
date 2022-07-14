import React, {Component} from 'react';
import {
    View,
    Text,
    StyleSheet,
    Image,
    ScrollView,
    Button,
    Dimensions,
    TouchableOpacity,
} from 'react-native';
import camera from '../Assest/Icon/camera.jpg';
import camera1 from '../Assest/Icon/camera1.jpg';
import camera2 from '../Assest/Icon/camera2.jpg';

const {width} = Dimensions.get('window');

export default class Welcome extends Component {
    static navigationOptions = {
        header: null,
    };

    render() {
        return (
            <View style={{flex: 1}}>
                <View style={styles.wel}>
                    <View style={{flexDirection: 'row'}}>
                        <Text style={styles.welText1}>Welcome to</Text>
                        <Text style={styles.welText2}>Cameran</Text>
                    </View>
                    <View style={{paddingTop: 10}}>
                        <Text style={styles.welText3}>Sự lựa chọn đúng đắn của bạn</Text>
                    </View>
                </View>
                <View style={{flex: 17}}>
                    <View style={{margin: 15, alignItems: 'center'}}>
                        <ScrollView
                            horizontal={true}
                            pagingEnabled={true}
                            showsHorizontalScrollIndicator={false}>
                            <Image source={camera} style={styles.image}/>
                            <Image source={camera1} style={styles.image}/>
                            <Image source={camera2} style={styles.image}/>
                        </ScrollView>
                    </View>
                </View>
                <View style={{flex: 10, alignItems: 'center'}}>
                    <TouchableOpacity style={styles.login} onPress={() => this.props.navigation.push('Authentication')}>
                        <Text style={styles.text1}>Login</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.signUp}
                                      onPress={() => this.props.navigation.push('Authentication')}>
                        <Text style={styles.text2}>Sign up</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.forget} onPress={() => this.props.navigation.push('Main')}>
                        <Text style={styles.text3}>I dont have a account</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    wel: {
        flex: 3,
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 10,
    },
    welText1: {
        fontFamily: 'Quicksand-Bold',
        fontSize: 27,
        color: '#6B6972',
    },
    welText2: {
        fontFamily: 'Quicksand-Bold',
        fontSize: 27,
        color: '#F13233',
    },
    welText3: {
        fontFamily: 'Quicksand-Bold',
        fontSize: 15,
        color: '#C9C9C9',
    },
    image: {
        width: width - 30,
        height: 350,
        borderRadius: 20,
        margin: 2,
    },
    login: {
        alignItems: 'center',
        borderRadius: 5,
        paddingTop: 5,
        backgroundColor: '#F13233',
        width: 250,
        height: 40,
        marginTop: 60,
    },
    signUp: {
        marginTop: 10,
        alignItems: 'center',
        borderRadius: 5,
        paddingTop: 5,
        backgroundColor: '#F6F2F7',
        width: 250,
        height: 40,
    },
    forget: {
        marginTop: 10,
        alignItems: 'center',
        borderRadius: 5,
        paddingTop: 5,
        backgroundColor: 'white',
        width: 250,
        height: 40,
    },
    text1: {
        fontFamily: 'Quicksand-Bold',
        fontSize: 17,
        color: 'white',
    },
    text2: {
        fontFamily: 'Quicksand-SemiBold',
        fontSize: 17,
        color: '#42433E',
    },
    text3: {
        fontFamily: 'Quicksand-Medium',
        fontSize: 14,
        color: '#42433E',
    },
});
