import React from 'react';
import "../../extra/styles/home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";

export default class UnitSelection extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            test: 1,
            firstOption: "خانه",
            secondOption: "برنامه هفتگی",
            firstRoute: "",
            secondRoute: "/schedule"
        };
    }

    async componentDidMount() {
        document.title = "انتخاب واحد";
    }

    render() {
        return (
            <div>
                <Header firstOption={this.state.firstOption}
                        secondOption={this.state.secondOption}
                        firstRoute={this.state.firstRoute}
                        secondRoute={this.state.secondRoute}/>
                

                <br/>
                <Footer/>
            </div>
        );
    }

}