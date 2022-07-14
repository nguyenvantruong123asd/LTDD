import React, {Component} from 'react';
import {
    Text,
    View,
    ScrollView,
    StyleSheet,
    Dimensions,
    TouchableOpacity,
    TextInput,
    Image,
} from 'react-native';
import NumberFormat from 'react-number-format';
import axios from 'axios';
import cam1 from '../Assest/image/cam1.jpg';
import Cart1 from '../Assest/Icon/cart.png';
import logo1 from '../Assest/Icon/samsung.png';

const {width} = Dimensions.get('window');
const productW = (width - 50) / 2;
const productI = (productW / 380) * 300;

export default class Catagories extends Component {
    static navigationOptions = {
        header: null,
    };

    constructor(props) {
        super(props);

        this.state = {
            logo: [
                {
                    key: 1,
                    name: 'Samsung',
                },
                {
                    key: 2,
                    name: 'Nokia',
                },
            ],

            item: [
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
            title: [
                {
                    key: 1,
                    name1: 'Camera IP',
                    name2: ' - an ninh, chống trộm',
                },
                {
                    key: 2,
                    name1: 'Camera IP',
                    name2: ' - an ninh, chống trộm',
                },
                {
                    key: 3,
                    name1: 'Camera IP',
                    name2: ' - an ninh, chống trộm',
                },
                {
                    key: 4,
                    name1: 'Camera IP',
                    name2: ' - an ninh, chống trộm',
                },
            ],
            persons: [],
            listpro: [],
        };
    }

    componentDidMount() {
        axios
            .get(`https://jsonplaceholder.typicode.com/users`)
            .then(res => {
                const persons = res.data;
                this.setState({persons});
            })

            .catch(error => console.log(error));


    }

    showitem() {
        return this.state.logo.map(data => (
            <TouchableOpacity>
                <Text style={styles.titleText}>{data.name}</Text>
            </TouchableOpacity>
        ));
    }

    showlist() {
        return this.state.persons.map(data => (
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

    showtitle() {
        return this.state.title.map(data => (
            <View style={styles.wrapper1}>
                <TouchableOpacity
                    onPress={this.props.onOpen}
                    onPress={() => this.props.navigation.push('ListProduct')}>
                    <View style={{flex: 1, flexDirection: 'row', alignItems: 'center'}}>
                        <Text style={styles.text}>{data.name1}</Text>
                        <Text style={styles.text1}>{data.name2}</Text>
                    </View>
                </TouchableOpacity>
                <View style={{flex: 3, marginBottom: 3}}>
                    <ScrollView horizontal={true} showsHorizontalScrollIndicator={false}>
                        {this.showlist()}
                    </ScrollView>
                </View>
            </View>
        ));
    }

    render() {
        const item = this.showitem();
        const list = this.showlist();
        const title = this.showtitle();

        return (
            <View style={{flex: 1, backgroundColor: '#D7D7D7'}}>
                <View style={{height: 51, backgroundColor: '#F13233', padding: 10}}>
                    <View style={styles.wrapper5}>
                        <TextInput style={styles.input5} placeholder="Tìm kiếm"/>
                        <TouchableOpacity
                            onPress={this.props.onOpen}
                            onPress={() => this.props.navigation.push('Cart')}>
                            <Image style={styles.icon5} source={Cart1}/>
                        </TouchableOpacity>
                    </View>
                </View>
                <View>
                    <View style={styles.titleContainer}>
                        <ScrollView
                            horizontal={true}
                            showsHorizontalScrollIndicator={false}>
                            {item}
                        </ScrollView>
                    </View>
                </View>
                <ScrollView>
                    <View>{title}</View>
                </ScrollView>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    titleText: {
        marginRight: 3,
        marginLeft: 3,
        marginBottom: 4,
        paddingRight: 4,
        paddingBottom: 2,
        paddingLeft: 4,
        fontSize: 13,
        color: '#BFBFBF',
        fontFamily: 'Quicksand-Medium',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#F13233',
        borderRadius: 4,
    },
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
        width: '87%',
        backgroundColor: 'white',
        borderRadius: 5,
        fontFamily: 'Quicksand-Medium',
    },
    text1: {
        fontSize: 18,
        fontFamily: 'Quicksand-Bold',
        paddingBottom: 10,
        color: '#757575',
    },
    textName: {
        marginVertical: 5,
        paddingLeft: 10,
        paddingRight: 10,
        color: 'white',
        fontFamily: 'Quicksand-SemiBold',
        fontSize: 14,
        borderRadius: 10
    },
    text: {
        fontSize: 20,
        color: '#F13233',
        fontFamily: 'Quicksand-Bold',
        paddingBottom: 10,
    },
    textPrice: {
        paddingLeft: 10,
        color: 'red',
        fontFamily: 'Quicksand-Bold',
        fontSize: 18,
        backgroundColor: 'white',
        paddingBottom: 4,
        paddingTop: 4,
        borderRadius: 10
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

    titleContainer: {
        backgroundColor: 'white',
        margin: 8,
        marginBottom: 4,
        padding: 5,
        paddingBottom: 2,
        borderRadius: 7,
        flexDirection: 'row',
        flexWrap: 'wrap',
    },
    titleText: {
        marginRight: 3,
        marginLeft: 3,
        marginBottom: 4,
        paddingRight: 4,
        paddingBottom: 2,
        paddingLeft: 4,
        fontSize: 16,
        color: '#343D46',
        fontFamily: 'Quicksand-Bold',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#F13233',
        borderRadius: 4,
    },
    wrapper1: {
        backgroundColor: 'white',
        margin: 8,
        marginLeft: 0,
        marginRight: 0,
        marginBottom: 4,
        shadowColor: '#2E272B',
        shadowOffset: {width: 0, height: 3},
        shadowOpacity: 0.2,
        padding: 5,
    },
});
