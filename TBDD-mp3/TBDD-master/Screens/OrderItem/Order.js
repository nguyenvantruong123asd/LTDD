import React, {Component} from 'react';
import {
  View,
  TouchableOpacity,
  Text,
  Image,
  StyleSheet,
  Dimensions,
  ScrollView,
} from 'react-native';
import back from '../../Assest/image/back.png';
import cam3 from '../../Assest/image/cam1.jpg';

export default class OrderHistory extends Component {
  constructor(props) {
    super(props);
    this.state = {
      item: [
        {
          key: 1,
          name: 'Camera IP',
          time: '2017-04-19 08:30:08',
          status: 'Đã thanh toán',
          price: 9000000,
        },
        {
          key: 1,
          name: 'Camera IP',
          time: '2017-04-19 08:30:08',
          status: 'Đã thanh toán',
          price: 9000000,
        },
        {
          key: 1,
          name: 'Camera IP',
          time: '2017-04-19 08:30:08',
          status: 'Đã thanh toán',
          price: 9000000,
        },
        {
          key: 1,
          name: 'Camera IP',
          time: '2017-04-19 08:30:08',
          status: 'Đã thanh toán',
          price: 9000000,
        },
        {
          key: 1,
          name: 'Camera IP',
          time: '2017-04-19 08:30:08',
          status: 'Đã thanh toán',
          price: 9000000,
        },
        {
          key: 1,
          name: 'Camera IP',
          time: '2017-04-19 08:30:08',
          status: 'Đã thanh toán',
          price: 9000000,
        },
      ],
    };
  }
  static navigationOptions = {
    header: null,
  };
  showItem() {
    return this.state.item.map(data => (
      <View
        style={{
          flexDirection: 'row',
          marginLeft: 10,
          justifyContent: 'space-between',
          alignItems: 'center',
        }}>
        <View
          style={{
            backgroundColor: 'white',
            height: width / 3.5,
            marginTop:-10,
            justifyContent: 'center',
          }}>
          <Image
            source={cam3}
            style={{
              width: 80,
              height: width / 5,
              marginLeft: 10,
              marginRight: 5,
            }}
          />
        </View>
        <View style={styles.orderRow}>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}>
            <Text style={{color: '#9A9A9A', fontFamily: 'Quicksand-Bold'}}>
              Tên mặt hàng:
            </Text>
            <Text style={{fontFamily: 'Quicksand-Bold', color: '#343D46'}}>
              {data.name}
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}>
            <Text style={{color: '#9A9A9A', fontFamily: 'Quicksand-Bold'}}>
              Thời gian đặt:
            </Text>
            <Text style={{fontFamily: 'Quicksand-Bold',color: '#343D46'}}>{data.time}</Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}>
            <Text style={{color: '#9A9A9A',fontFamily: 'Quicksand-Bold'}}>
              Trạng thái:
            </Text>
            <Text style={{color: '#F13233', fontFamily: 'Quicksand-Bold'}}>
              {data.status}
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}>
            <Text style={{color: '#9A9A9A', fontFamily: 'Quicksand-Bold'}}>
              Tổng tiền:
            </Text>
            <Text style={{color: '#F13233', fontWeight: 'bold'}}>
              {data.price}
            </Text>
          </View>
        </View>
      </View>
    ));
  }
  render() {
    const {
      wrapper,
      header,
      headerTitle,
      backIconStyle,
      body,
      orderRow,
    } = styles;
    return (
      <View style={wrapper}>
        <View>
          <TouchableOpacity
            onPress={this.props.onOpen}
            onPress={() => this.props.navigation.goBack()}>
            <Image
              source={back}
              style={{width: 20, height: 20, margin: 10, marginBottom: 10}}
            />
          </TouchableOpacity>
          <Text style={styles.headerTitle1}>Chờ xác nhận</Text>
        </View>
        <View style={body}>
          <ScrollView>{this.showItem()}</ScrollView>
        </View>
      </View>
    );
  }
}

const {width} = Dimensions.get('window');

const styles = StyleSheet.create({
  headerTitle1: {
    color: '#333137',
    fontSize: 22,
    marginLeft: 10,
    marginBottom: 10,
    fontFamily: 'Quicksand-SemiBold',
  },
  wrapper: {flex: 1, backgroundColor: '#fff'},
  headerTitle: {fontFamily: 'Avenir', color: '#fff', fontSize: 20},
  backIconStyle: {width: 30, height: 30},
  body: {flex: 10, backgroundColor: '#F6F6F6', flexDirection: 'row'},
  orderRow: {
    height: width / 3.5,
    backgroundColor: '#FFF',
    margin: 10,
    marginTop: 0,
    marginLeft: 0,
    width: width - 110,
    padding: 10,
    borderRadius: 2,
    justifyContent: 'space-around',
  },
});
