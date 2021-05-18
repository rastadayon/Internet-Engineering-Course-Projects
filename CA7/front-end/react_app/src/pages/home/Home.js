import React from 'react';
import "./home-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import HomeTopSection from "../../components/home/homeTopSection/HomeTopSection";
import ProfileInfo from '../../components/home/profileInfo/ProfileInfo';
import ReportCards from '../../components/home/reportCards/ReportCards'
import API from '../../apis/api';
import authHeader from '../../services/auth-header.js'


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            studentInfo: undefined,
            test: 1
        }
        this.updateStudentInfo = this.updateStudentInfo.bind(this);
        this.reportCards = this.updateReportCards.bind(this);
    }

    updateStudentInfo() {
        API.get("student/", { headers: authHeader() }).then(
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
                <Header firstOption={"انتخاب واحد"}
                        secondOption={"برنامه هفتگی"}
                        firstRoute={"/courses"}
                        secondRoute={"/schedule"}/>
                <main>
                <HomeTopSection/>
                <div className="container-fluid text-center">
                    <div className="main row">
                    <ProfileInfo studentInfo = {this.state.studentInfo}/>
                    <ReportCards reportCards = {this.state.reportCards}/>
                    </div>
                </div>
                </main>
                <Footer/>
            </div>
        );
    }

}