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
                console.log('in updateStudent info')
                console.log(this.state.studentInfo.id)
        }).catch(error => {
            if(error.response.status != 200)
                window.location.href = "http://localhost:3000/login"
            console.log('rid')
        })
    }

    componentDidMount() {
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
                    <ProfileInfo studentInfo = {this.state.studentInfo}/>
                    {/* {this.ProfileInfo()} */}
                    </div>
                </div>
                <Footer/>
            </div>
        );
    }

    // ProfileInfo() {
    //     // console.log(studentInfo)
    //     // console.log(typeof props.studentInfo)
    //     if(this.state.studentInfo != undefined)
    //         console.log(this.state.studentInfo.id)
    //     return (
    //         <div className="std-info col-sm-3">
    //             {/* <img className="avatar" src="../assets/images/robot.png" alt="avatar"/> */}
    //             <ul>
    //                 <li>نام: <span className="std-name">{this.state.studentInfo.id}</span></li>
    //                 <li>شماره دانشجویی: <span className="std-id">۸۱۰۱۹۶۰۰۰</span></li>
    //                 <li>تاریخ تولد: <span className="std-birthday">۱۳۷۸/۱/۱</span></li>
    //                 <li>معدل کل: <span className="std-gpa">۱۷.۸۲</span></li>
    //                 <li>واحد گذرانده: <span className="std-units">۹۴.۰۰</span></li>
    //                 <li>دانشکده: <span className="std-campus">پردیس دانشکده‌های فنی</span></li>
    //                 <li>رشته: <span className="std-major">مهندسی کامپیوتر</span></li>
    //                 <li>مقطع: <span className="std-level>">کارشناسی</span></li>
    //                 <li><span className="std-status">مشغول به تحصیل</span></li>
    //             </ul>

    //         </div>
    //     );
    // }

}