import React, {Component} from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  Dimensions,
  ScrollView,
  TouchableOpacity,
} from 'react-native';
import axios from 'axios';
import img1 from '../Assest/image/cam1.jpg';
import img2 from '../Assest/image/cam2.jpg';

const back = require('../Assest/image/back.png');
const cart = require('../Assest/Icon/cart1.png');

export default class ProductDetail extends Component {
  static navigationOptions = {
    header: null,
  };

  constructor(props) {
    super(props);
    this.state = {
      item: [
        {
          key: 1,
          name: 'Camera IP N34PX424',
          price: '7.900.000đ',
        },
        
      ],
      persons: [],
    };
    console.log(this.props);

  }

  
  goBack() {
    const {navigator} = this.props;
    navigator.pop();
  }
  showitem() {
    console.log("aaaaaaaaaaa")
    console.log(this.props)
    const person = this.props.navigation.state.params.data;
    return (
      <View style={styles.cardStyle}>
        <View style={{flex: 10}}>
          <View style={styles.header}>
            <TouchableOpacity
              onPress={this.props.onOpen}
              onPress={() => this.props.navigation.goBack()}>
              <Image style={styles.backStyle} source={back} />
            </TouchableOpacity>
            <TouchableOpacity
              onPress={this.props.onOpen}
              onPress={() => this.props.navigation.push('Cart')}>
              <Image style={styles.cartStyle} source={cart} />
            </TouchableOpacity>
          </View>
          <View
            style={{
              paddingRight: 10,
              marginBottom: 10,
              flexDirection: 'row',
              justifyContent: 'space-between',
              alignItems: 'center',
            }}>
            <Text style={styles.textName}>{person.name}</Text>
            <View
              style={{
                flexDirection: 'row',
                justifyContent: 'space-between',
                alignItems: 'center',
              }}>
              <View>
                <Text style={styles.text}>giảm -- </Text>
              </View>
              <View
                style={{
                  alignItems: 'center',
                  justifyContent: 'center',
                  height: 40,
                  width: 40,
                  backgroundColor: 'red',
                  borderRadius: 20,
                }}>
                <Text style={styles.discount}>45%</Text>
              </View>
            </View>
          </View>
          <ScrollView>
            <View style={styles.imageContainer}>
              <ScrollView
                style={{flexDirection: 'row', height: swiperHeight}}
                horizontal
                showsHorizontalScrollIndicator={false}>
                <Image source={img1} style={styles.productImageStyle} />
                <Image source={img2} style={styles.productImageStyle} />
              </ScrollView>
            </View>
            <View style={styles.footer}>
              <View style={styles.titleContainer}>
                <Text style={styles.textMain}>
                  <Text style={styles.textBlack}>Giá</Text>
                  <Text style={styles.textHighlight}>: </Text>
                  <Text style={styles.textSmoke1}>9.900.000đ</Text>
                  <Text style={styles.textSmoke2}> -- 7.900.000đ</Text>
                </Text>
              </View>
              <View style={styles.descContainer}>
                <Text style={styles.descStyle}>
                  A delicate layer of eyelash lace brings dreamy elegance to
                  this piece, while smooth, lightweight lining feels luxurious
                  against your skin. We love it with heels for a look that fits
                  in on date night, or with cool booties to add an edge.
                </Text>
                <View
                  style={{
                    marginTop: 15,
                    padding: 10,
                    borderRadius: 10,
                    borderWidth: 2,
                    borderColor: '#6D6E6A',
                  }}>
                  <View
                    style={{justifyContent: 'center', alignItems: 'center'}}>
                    <Text
                      style={{
                        fontFamily: 'Quicksand-Bold',
                        fontSize: 17,
                        color: '#F13233',
                        paddingBottom: 10,
                      }}>
                      Thông tin cấu hình
                    </Text>
                  </View>
                  <View
                    style={{
                      flexDirection: 'row',
                      justifyContent: 'space-between',
                    }}>
                    <Text style={styles.info1}>Tên máy</Text>
                    <Text style={styles.info2}>Camera IP bla bla</Text>
                  </View>
                </View>
              </View>
            </View>
          </ScrollView>
        </View>
        <View style={styles.add}>
          <View style={styles.buttonAdd1}>
            <Text style={styles.text1}>Mua ngay</Text>
          </View>
          <View style={styles.buttonAdd2}>
            <Text style={styles.text2}>Thêm vào giỏ</Text>
          </View>
        </View>
      </View>
    );
  }
  render() {
    const {
      wrapper,
      cardStyle,
      header,
      footer,
      backStyle,
      imageContainer,
      cartStyle,
      textBlack,
      textSmoke,
      textHighlight,
      textMain,
      titleContainer,
      descContainer,
      productImageStyle,
      descStyle,
      txtMaterial,
      txtColor,
    } = styles;
  return <View style={wrapper}>{this.showitem()}</View>;
  }
}

