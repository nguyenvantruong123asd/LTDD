import React, {Component} from 'react';
import {
    View,
    TouchableOpacity,
    Text,
    Image,
    StyleSheet,
    TextInput,
    ScrollView,
    Dimensions,
} from 'react-native';
import back from '../Assest/image/back.png';
import profile from '../Assest/image/meo.jpg';
import dadat from '../Assest/Icon/dathang.png';
import danggiao from '../Assest/Icon/danggiao.png';
import dathanhtoan from '../Assest/Icon/dathanhtoan.jpg';
import dangnhap from '../Assest/Icon/dangnhap.png';
import dangky from '../Assest/Icon/dangky.png';

const {width} = Dimensions.get('window');
const text = (width + 160) / 2;

export default class Account extends Component {
    constructor(props) {
        super(props);

        this.state = {isLoggin: false};
    }

    static navigationOptions = {
        header: null,
    };

    render() {
        const {
            wrapper,
            header,
            headerTitle,
            backIconStyle,
            body,
            signInContainer,
            signInTextStyle,
            textInput,
        } = styles;
        const logout = (
            <View style={wrapper}>
                <View style={{marginBottom: 20, marginTop: 10}}>
                    <Text style={styles.headerTitle}>Thông tin người dùng</Text>
                </View>
                <View
                    style={{
                        flex: 1,
                        backgroundColor: 'white',
                        padding: 20,
                        justifyContent: 'space-between',
                    }}>
                    <View style={{flex: 1}}>
                        <View>
                            <ScrollView>
                                <View
                                    style={{
                                        flex: 1,
                                        borderColor: '#C9C8C6',
                                        paddingBottom: 10,
                                        paddingTop: 10,
                                        marginTop: 20,
                                    }}>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                        }}>
                                        <TouchableOpacity
                                            onPress={() =>
                                                this.props.navigation.push('Authentication')
                                            }>
                                            <View style={{alignItems: 'center', paddingLeft: 50}}>
                                                <Image
                                                    source={dangnhap}
                                                    style={{width: 70, height: 70}}
                                                />
                                                <Text style={styles.deli}>Đăng nhập</Text>
                                            </View>
                                        </TouchableOpacity>
                                        <TouchableOpacity
                                            onPress={() =>
                                                this.props.navigation.push('Authentication')
                                            }>
                                            <View style={{alignItems: 'center', paddingRight: 50}}>
                                                <Image
                                                    source={dangky}
                                                    style={{width: 70, height: 70}}
                                                />
                                                <Text style={styles.deli}>Đăng ký</Text>
                                            </View>
                                        </TouchableOpacity>
                                    </View>
                                </View>
                            </ScrollView>
                        </View>
                    </View>
                </View>
            </View>
        );
        const loggin = (
            <View style={wrapper}>
                <View style={{marginBottom: 20, marginTop: 10}}>
                    <Text style={styles.headerTitle}>Thông tin người dùng</Text>
                </View>
                <View style={styles.container}>
                    <View style={{flex: 1}}>
                        <View>
                            <View
                                style={{
                                    flexDirection: 'row',
                                    justifyContent: 'space-between',
                                    marginBottom: 10,
                                    alignItems: 'center',
                                }}>
                                <Image
                                    source={profile}
                                    style={{width: 60, borderRadius: 30, height: 60}}
                                />
                                <Text style={styles.info}>Yaoming113001</Text>
                            </View>
                            <ScrollView>
                                <View
                                    style={{
                                        flex: 1,
                                        borderTopWidth: 1,
                                        borderBottomWidth: 1,
                                        borderColor: '#C9C8C6',
                                        paddingBottom: 10,
                                        paddingTop: 10,
                                        marginTop: 20,
                                    }}>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                        }}>
                                        <TouchableOpacity
                                            onPress={() => this.props.navigation.push('Order')}>
                                            <View style={{alignItems: 'center'}}>
                                                <Image source={dadat} style={{width: 40, height: 40}}/>
                                                <Text style={styles.deli}>Đơn đã đặt</Text>
                                            </View>
                                        </TouchableOpacity>
                                        <TouchableOpacity
                                            onPress={() => this.props.navigation.push('Deli')}>
                                            <View style={{alignItems: 'center'}}>
                                                <Image
                                                    source={danggiao}
                                                    style={{width: 40, height: 40}}
                                                />
                                                <Text style={styles.deli}>Đang giao</Text>
                                            </View>
                                        </TouchableOpacity>
                                        <TouchableOpacity
                                            onPress={() =>
                                                this.props.navigation.push('OrderHistory')
                                            }>
                                            <View style={{alignItems: 'center', marginRight: 20}}>
                                                <Image
                                                    source={dathanhtoan}
                                                    style={{width: 40, height: 40}}
                                                />
                                                <Text style={styles.deli}>Lịch sử mua</Text>
                                            </View>
                                        </TouchableOpacity>
                                    </View>
                                </View>
                                <View style={{flex: 7, marginTop: 10}}>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                            marginTop: 12,
                                        }}>
                                        <Text style={styles.info1}>Họ tên:</Text>
                                        <Text style={styles.info2}>Nguyễn Phước Đức</Text>
                                    </View>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                            marginTop: 12,
                                        }}>
                                        <Text style={styles.info1}>Mật khẩu:</Text>
                                        <Text style={styles.info2}>**********</Text>
                                    </View>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                            marginTop: 12,
                                        }}>
                                        <Text style={styles.info1}>SĐT:</Text>
                                        <Text style={styles.info2}>0389072007</Text>
                                    </View>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                            marginTop: 12,
                                        }}>
                                        <Text style={styles.info1}>Gmail:</Text>
                                        <Text style={styles.info2}>16130334@st.hcmuaf.edu.vn</Text>
                                    </View>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                            marginTop: 12,
                                        }}>
                                        <Text style={styles.info1}>Địa chỉ:</Text>
                                        <Text style={styles.info2}>
                                            35/1c ấp Hưng Lân, xã Bà Điểm
                                        </Text>
                                    </View>
                                    <View
                                        style={{
                                            flexDirection: 'row',
                                            justifyContent: 'space-between',
                                            alignItems: 'center',
                                            marginTop: 12,
                                        }}>
                                        <Text style={styles.info1}>Giới tính:</Text>
                                        <Text style={styles.info2}>Nam</Text>
                                    </View>
                                </View>
                            </ScrollView>
                        </View>
                        <View style={{alignItems: 'center'}}>
                            <TouchableOpacity
                                style={styles.login}
                                onPress={() => this.props.navigation.navigate('ChangeInfo')}>
                                <Text style={styles.signText}>THAY ĐỔI</Text>
                            </TouchableOpacity>
                            <TouchableOpacity
                                style={{alignItems: 'center'}}
                                onPress={() =>
                                    this.props.navigation.navigate('Authentication')
                                }>
                                <Text style={styles.textII}>Đăng xuất</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>
            </View>
        );
        const main = this.state.isLoggin ? logout : loggin;
        return <View style={{flex: 1}}>{main}</View>;
    }
}

