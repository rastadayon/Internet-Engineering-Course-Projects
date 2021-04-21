import React from 'react';
import "./home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import HomeTopSection from "./HomeTopSection";
import ProfileInfo from './ProfileInfo/ProfileInfo';
import API from '../../apis/api';


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            studentInfo: undefined,
            test: 1,
            firstOption: "انتخاب واحد",
            secondOption: "برنامه هفتگی",
            firstRoute: "/courses",
            secondRoute: "/schedule"
        }
        this.updateStudentInfo = this.updateStudentInfo.bind(this);
    }

    updateStudentInfo() {
        API.get("student/").then(
            jsonData => {
                this.setState({studentInfo: jsonData.data});
                console.log(this.state.studentInfo.id)
        }).catch(error => {
            console.log('rid')
            window.location.href = "http://localhost:3000/login"
        })
    }

    async componentDidMount() {
        document.title = "Home";
        this.updateStudentInfo()
    }

    render() {
        return (
            <div className="main-container">
                <Header firstOption={this.state.firstOption}
                        secondOption={this.state.secondOption}
                        firstRoute={this.state.firstRoute}
                        secondRoute={this.state.secondRoute}/>
                <HomeTopSection/>
                <div className="container-fluid text-center">
                    <div className="main row">
                    {/* <ProfileInfo studentInfo = {this.state.studentInfo}/> */}
                    </div>
                </div>
                <Footer/>
            </div>
        );
    }

}