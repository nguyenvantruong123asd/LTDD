import React, {Component} from 'react';
import {
    Text,
    View,
    StyleSheet,
    Dimensions,
    Image,
    ScrollView,
    TextInput,
    TouchableOpacity,
} from 'react-native';
import NumberFormat from 'react-number-format';
import search from '../Assest/Icon/search.png'

import Cart1 from '../Assest/Icon/cart.png';
import ad1 from '../Assest/image/ad1.jpg'
import Advertise from './Advertise.js';

const {height} = Dimensions.get('window');
const {width} = Dimensions.get('window');
const productW = (width - 50) / 2;
const productI = (productW / 380) * 300;
console.disableYellowBox = true;
import cam1 from '../Assest/image/cam1.jpg';
import cam2 from '../Assest/image/cam2.jpg';
import cam3 from '../Assest/image/cam3.jpg';
import cam4 from '../Assest/image/cam4.jpg';
import cam5 from '../Assest/image/cam5.jpg';
import cam6 from '../Assest/image/cam6.jpg';

export default class Home extends Component {
    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);
        this.state = {
            listdata: [
                {
                    key: 1,
                    name: 'Camera NOKIA',
                    price: 7000000,
                },
                {
                    key: 2,
                    name: 'Camera NOKIA',
                    price: 9000000,
                },
                {
                    key: 3,
                    name: 'Camera NOKIA',
                    price: 7000000,
                },
                {
                    key: 4,
                    name: 'Camera NOKIA',
                    price: 5000000,
                },
            ],
        };
    }

    showlist() {
        return this.state.listdata.map(data => (
            <View style={styles.product}>
                <TouchableOpacity
                    key={data.key}
                    onPress={this.props.onOpen}
                    onPress={() => this.props.navigation.push('ProductDetail', {data})}>
                    <Image source={cam1} style={styles.image3}/>
                    <Text style={styles.textName}>{data.name}</Text>
                </TouchableOpacity>
                <View
                    style={{
                        flexDirection: 'row',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        backgroundColor: 'white',
                        borderWidth: 3,
                        borderColor: '#F13233',
                        borderBottomLeftRadius: 20,
                        borderBottomRightRadius: 10,
                    }}>
                    <NumberFormat
                        value={data.price}
                        displayType={'text'}
                        thousandSeparator={true}
                        renderText={value => (
                            <Text style={styles.textPrice}>{value} đ</Text>
                        )}
                    />
                    <TouchableOpacity>
                        <Image
                            source={require('../Assest/Icon/cart1.png')}
                            style={{marginRight: 5, width: 25, height: 25}}
                        />
                    </TouchableOpacity>
                </View>
            </View>
        ));
    }

    showlistnew() {
        return this.state.listdata.map(data => (
            <View style={styles.product}>
                <TouchableOpacity
                    key={data.key}
                    onPress={this.props.onOpen}
                    onPress={() => this.props.navigation.push('ProductDetail')}>
                    <Image source={cam1} style={styles.image3}/>
                    <Text style={styles.textName}>{data.name}</Text>
                </TouchableOpacity>
                <View
                    style={{
                        flexDirection: 'row',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        backgroundColor: 'white',
                        borderWidth: 3,
                        borderColor: '#F13233',
                        borderBottomLeftRadius: 10,
                        borderBottomRightRadius: 10,
                    }}>
                    <NumberFormat
                        value={data.price}
                        displayType={'text'}
                        thousandSeparator={true}
                        renderText={value => (
                            <Text style={styles.textPrice}>{value} đ</Text>
                        )}
                    />
                    <TouchableOpacity>
                        <Image
                            source={require('../Assest/Icon/cart1.png')}
                            style={{marginRight: 5, width: 25, height: 25}}
                        />
                    </TouchableOpacity>
                </View>
            </View>
        ));
    }

    render() {
        const showlist = this.showlist();
        return (
            <View style={{flex: 1, backgroundColor: '#D7D7D7'}}>
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
                <ScrollView>
                    <View>
                        <View style={styles.wrapper1}>
                            <TouchableOpacity
                                onPress={this.props.onOpen}
                                onPress={() => this.props.navigation.push('Advertise')}>
                                <View style={{flex: 1, flexDirection: 'row'}}>
                                    <Text style={styles.text}>Chương trình khuyến mãi</Text>
                                    <Text style={styles.text1}>--- HOT!!!</Text>
                                </View>
                            </TouchableOpacity>
                            <View style={{flex: 3}}>
                                <ScrollView
                                    horizontal={true}
                                    pagingEnabled={true}
                                    showsHorizontalScrollIndicator={false}>
                                    <View>
                                        <Image source={ad1} style={styles.image}/>
                                    </View>
                                    <View>
                                        <Image source={cam4} style={styles.image}/>
                                    </View>
                                    <View>
                                        <Image source={cam3} style={styles.image}/>
                                    </View>
                                </ScrollView>
                            </View>
                        </View>
                    </View>

                    <View>
                        <View style={styles.wrapper1}>
                            <View
                                style={{
                                    flexDirection: 'row',
                                    justifyContent: 'space-between',
                                }}>
                                <Image source={require('../Assest/image/ad2.png')} style={styles.image1}/>
                                <Image source={require('../Assest/image/ad3.png')} style={styles.image2}/>
                            </View>
                        </View>
                    </View>

                    <View>
                        <View style={styles.container}>
                            <View style={styles.litContainer}>
                                <Text style={styles.text}>SẢN PHẨM BÁN CHẠY</Text>
                                <Text style={styles.text1}>- THÁNG 11</Text>
                            </View>

                            <View style={styles.body}>{showlist}</View>
                        </View>
                    </View>

                    <View>
                        <View style={styles.wrapper1}>
                            <View style={{flex: 1, flexDirection: 'row'}}>
                                <Text style={styles.text}>Sản phẩm mới nổi bậc</Text>
                                <Text style={styles.text1}>-- GIÁ RẺ</Text>
                            </View>
                            <View style={{flex: 4}}>
                                <ScrollView
                                    horizontal={true}
                                    showsHorizontalScrollIndicator={false}>
                                    {this.showlistnew()}

                                </ScrollView>
                            </View>
                        </View>
                    </View>
                </ScrollView>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    wrapper5: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    icon5: {
        height: 30,
        width: 30,
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
        fontFamily: 'Quicksand-Medium',
    },
    wrapper: {
        height: height * 0.3,
        backgroundColor: 'white',
        margin: 10,
        shadowColor: '#2E272B',
        shadowOffset: {width: 0, height: 3},
        shadowOpacity: 0.2,
        padding: 5,
    },
    wrapper1: {
        backgroundColor: 'white',
        marginBottom: 10,
        shadowColor: '#2E272B',
        shadowOffset: {width: 0, height: 3},
        shadowOpacity: 0.2,
        padding: 10,
    },
    image: {
        height: 150,
        width: width - 20,
        borderBottomLeftRadius: 10,
        borderBottomRightRadius: 10,
    },
    text: {
        fontSize: 20,
        color: '#343D46',
        fontFamily: 'Quicksand-Bold',
        paddingBottom: 10,
    },
    text1: {
        fontSize: 20,
        fontFamily: 'Quicksand-Bold',
        paddingBottom: 10,
        color: 'red',
    },
    image1: {
        width: width / 2 + 40,
        height: 111,
        borderBottomLeftRadius: 10,
        borderTopLeftRadius: 10,
    },
    image2: {
        height: 111,
        width: width / 2 - 62,
        borderBottomRightRadius: 10,
        borderTopRightRadius: 10,
    },
    heightI: {
        height: 100,
    },
    container: {
        backgroundColor: '#fff',
        marginBottom: 10,
        padding: 5,
    },
    litContainer: {
        height: 40,
        alignItems: 'center',
        flexDirection: 'row',
        padding: 5,
    },
    product: {
        width: productW,
        backgroundColor: '#F13233',
        borderRadius: 10,
        margin: 5,
    },
    image3: {
        width: productW,
        height: productI,
        borderTopRightRadius: 10,
        borderTopLeftRadius: 10,
    },
    body: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        flexWrap: 'wrap',
        paddingBottom: 5,
    },
    textName: {
        marginVertical: 5,
        paddingLeft: 10,
        paddingRight: 10,
        color: 'white',
        fontFamily: 'Quicksand-SemiBold',
        fontSize: 14,
    },
    textPrice: {
        paddingLeft: 10,
        color: 'red',
        fontFamily: 'Quicksand-Bold',
        fontSize: 18,
        backgroundColor: 'white',
        paddingBottom: 4,
        paddingTop: 4,
        borderRadius: 10,
    },
});
