import React, {Component} from 'react';
import {
    Text,
    View,
    Button,
    TouchableOpacity,
    StyleSheet,
    Image,
    TextInput,
} from 'react-native';
import back from '../Assest/image/back.png';
import home from '../Assest/Icon/home0.png';

export default class Authentication extends Component {

    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);
        this.state = {isSignIn: true};
    }

    signIn() {
        this.setState({isSignIn: true});
    }

    signOut() {
        this.setState({isSignIn: false});
    }

    render() {
        const signinJSX = (
            <View>
                <Text style={styles.textI}>Email</Text>
                <TextInput style={styles.input} placeholder="Enter you email"/>
                <Text style={styles.textI}>Password</Text>
                <TextInput style={styles.input} placeholder="Enter you password"/>
                <View style={{alignItems: 'center'}}>
                    <TouchableOpacity style={styles.login}>
                        <Text style={styles.signText}>ĐĂNG NHẬP</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.login} onPress={this.props.onOpen}
                                      onPress={() => this.props.navigation.push('LoginFB')}>
                        <Text style={styles.signText}>Login With FB</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={{alignItems: 'center'}}>
                        <Text style={styles.textII}>Quên mật khẩu</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
        const signupJSX = (
            <View>
                <Text style={styles.textI}>Name</Text>
                <TextInput style={styles.input} placeholder="Enter you name"/>
                <Text style={styles.textI}>Email</Text>
                <TextInput style={styles.input} placeholder="Enter you email"/>
                <Text style={styles.textI}>Password</Text>
                <TextInput style={styles.input} placeholder="Enter you password"/>
                <Text style={styles.textI}>Re-password</Text>
                <TextInput
                    style={styles.input}
                    placeholder="Enter you password again"
                />
                <View style={{alignItems: 'center'}}>
                    <TouchableOpacity style={styles.signUp}>
                        <Text style={styles.signText}>ĐĂNG KÝ</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );

        const {isSignIn} = this.state;
        const mainJSX = isSignIn ? signinJSX : signupJSX;

        return (
            <View style={styles.container}>
                <View>
                    <TouchableOpacity onPress={this.props.onOpen} onPress={() => this.props.navigation.goBack()}>
                        <Image style={styles.icon} source={back}/>
                    </TouchableOpacity>
                    <Text style={styles.styleText}>{isSignIn ? 'ĐĂNG NHẬP' : 'ĐĂNG KÝ'}</Text>
                    <TouchableOpacity
                        onPress={this.props.onOpen}
                        onPress={() =>
                            this.props.navigation.push('Main')
                        }></TouchableOpacity>
                </View>
                {mainJSX}
                <View style={{alignItems: 'center', justifyContent: 'center'}}>
                    <View style={styles.controlStyle}>
                        <TouchableOpacity
                            style={isSignIn ? styles.signIn1 : styles.signIn}
                            onPress={this.signIn.bind(this)}>
                            <Text style={isSignIn ? styles.active : styles.isActive}>
                                SIGN IN
                            </Text>
                        </TouchableOpacity>
                        <TouchableOpacity
                            style={isSignIn ? styles.signOut : styles.signOut1}
                            onPress={this.signOut.bind(this)}>
                            <Text style={isSignIn ? styles.isActive : styles.active}>
                                SIGN UP
                            </Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    textII: {
        textDecorationLine: 'underline',
        color: '#7E8186',
        fontSize: 13,
        paddingTop: 15,
        fontFamily: 'Quicksand-SemiBold',
    },
    login: {
        alignItems: 'center',
        borderRadius: 5,
        paddingTop: 5,
        backgroundColor: '#F13233',
        width: 150,
        height: 40,
        marginTop: 60,
        paddingTop: 10,
    },
    signUp: {
        alignItems: 'center',
        borderRadius: 5,
        paddingTop: 5,
        backgroundColor: '#F13233',
        width: 150,
        height: 40,
        marginTop: 20,
        paddingTop: 10,
    },
    textI: {
        color: '#6D6E6A',
        fontSize: 18,
        fontFamily: 'Quicksand-SemiBold',
        marginTop: 10,
    },
    wrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    icon: {
        height: 30,
        width: 30,
    },
    input: {
        height: 40,
        fontSize: 12,
        padding: 1,
        backgroundColor: 'white',
        marginBottom: 20,
        borderBottomWidth: 1,
        borderColor: '#7E8186',
        fontFamily: 'Quicksand-SemiBold',
        color: '#6D6E6A',
    },
    styleText: {
        color: '#333137',
        fontSize: 27,
        fontFamily: 'Quicksand-Bold',
    },
    container: {
        flex: 1,
        backgroundColor: 'white',
        padding: 20,
        paddingTop: 10,
        justifyContent: 'space-between',
    },
    controlStyle: {
        flexDirection: 'row',
        width: 300,
    },
    signIn: {
        backgroundColor: 'white',
        alignItems: 'center',
        paddingVertical: 14,
        flex: 1,
        borderBottomLeftRadius: 20,
        borderTopLeftRadius: 20,
        margin: 1,
        borderWidth: 2,
        borderColor: '#F13233',
    },
    signIn1: {
        backgroundColor: '#F13233',
        alignItems: 'center',
        paddingVertical: 14,
        flex: 1,
        borderBottomLeftRadius: 20,
        borderTopLeftRadius: 20,
        margin: 1,
    },
    signOut: {
        backgroundColor: 'white',
        alignItems: 'center',
        flex: 1,
        paddingVertical: 14,
        borderBottomRightRadius: 20,
        borderTopRightRadius: 20,
        margin: 1,
        borderWidth: 2,
        borderColor: '#F13233',
    },
    signOut1: {
        backgroundColor: '#F13233',
        alignItems: 'center',
        flex: 1,
        paddingVertical: 14,
        borderBottomRightRadius: 20,
        borderTopRightRadius: 20,
        margin: 1,
    },
    isActive: {
        color: '#6D6E6A',
        fontFamily: 'Quicksand-Bold',
        fontSize: 15,
    },
    active: {
        color: 'white',
        fontFamily: 'Quicksand-Bold',
        fontSize: 15,
    },
    sign: {
        height: 40,
        borderRadius: 17,
        alignItems: 'center',
        justifyContent: 'center',
    },
    signText: {
        color: 'white',
        fontFamily: 'Quicksand-Bold',
    },
});
