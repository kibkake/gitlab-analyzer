import React, { useContext } from 'react'
import { Component } from 'react';


export const LanguageScaleContext = React.createContext();

class LanguageScaleProvidor extends Component{
    state= {
        LanguageScale:[],
    }

    setScale(newScale){
        this.setState({
            LanguageScale:newScale
        })

    }
}