const {width} = Dimensions.get('window');
const swiperWidth = width / 1.8 - 30;
const swiperHeight = (swiperWidth * 452) / 361;
const widthText = (width + 140) / 2;
const widthBut = (width - 50) / 2;
const widthinfo1 = (width + 50) / 2;

const styles = StyleSheet.create({
  info1: {
    fontFamily: 'Quicksand-Medium',
    fontSize: 14,
    color: '#999A9C',
  },
  info2: {
    fontFamily: 'Quicksand-SemiBold',
    fontSize: 14,
    width: widthinfo1,
    color: '#6D6E6A',
    paddingLeft: 10,
  },
  text1: {
    fontFamily: 'Quicksand-Bold',
    fontSize: 19,
    color: '#F13233',
  },
  text2: {
    fontFamily: 'Quicksand-Bold',
    fontSize: 19,
    color: 'white',
  },
  buttonAdd1: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
    borderWidth: 3,
    borderColor: '#F13233',
    height: 45,
    backgroundColor: 'white',
    width: widthBut,
    margin: 10,
    marginRight: 0,
  },
  buttonAdd2: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#FF632F',
    height: 45,
    backgroundColor: 'white',
    width: widthBut,
    margin: 10,
    backgroundColor: '#F13233',
  },
  add: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  text: {
    fontFamily: 'Quicksand-Medium',
    color: 'red',
    fontSize: 14,
  },
  discount: {
    fontFamily: 'Quicksand-Bold',
    color: 'white',
    fontSize: 18,
  },
  textName: {
    paddingLeft: 10,
    paddingRight: 10,
    color: '#3F3F46',
    fontFamily: 'Quicksand-Bold',
    fontSize: 20,
    width: widthText,
  },
  wrapper: {
    flex: 1,
    backgroundColor: '#D6D6D6',
  },
  cardStyle: {
    flex: 1,
    backgroundColor: '#FFFFFF',
    borderRadius: 5,
    marginHorizontal: 10,
    marginVertical: 10,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    height: 50,
    padding: 10,
  },
  cartStyle: {
    width: 25,
    height: 25,
  },
  backStyle: {
    width: 25,
    height: 25,
  },
  productStyle: {
    width: width / 2,
    height: width / 2,
  },
  footer: {
    flex: 6,
  },
  imageContainer: {
    flex: 5,
    alignItems: 'center',
    flexDirection: 'row',
    marginHorizontal: 10,
    marginTop: 20,
  },
  textMain: {
    paddingLeft: 20,
    marginVertical: 10,
  },
  textMain1: {
    paddingLeft: 20,
  },
  textBlack: {
    fontFamily: 'Avenir',
    fontSize: 20,
    fontWeight: 'bold',
    color: '#3F3F46',
  },
  textSmoke1: {
    fontFamily: 'Quicksand-Bold',
    fontSize: 20,
    color: '#3F3F46',
    textDecorationLine: 'line-through',
  },
  textSmoke2: {
    fontFamily: 'Quicksand-Bold',
    fontSize: 20,
    color: 'red',
  },
  textHighlight: {
    fontFamily: 'Avenir',
    fontSize: 20,
    color: '#7D59C8',
  },
  titleContainer: {
    borderBottomWidth: 1,
    borderColor: '#F6F6F6',
    marginHorizontal: 20,
    paddingBottom: 5,
  },
  descContainer: {
    margin: 10,
    paddingTop: 10,
    paddingHorizontal: 10,
  },
  descStyle: {
    color: '#AFAFAF',
  },
  linkStyle: {
    color: '#7D59C8',
  },
  productImageStyle: {
    width: swiperWidth,
    height: swiperHeight,
    marginHorizontal: 5,
  },
  mainRight: {
    justifyContent: 'space-between',
    alignSelf: 'stretch',
    paddingLeft: 20,
  },
  txtColor: {
    color: '#C21C70',
    fontSize: 15,
    fontWeight: '400',
    fontFamily: 'Avenir',
  },
  txtMaterial: {
    color: '#C21C70',
    fontSize: 15,
    fontWeight: '400',
    fontFamily: 'Avenir',
  },
});
