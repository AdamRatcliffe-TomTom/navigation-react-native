import React from 'react';
import {ViewStyle, requireNativeComponent} from 'react-native';

interface Props {
  style?: ViewStyle;
}

const MapView = (props: Props) => {
  return <ReactMapView {...props} style={props.style} />;
};

const ReactMapView = requireNativeComponent('RCTMapView');

export default MapView;
