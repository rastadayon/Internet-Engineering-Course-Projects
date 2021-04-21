import React from 'react';
import "../../assets/styles/schedule-styles.css";
import Header from "../general/Header";
import Footer from "../general/Footer";
import API from '../../apis/api';
import ScheduleTable from './ScheduleTable';


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            schedule: undefined,
            test: 1
        }
        this.updateSchedule = this.updateSchedule.bind(this);
    }

    updateSchedule() {
        API.get("student/schedule").then(
            jsonData => {
                this.setState({schedule: jsonData.data});
                console.log(this.state.schedule.id)
        }).catch(error => {
            console.log('failed')
            window.location.href = "http://localhost:3000/schedule"
        })
    }

    async componentDidMount() {
        document.title = "Schedule";
        this.updateStudentInfo()
    }

    render() {
        return (
            <div className="main-container">
                <Header firstOption={"خانه"}
                        secondOption={"انتخاب واحد"}
                        firstRoute={""}
                        secondRoute={"/courses"}/>
                <ScheduleTable/>
                <Footer/>
            </div>
        );
    }

}