const styles = StyleSheet.create({
    deli: {
        fontSize: 14,
        color: '#757575',
        fontFamily: 'Quicksand-Medium',
    },
    info: {
        color: '#F13233',
        fontSize: 20,
        fontFamily: 'Quicksand-Bold',
        paddingRight: 20,
    },
    info1: {
        color: '#343D46',
        fontSize: 16,
        fontFamily: 'Quicksand-Bold',
    },
    info2: {
        color: '#7E8186',
        fontSize: 15,
        fontFamily: 'Quicksand-Medium',
        paddingLeft: 20,
        textAlign: 'left',
        width: text,
        paddingRight: 20,
    },
    container: {
        flex: 1,
        backgroundColor: 'white',
        padding: 20,
        justifyContent: 'space-between',
        paddingRight: 0,
    },
    textII: {
        textDecorationLine: 'underline',
        color: '#7E8186',
        fontSize: 13,
        paddingTop: 10,
        fontFamily: 'Quicksand-SemiBold',
    },
    signText: {
        color: 'white',
        fontFamily: 'Quicksand-Bold',
    },
    login: {
        alignItems: 'center',
        borderRadius: 5,
        paddingTop: 5,
        backgroundColor: '#F13233',
        width: 150,
        height: 40,
        marginTop: 30,
        paddingTop: 10,
    },
    input: {
        height: 30,
        fontSize: 12,
        padding: 1,
        backgroundColor: 'white',
        marginBottom: 20,
        borderBottomWidth: 1,
        borderColor: '#7E8186',
        fontFamily: 'Quicksand-SemiBold',
        color: '#6D6E6A',
    },
    textI: {
        color: '#6D6E6A',
        fontSize: 18,
        fontFamily: 'Quicksand-SemiBold',
        marginTop: 10,
    },
    headerTitle: {
        color: '#333137',
        fontSize: 25,
        marginLeft: 10,
        fontFamily: 'Quicksand-SemiBold',
    },
    wrapper: {flex: 1, backgroundColor: '#fff'},
    header: {
        flex: 1,
        backgroundColor: '#2ABB9C',
        alignItems: 'center',
        justifyContent: 'space-between',
        flexDirection: 'row',
        paddingHorizontal: 10,
    }, // eslint-disable-line
    backIconStyle: {width: 30, height: 30},
    body: {flex: 10, justifyContent: 'center'},
    textInput: {
        height: 45,
        marginHorizontal: 20,
        backgroundColor: '#FFFFFF',
        fontFamily: 'Avenir',
        paddingLeft: 20,
        borderRadius: 20,
        marginBottom: 20,
        borderColor: '#2ABB9C',
        borderWidth: 1,
    },
    signInTextStyle: {
        color: '#FFF',
        fontFamily: 'Avenir',
        fontWeight: '600',
        paddingHorizontal: 20,
    },
    signInContainer: {
        marginHorizontal: 20,
        backgroundColor: '#2ABB9C',
        borderRadius: 20,
        height: 45,
        alignItems: 'center',
        justifyContent: 'center',
        alignSelf: 'stretch',
    },
    signInStyle: {
        flex: 3,
        marginTop: 50,
    },
});

// goBackToMain() {
//     const { navigator } = this.props;
//     navigator.pop();
// }
