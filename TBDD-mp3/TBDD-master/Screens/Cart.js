import React, {Component} from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    ScrollView,
    Dimensions,
    StyleSheet,
    Image,
    TextInput,
    Alert,
} from 'react-native';
import Swipeout from 'react-native-swipeout';
import axios from 'axios';
import back from '../Assest/image/back.png';
import cam1 from '../Assest/Icon/camera.jpg';
import del from '../Assest/image/delete.png';

function toTitleCase(str) {
    return str.replace(
        /\w\S*/g,
        txt => txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(),
    );
}

class CartView extends Component {
    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);

        this.state = {
            item: [
                {id: 1, name: 'Camera IP', price: '7.900.000đ'},
                {id: 2, name: 'Camera IP', price: '7.900.000đ'},
                {id: 3, name: 'Camera IP', price: '7.900.000đ'},
                {id: 4, name: 'Camera IP', price: '7.900.000đ'},
            ],
            activekey: null,
            deleteRowkey: null,
        };
    }

    refresh = deletekey => {
        this.setState(preState => {
            return {
                deleteRowkey: deletekey,
            };
        });
    };

    showcart() {
        const swipeSetting = {
            autoClose: true,
            onClose: (secID, rowID, direction) => {
            },
            onOpen: (secID, rowID, direction) => {
            },
            right: [
                {
                    onPress: () => {
                        const deleteRow = this.state.activekey;
                        Alert.alert(
                            'Thông báo',
                            'Bạn có chắc muốn xóa sản phẩm khỏi giỏ hàng',
                            [
                                {
                                    text: 'Không',
                                    onPress: () => console.log('Hủy xóa'),
                                    style: 'Hủy',
                                },
                                {
                                    text: 'Có',
                                    onPress: () => {
                                        this.state.item.splice(this.state.id, 1);
                                        this.refresh();
                                    },
                                },
                            ],
                            {cancelable: true},
                        );
                    },
                    text: 'Xoá sản phẩm',
                    type: 'delete',
                },
            ],
            rowId: this.props.index,
            sectionId: 1,
        };
        return this.state.item.map(data => (
            <Swipeout {...swipeSetting}>
                <View style={{borderWidth: 5, borderColor: 'white'}}>
                    <View style={styles.product}>
                        <Image source={cam1} style={styles.productImage}/>
                        <View style={styles.mainRight}>
                            <View
                                style={{justifyContent: 'space-between', flexDirection: 'row'}}>
                                <Text style={styles.txtName}>{data.name}</Text>
                                <TouchableOpacity>
                                    <Image source={del} style={{width: 20, height: 20}}/>
                                </TouchableOpacity>
                            </View>
                            <View>
                                <Text style={styles.txtPrice}>{data.price}</Text>
                            </View>
                            <View style={styles.productController}>
                                <View style={styles.numberOfProduct}>
                                    <TouchableOpacity style={styles.number1}>
                                        <Text>-</Text>
                                    </TouchableOpacity>
                                    <Text style={styles.number}>{1}</Text>
                                    <TouchableOpacity style={styles.number2}>
                                        <Text>+</Text>
                                    </TouchableOpacity>
                                </View>
                                <TouchableOpacity
                                    style={styles.showDetailContainer}
                                    onPress={this.props.onOpen}
                                    onPress={() =>
                                        this.props.navigation.push('ProductDetail', {data})
                                    }>
                                    <Text style={styles.txtShowDetail}>Xem thêm</Text>
                                </TouchableOpacity>
                            </View>
                        </View>
                    </View>
                </View>
            </Swipeout>
        ));
    }

    render() {
        const {main, checkoutButton, checkoutTitle, wrapper} = styles;

        return (
            <View style={wrapper}>
                <View>
                    <Text style={styles.headerTitle}>Giỏ hàng</Text>
                </View>

                <ScrollView style={main}>{this.showcart()}</ScrollView>
                <View style={styles.bottom}>
                    <View style={styles.mgg}>
                        <TouchableOpacity>
                            <Text
                                style={{
                                    fontFamily: 'Quicksand-Bold',
                                    color: 'white',
                                    fontSize: 16,
                                }}>
                                Mã giảm giá
                            </Text>
                        </TouchableOpacity>
                        <TextInput style={styles.input} placeholder="Nhập mã giảm giá"/>
                    </View>
                    <TouchableOpacity style={checkoutButton}>
                        <Text style={checkoutTitle}>THANH TOÁN ĐƠN HÀNG</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }
}

