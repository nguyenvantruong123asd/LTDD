import React, {Component} from 'react';
import {View, Text, StyleSheet, Image, Dimensions} from 'react-native';
import map from '../Assest/Icon/map.png';

import phoneIcon from '../Assest/Icon/phone.png';
import mailIcon from '../Assest/Icon/mail.png';
import messageIcon from '../Assest/Icon/message.png';
import locationIcon from '../Assest/Icon/location.png';

class Contact extends Component {
    render() {
        const {
            mapContainer,
            wrapper,
            infoContainer,
            rowInfoContainer,
            imageStyle,
            infoText,
        } = styles;
        return (
            <View style={wrapper}>
                <View style={mapContainer}>
                    <Image
                        style={{flex: 1, alignSelf: 'stretch', width: undefined}}
                        source={map}
                    />
                </View>
                <View style={infoContainer}>
                    <View style={rowInfoContainer}>
                        <Image source={locationIcon} style={imageStyle}/>
                        <Text style={infoText}>90 Le Thi Rieng/ Ben Thanh Dist</Text>
                    </View>
                    <View style={rowInfoContainer}>
                        <Image source={phoneIcon} style={imageStyle}/>
                        <Text style={infoText}>(+84) 01694472176</Text>
                    </View>
                    <View style={rowInfoContainer}>
                        <Image source={mailIcon} style={imageStyle}/>
                        <Text style={infoText}>khoaphamtraining@gmail.com</Text>
                    </View>
                    <View style={[rowInfoContainer, {borderBottomWidth: 0}]}>
                        <Image source={messageIcon} style={imageStyle}/>
                        <Text style={infoText}>(+84) 09877067707</Text>
                    </View>
                </View>
            </View>
        );
    }
}

const {width} = Dimensions.get('window');
const styles = StyleSheet.create({
    wrapper: {flex: 1, backgroundColor: '#F6F6F6'},
    mapStyle: {
        width: width - 40,
        height: 230,
        alignSelf: 'center',
        alignItems: 'center',
        justifyContent: 'center',
    },
    mapContainer: {
        justifyContent: 'center',
        alignItems: 'center',
        flex: 1,
        backgroundColor: '#FFFFFF',
        margin: 10,
        borderRadius: 2,
        shadowColor: '#3B5458',
        shadowOffset: {width: 0, height: 3},
        shadowOpacity: 0.2,
    },
    infoContainer: {
        padding: 10,
        flex: 1,
        backgroundColor: '#FFF',
        margin: 10,
        marginTop: 0,
        borderRadius: 2,
        shadowColor: '#3B5458',
        shadowOffset: {width: 0, height: 3},
        shadowOpacity: 0.2,
    },
    rowInfoContainer: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderColor: '#D6D6D6',
    },
    imageStyle: {
        width: 25,
        height: 25,
    },
    infoText: {
        fontFamily: 'Quicksand-Bold',
        color: '#76A466',
        fontWeight: '500',
        fontSize: 15
    },
});

export default Contact;
