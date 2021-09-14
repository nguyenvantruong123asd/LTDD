import React, {Component} from 'react';
import {
  Text,
  View,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  Image,
} from 'react-native';
import back from '../Assest/image/back.png';
import camera from '../Assest/image/cam2.jpg';

export default class Search extends Component {
  static navigationOptions = {
    header: null,
  };
  render() {
    return (
      <View style={styles.container}>
        <View style={styles.wrapper}>
          
          <ScrollView>
            <View style={styles.producContainer}>
              <Image style={styles.producImage} source={camera} />
              <View style={styles.productInfo}>
                <Text style={styles.textInfo}>Camera IP - Nokia</Text>
                <Text style={styles.textInfo1}>9.900.000</Text>
                <View style={styles.lastRowInfo}>
                  <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-Medium',marginBottom: 4}}>Còn hàng</Text>
                  <View />
                  <TouchableOpacity>
                    <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-SemiBold',marginBottom: 4}}>Thêm vào giỏ</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </View>
             <View style={styles.producContainer}>
              <Image style={styles.producImage} source={camera} />
              <View style={styles.productInfo}>
                <Text style={styles.textInfo}>Camera IP - Nokia</Text>
                <Text style={styles.textInfo1}>9.900.000</Text>
                <View style={styles.lastRowInfo}>
                  <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-Medium',marginBottom: 4}}>Còn hàng</Text>
                  <View />
                  <TouchableOpacity>
                    <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-SemiBold',marginBottom: 4}}>Thêm vào giỏ</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </View>
             <View style={styles.producContainer}>
              <Image style={styles.producImage} source={camera} />
              <View style={styles.productInfo}>
                <Text style={styles.textInfo}>Camera IP - Nokia</Text>
                <Text style={styles.textInfo1}>9.900.000</Text>
                <View style={styles.lastRowInfo}>
                  <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-Medium',marginBottom: 4}}>Còn hàng</Text>
                  <View />
                  <TouchableOpacity>
                    <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-SemiBold',marginBottom: 4}}>Thêm vào giỏ</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </View>
             <View style={styles.producContainer}>
              <Image style={styles.producImage} source={camera} />
              <View style={styles.productInfo}>
                <Text style={styles.textInfo}>Camera IP - Nokia</Text>
                <Text style={styles.textInfo1}>9.900.000</Text>
                <View style={styles.lastRowInfo}>
                  <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-Medium',marginBottom: 4}}>Còn hàng</Text>
                  <View />
                  <TouchableOpacity>
                    <Text style={{fontSize: 14,color: "#6D6E6A", fontFamily: 
                  'Quicksand-SemiBold',marginBottom: 4}}>Thêm vào giỏ</Text>
                  </TouchableOpacity>
                </View>
              </View>
        
            </View>
          </ScrollView>
        </View>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  textInfo:{
    fontSize: 15,
    color: "#6D6E6A",
    fontFamily: 'Quicksand-Bold',
  },
  textInfo1:{
    fontSize: 16,
    color: "red",
    fontFamily: 'Quicksand-Bold',
  },
  container: {
    flex: 1,
    backgroundColor: '#CDDEC7',
    padding: 10,
  },
  header: {
    height: 40,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  wrapper: {
    borderRadius: 10,
    backgroundColor: 'white',
    paddingHorizontal: 10,
    flex: 1,
  },
  back: {
    width: 25,
    height: 25,
  },
  titleStyle: {
    color: '#76A466',
    fontSize: 20,
    fontFamily: 'Quicksand-Bold',
  },
  producImage: {
    width: 100,
    height: 80,
    borderTopLeftRadius: 10,
    borderBottomLeftRadius: 10,

  },
  productInfo: {
    justifyContent: 'space-between',
    backgroundColor: "white",
    width: 237,
    borderTopRightRadius: 10,
    borderBottomRightRadius: 10,
    paddingLeft: 5,
    paddingRight: 5,
    marginLeft: 5,
    borderWidth: 2,
    borderColor: "#76A466"
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
    marginBottom: 7
    
  },
});
