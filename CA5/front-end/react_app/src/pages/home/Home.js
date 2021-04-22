import React from 'react';
import "./home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import HomeTopSection from "./HomeTopSection";
import ProfileInfo from './ProfileInfo/ProfileInfo';
import ReportCards from './ReportCards/ReportCards'
import API from '../../apis/api';


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            studentInfo: undefined,
            reportCards: undefined,
            test: 1,
            firstOption: "انتخاب واحد",
            secondOption: "برنامه هفتگی",
            firstRoute: "/courses",
            secondRoute: "/schedule"
        }
        this.updateStudentInfo = this.updateStudentInfo.bind(this);
        this.reportCards = this.updateReportCards.bind(this);
    }

    updateStudentInfo() {
        API.get("student/").then(
            jsonData => {
                this.setState({studentInfo: jsonData.data});
                // console.log('in updateStudent info')
                // console.log(this.state.studentInfo.id)
        }).catch(error => {
            if(error.response.status != 200)
                window.location.href = "http://localhost:3000/login"
            console.log('rid')
        })
    }

    updateReportCards() {
        API.get("student/reportCards").then(
            jsonData => {
                this.setState({reportCards: jsonData.data});
                // console.log(this.state.reportCards)
        }).catch(error => {
            if(error.response.status != 200)
                window.location.href = "http://localhost:3000/login"
            console.log('rid')
        })
    }

    componentDidMount() {
        document.title = "Home";
        this.updateStudentInfo()
        this.updateReportCards()
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
                    <ProfileInfo studentInfo = {this.state.studentInfo}/>
                    <ReportCards reportCards = {this.state.reportCards}/>
                    </div>
                </div>
                <Footer/>
            </div>
        );
    }

}