const {width} = Dimensions.get('window');
const text = (width + 90) / 2;
const input = (width + 100) / 2;
const styles = StyleSheet.create({
    input: {
        borderRadius: 10,
        height: 35,
        width: input,
        paddingRight: 10,
        paddingLeft: 10,
        marginLeft: 10,
        backgroundColor: 'white',
        color: '#333137',
        fontFamily: 'Quicksand-Bold',
        fontSize: 14,
    },
    mgg: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 10,
        marginRight: 10,
        marginLeft: 10,
    },
    bottom: {
        backgroundColor: '#F13233',
        paddingTop: 15,
        borderRadius: 15,
        marginBottom: 10,
        marginLeft: 10,
        marginRight: 10,
    },
    number2: {
        paddingLeft: 10,
        paddingRight: 10,
        borderWidth: 1,
        borderColor: 'black',
        borderTopRightRadius: 5,
        borderBottomRightRadius: 5,
    },
    number1: {
        paddingLeft: 10,
        paddingRight: 10,
        borderWidth: 1,
        borderColor: 'black',
        borderTopLeftRadius: 5,
        borderBottomLeftRadius: 5,
    },
    number: {
        paddingLeft: 10,
        paddingRight: 10,
        borderTopWidth: 1,
        borderBottomWidth: 1,
        borderColor: 'black',
    },
    wrapper: {
        flex: 1,
        backgroundColor: 'white',
    },
    checkoutButton: {
        height: 40,
        margin: 10,
        marginTop: 0,
        backgroundColor: 'white',
        borderRadius: 2,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 10,
    },
    main: {
        width,
    },
    checkoutTitle: {
        color: '#343D46',
        fontSize: 15,

        fontWeight: 'bold',
        fontFamily: 'Quicksand-Bold',
    },
    product: {
        flexDirection: 'row',
        padding: 7,
        borderRadius: 10,
        borderWidth: 2,
        backgroundColor: 'white',
        borderColor: '#F13233',
    },
    productImage: {
        width: 100,
        height: 80,
        flex: 1,
        resizeMode: 'center',
        borderRadius: 30,
    },
    mainRight: {
        flex: 3,
        justifyContent: 'space-between',
    },
    productController: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    numberOfProduct: {
        flex: 1,
        flexDirection: 'row',
        paddingLeft: 20,
    },
    txtName: {
        paddingLeft: 23,
        width: text,
        color: '#343D46',
        fontSize: 15,
        fontWeight: '400',
        fontFamily: 'Quicksand-Bold',
    },
    txtPrice: {
        paddingLeft: 20,
        color: 'red',
        fontSize: 20,
        fontWeight: '400',
        fontFamily: 'Quicksand-Bold',
    },
    txtShowDetail: {
        color: 'white',
        paddingRight: 5,
        paddingLeft: 5,
        borderRadius: 3,
        backgroundColor: '#F13233',
        fontSize: 12,
        fontWeight: '400',
        textAlign: 'right',
        fontFamily: 'Quicksand-SemiBold',
    },
    showDetailContainer: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'flex-end',
    },
    header: {
        height: 50,
        backgroundColor: '#76A466',
        alignItems: 'center',
        justifyContent: 'space-between',
        flexDirection: 'row',
        paddingHorizontal: 10,
    }, // eslint-disable-line
    headerTitle: {
        color: '#333137',
        fontSize: 27,
        marginLeft: 10,
        marginTop: 10,
        marginBottom: 20,
        fontFamily: 'Quicksand-Bold',
    },
});

export default CartView;
