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
import back from '../../Assest/image/back.png';
import cam1 from '../../Assest/Icon/camera.jpg';
import del from '../../Assest/image/delete.png';
import search from '../../Assest/Icon/search.png';

import Cart1 from '../../Assest/Icon/cart.png';
import {interfaceDeclaration} from '@babel/types';

function toTitleCase(str) {
    return str.replace(
        /\w\S*/g,
        txt => txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(),
    );
}

class ProductAd extends Component {
    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);

        this.state = {
            item: [
                {
                    id: 1,
                    name: 'Camera IP   aaaa',
                    price: '7.900.000đ',
                    nsx: 'HTC',
                    loai: 'Camera An Ninh',
                    giam: 45,
                    hinhanh: '.....',
                },
                {
                    id: 2,
                    name: 'Camera IP    bbbb',
                    price: '7.900.000đ',
                    nsx: 'HTC',
                    loai: 'Camera An Ninh',
                    giam: '45',
                    hinhanh: '.....',
                },
                {
                    id: 3,
                    name: 'Camera IP    cccc',
                    price: '7.900.000đ',
                    nsx: 'HTC',
                    loai: 'Camera An Ninh',
                    giam: '45',
                    hinhanh: '.....',
                },
            ],
            person: [],
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
        var index = 2;
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

                        this.props.navigation.push('Edit');
                    },

                    text: 'Sửa',
                    backgroundColor: 'green',
                },
                {
                    onPress: () => {
                        const deleteRow = this.state.activekey;

                        Alert.alert(
                            'Thông báo',
                            'Bạn có chắc muốn xóa sản phẩm khỏi giỏ hàng',
                            [
                                {
                                    text: 'Không',
                                },
                                {
                                    text: 'Có',
                                    onPress: () => {
                                        this.state.item.splice(index, 1);

                                        this.refresh();
                                    },
                                },
                            ],
                            {cancelable: true},
                        );
                    },


                    text: 'Xoá',
                    type: 'delete',
                },
            ],

            rowId: this.props.index,
            sectionId: 1,
        };

        return this.state.item.map(data => (
            <View style={{borderWidth: 5, borderColor: 'white'}}>
                <Swipeout {...swipeSetting}>
                    <View style={styles.product}>
                        <Image source={cam1} style={styles.productImage}/>
                        <View style={styles.mainRight}>
                            <View style={{flexDirection: 'row'}}>
                                <View style={{width: '30%'}}>
                                    <Text style={styles.txtName}>Tên SP:</Text>
                                    <Text style={styles.txtName}>Giá:</Text>
                                    <Text style={styles.txtName}>NSX:</Text>
                                    <Text style={styles.txtName}>Loại hàng:</Text>
                                </View>
                                <View style={{width: '70%'}}>
                                    <Text style={styles.txtName3}>{data.name}</Text>
                                    <Text style={styles.txtName3}>{data.price}</Text>
                                    <Text style={styles.txtName3}>{data.nsx}</Text>
                                    <Text style={styles.txtName3}>{data.loai}</Text>
                                </View>
                            </View>
                        </View>
                    </View>
                </Swipeout>
            </View>
        ));
    }

    render() {
        const {main, checkoutButton, checkoutTitle, wrapper} = styles;

        return (
            <View style={wrapper}>
                <View>
                    <Text style={styles.headerTitle}>Quản lý sản phẩm</Text>
                    <View style={{height: 51, backgroundColor: '#F13233', padding: 10}}>
                        <View style={styles.wrapper5}>
                            <TextInput style={styles.input5} placeholder="Tìm kiếm"/>
                            <TouchableOpacity
                                onPress={this.props.onOpen}
                                onPress={() => this.props.navigation.push('Search')}>
                                <Image style={styles.icon5} source={search}/>
                            </TouchableOpacity>
                            <TouchableOpacity
                                onPress={this.props.onOpen}
                                onPress={() => this.props.navigation.push('Cart')}>
                                <Image style={styles.icon5} source={Cart1}/>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>

                <ScrollView style={main}>{this.showcart()}</ScrollView>
            </View>
        );
    }
}

const {width} = Dimensions.get('window');
const text = (width + 90) / 2;
const input = (width + 100) / 2;
const styles = StyleSheet.create({
    wrapper5: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    input5: {
        height: 30,
        fontSize: 12,
        padding: 1,
        paddingLeft: 3,
        fontSize: 14,
        width: '80%',
        backgroundColor: 'white',
        borderRadius: 5,
    },
    icon5: {
        height: 30,
        width: 30,
    },
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
        marginTop: 30,
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
        paddingLeft: 10,
        color: '#343D46',
        fontSize: 15,
        fontWeight: '400',
        fontFamily: 'Quicksand-SemiBold',
    },
    txtName3: {
        paddingLeft: 10,
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

export default ProductAd;
