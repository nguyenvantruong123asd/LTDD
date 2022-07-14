import React, {Component} from 'react';
import {
    AppRegistry,
    FlatList,
    Dimensions,
    StyleSheet,
    Text,
    View,
    Image,
    TextInput,
    Alert,
    TouchableOpacity,
} from 'react-native';
import flatListData from './flatListData.js';
import Swipeout from 'react-native-swipeout';
import cam1 from '../../Assest/Icon/camera.jpg';
import search from '../../Assest/Icon/search.png';
import create from '../../Assest/Icon/create.png';
import del from '../../Assest/Icon/delete.png';
import edit from '../../Assest/Icon/edit.png';

class FlatListItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeRowKey: null,
        };
    }

    render() {
        const swipeSettings = {
            autoClose: true,
            onClose: (secId, rowId, direction) => {
                if (this.state.activeRowKey != null) {
                    this.setState({activeRowKey: null});
                }
            },
            onOpen: (secId, rowId, direction) => {
                this.setState({activeRowKey: this.props.item.key});
            },
            right: [
                {
                    onPress: () => {
                        const deleteRow = this.state.activekey;

                        this.props.navigation.push('Edit');
                    },
                    text: 'Sửa',
                    backgroundColor: 'green',
                    type: 'delete',
                },
                {
                    onPress: () => {
                        const deletingRow = this.state.activeRowKey;
                        Alert.alert(
                            'Alert',
                            'Are you sure you want to delete ?',
                            [
                                {
                                    text: 'No',
                                    onPress: () => console.log('Cancel Pressed'),
                                    style: 'cancel',
                                },
                                {
                                    text: 'Yes',
                                    onPress: () => {
                                        flatListData.splice(this.props.index, 1);
                                        //Refresh FlatList !
                                        this.props.parentFlatList.refreshFlatList(deletingRow);
                                    },
                                },
                            ],
                            {cancelable: true},
                        );
                    },
                    text: 'Xóa',
                    type: 'delete',
                },
            ],
            rowId: this.props.index,
            sectionId: 1,
        };
        return (
            <View style={{borderWidth: 5, borderColor: 'white'}}>
                <Swipeout {...swipeSettings}>
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
                                <View style={{width: '55%'}}>
                                    <Text style={styles.txtName3}>{this.props.item.name}</Text>
                                    <Text style={styles.txtName3}>{this.props.item.price}</Text>
                                    <Text style={styles.txtName3}>{this.props.item.nsx}</Text>
                                    <Text style={styles.txtName3}>{this.props.item.loai}</Text>
                                </View>
                                <View style={{width: '15%'}}>
                                    <TouchableOpacity>
                                        <Image
                                            style={{
                                                width: 20,
                                                height: 20,
                                                marginLeft: 25,
                                                marginBottom: 20,
                                            }}
                                            source={del}
                                        />
                                    </TouchableOpacity>
                                    <TouchableOpacity>
                                        <Image style={{width: 40, height: 40}} source={edit}/>
                                    </TouchableOpacity>
                                </View>
                            </View>
                        </View>
                    </View>
                </Swipeout>
            </View>
        );
    }
}

const {width} = Dimensions.get('window');
const text = (width + 90) / 2;
const input = (width + 100) / 2;

export default class ShowProduct extends Component {
    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);
        this.state = {
            deletedRowKey: null,
        };
    }

    refreshFlatList = deletedKey => {
        this.setState(prevState => {
            return {
                deletedRowKey: deletedKey,
            };
        });
    };

    render() {
        return (
            <View style={styles.wrapper}>
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
                                onPress={() => this.props.navigation.push('Create')}>
                                <Image style={styles.icon5} source={create}/>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>
                <FlatList
                    data={flatListData}
                    renderItem={({item, index}) => {
                        return (
                            <FlatListItem
                                item={item}
                                index={index}
                                parentFlatList={this}></FlatListItem>
                        );
                    }}></FlatList>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    flatListItem: {
        color: 'white',
        padding: 10,
        fontSize: 16,
    },
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
