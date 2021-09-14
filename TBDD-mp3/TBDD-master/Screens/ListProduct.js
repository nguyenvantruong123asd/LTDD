import React, {Component} from 'react';
import {
  Text,
  View,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  Image,
  Dimensions,
} from 'react-native';
import back from '../Assest/image/back.png';
import cam1 from '../Assest/image/cam2.jpg';

// import listdata from './Date/ListPro.js'
const {height} = Dimensions.get('window');
const {width} = Dimensions.get('window');
const productW = (width - 60) / 2;
const productI = (productW / 380) * 300;

export default class ListProduct extends Component {
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
      title: [
        {
          key: 1,
          name: 'Camera IP',
        },
        {
          key: 2,
          name: 'Camera Vintage',
        },
        {
          key: 3,
          name: 'Camera An Ninh',
        },
        {
          key: 4,
          name: 'Canon',
        },
        {
          key: 5,
          name: 'Camera Nokia',
        },
      ],
    };
  }
  showlist() {
    return this.state.listdata.map(data => (
      <TouchableOpacity
        key={data.key}
        onPress={this.props.onOpen}
        onPress={() => this.props.navigation.push('ProductDetail',{data})}>
        <View style={styles.product}>
          <Image source={cam1} style={styles.image3} />
          <Text style={styles.textName}>{data.name}</Text>
          <Text style={styles.textPrice}>{data.price}</Text>
        </View>
      </TouchableOpacity>
    ));
  }
  showtitle() {
    return this.state.title.map(data => (
      <TouchableOpacity key={data.key}>
        <Text style={styles.titleText}>{data.name}</Text>
      </TouchableOpacity>
    ));
  }
  render() {
    const lists = this.showlist();
    const title = this.showtitle();
    console.log();
    return (
      <View style={styles.wrapper}>
        <View style={styles.header}>
          <TouchableOpacity
            onPress={this.props.onOpen}
            onPress={() => this.props.navigation.goBack()}>
            <Image source={back} style={styles.back} />
          </TouchableOpacity>
          <View style={{flexDirection: 'row', alignItems: 'baseline', marginBottom:10}}>
            <Text style={styles.text}>Camera IP</Text>
            <Text style={styles.text1}> - an ninh, chống trộm</Text>
          </View>

          <View style={styles.titleContainer}>{title}</View>
        </View>

        <View style={{flex: 10}}>
          <ScrollView>
            <View style={{paddingRight: 10}}>
              <View style={styles.body}>{lists}</View>
            </View>
          </ScrollView>
        </View>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  text1: {
    fontSize: 18,
    fontFamily: 'Quicksand-SemiBold',
    paddingBottom: 10,
    color: '#757575',
  },
  text: {
    fontSize: 24,
    color: '#F13233',
    fontFamily: 'Quicksand-Bold',
    paddingBottom: 10,
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
    borderWidth: 3,
    paddingBottom: 4,
    paddingTop: 4,
    borderColor: '#F13233',
    borderBottomRightRadius: 10,
    borderBottomLeftRadius: 10,
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
    paddingBottom: 10,
  },
  product: {
    width: productW,
    backgroundColor: '#F13233',
    borderRadius: 10,
    margin: 5,
    marginBottom: 10,
  },
  textInfo: {
    fontSize: 15,
    color: '#6D6E6A',
    fontFamily: 'Quicksand-Bold',
  },
  textInfo1: {
    fontSize: 16,
    color: 'red',
    fontFamily: 'Quicksand-Bold',
  },
  container: {
    flex: 1,
    backgroundColor: 'white',
    padding: 10,
  },
  header: {},
  wrapper: {
    borderRadius: 10,
    backgroundColor: 'white',
    paddingHorizontal: 10,
    paddingRight: 0,
    flex: 1,
  },
  back: {
    width: 25,
    height: 25,
    marginTop: 10,
  },
  titleStyle: {
    color: '#343D46',
    fontSize: 23,
    fontFamily: 'Quicksand-SemiBold',
  },
  producImage: {
    width: 100,
    height: 80,
    borderTopLeftRadius: 10,
    borderBottomLeftRadius: 10,
  },
  productInfo: {
    justifyContent: 'space-between',
    backgroundColor: 'white',
    width: 237,
    borderTopRightRadius: 10,
    borderBottomRightRadius: 10,
    paddingLeft: 5,
    paddingRight: 5,
    marginLeft: 5,
    borderWidth: 2,
    borderColor: '#76A466',
  },
  lastRowInfo: {
    justifyContent: 'space-between',
    flexDirection: 'row',
  },
  producContainer: {
    flexDirection: 'row',
    padding: 5,
    paddingLeft: 0,
    paddingTop: 12,
    marginBottom: 7,
  },
  titleContainer: {
    backgroundColor: 'white',

    marginBottom: 10,
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
    fontSize: 14,
    color: '#343D46',
    fontFamily: 'Quicksand-Medium',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#F13233',
    borderRadius: 4,
  },
});
