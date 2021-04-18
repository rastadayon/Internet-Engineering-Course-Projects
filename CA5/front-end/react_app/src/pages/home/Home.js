import React from 'react';
import "../../extra/styles/home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";

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
                

                <br/>
                <Footer/>
            </div>
        );
    }

}