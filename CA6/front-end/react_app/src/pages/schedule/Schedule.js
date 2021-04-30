import React from 'react';
import Header from "../general/Header";
import Footer from "../general/Footer";
import API from '../../apis/api';
import ScheduleTable from "../../components/schedule/scheduleTable/ScheduleTable"
import './schedule-styles.css'


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            scheduleInfo: undefined,
            test: 1
        }
        this.updateScheduleInfo = this.updateScheduleInfo.bind(this);
    }

    updateScheduleInfo() {
        API.get("/schedule/").then(
            jsonData => {
                this.setState({scheduleInfo: jsonData.data});
        }).catch(error => {
            if(error.response.status != 200)
                window.location.href = "http://localhost:3000/login"
            console.log('failed')
        })
    }

    async componentDidMount() {
        document.title = "Schedule";
        this.updateScheduleInfo()
    }

    render() {
        return (
            <div className="main-container">
                <Header firstOption={"خانه"}
                        secondOption={"انتخاب واحد"}
                        firstRoute={""}
                        secondRoute={"/courses"}/>
                <ScheduleTable scheduleInfo = {this.state.scheduleInfo} />
                <Footer/>
            </div>
        );
    }

}