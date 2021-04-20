import React from 'react';
import "../../extra/styles/home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import HomeTopSection from "./HomeTopSection";

export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            test: 1
        };
    }

    async componentDidMount() {
        document.title = "بلبلستان";
    }

    render() {
        return (
            <div>
                <Header/>
                <HomeTopSection/>

                <br/>
                <Footer/>
            </div>
        );
    }

}