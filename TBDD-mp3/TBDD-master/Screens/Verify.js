import React, {Component} from 'react';
import {
    Text,
    View,
    Button,
    TouchableOpacity,
    StyleSheet,
    Image,
    TextInput,
    Dimensions
} from 'react-native';
import back from '../Assest/image/back.png';
import home from '../Assest/Icon/home0.png';

const {width} = Dimensions.get('window');
const wid = width - 130;

export default class Verify extends Component {
    static navigationOptions = {
        header: null,
    };

    render() {
        return (
            <View style={styles.container}>
                <View style={{flex: 1}}>
                    <TouchableOpacity onPress={this.props.onOpen} onPress={() => this.props.navigation.goBack()}>
                        <Image style={styles.icon} source={back}/>
                    </TouchableOpacity>
                    <Text style={styles.styleText}>Xác minh</Text>
                    <Text style={styles.styleText1}>Vui lòng điền mã xác nhận đã gửi đến ********07</Text>
                    <TouchableOpacity></TouchableOpacity>
                </View>

                <View style={{flex: 5}}>
                    <Text style={styles.textI}>Mã xác nhận</Text>
                    <View style={{flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between'}}>
                        <TextInput style={styles.input} placeholder="Nhập mã xác nhận"/>
                        <TouchableOpacity style={styles.login1}>
                            <Text style={styles.signText}>Xác nhận</Text>
                        </TouchableOpacity>
                    </View>
                    <View style={{marginTop: 50}}>
                        <Text style={styles.textI}>Mật khẩu mới</Text>
                        <TextInput style={styles.input1} placeholder="Nhập mật khẩu mới của bạn"/>
                        <Text style={styles.textI}>Nhập lại mật khẩu mới</Text>
                        <TextInput style={styles.input1} placeholder="Nhập lại mật khẩu mới của bạn"/>
                    </View>


                    <View style={{alignItems: 'center'}}>
                        <TouchableOpacity style={styles.login}>
                            <Text style={styles.signText}>Thay đổi</Text>
                        </TouchableOpacity>
                        <TouchableOpacity style={{alignItems: 'center'}} onPress={this.props.onOpen}
                                          onPress={() => this.props.navigation.goBack()}>
                            <Text style={styles.textII}>Trở lại</Text>
                        </TouchableOpacity>
                    </View>
                </View>

            </View>
        );
    }
}
const styles = StyleSheet.create({
    styleText1: {
        color: '#6D6E6A',
        fontSize: 14,
        fontFamily: 'Quicksand-Regular',
    },
    textII: {
        textDecorationLine: 'underline',
        color: '#7E8186',
        fontSize: 13,
        paddingTop: 15,
        fontFamily: 'Quicksand-SemiBold',
    },
    login1: {
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 5,
        marginTop: 10,
        backgroundColor: '#F13233',
        width: 70,
        height: 30,
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
        width: wid,
        fontSize: 12,
        padding: 1,
        marginRight: 10,
        backgroundColor: 'white',
        borderBottomWidth: 1,
        borderColor: '#7E8186',
        fontFamily: 'Quicksand-SemiBold',
        color: '#6D6E6A',
    },
    input1: {
        height: 40,
        fontSize: 12,
        padding: 1,
        marginRight: 10,
        backgroundColor: 'white',
        borderBottomWidth: 1,
        borderColor: '#7E8186',
        fontFamily: 'Quicksand-SemiBold',
        color: '#6D6E6A',
    },
    styleText: {
        color: '#333137',
        fontSize: 24,
        fontFamily: 'Quicksand-Bold',
    },
    styleText1: {
        color: '#6D6E6A',
        fontSize: 14,
        fontFamily: 'Quicksand-Regular',
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
