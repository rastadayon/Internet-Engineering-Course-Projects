import React from "react";
import "../../assets/styles/courses.css";

export default class ScheduleIndex extends React.Component{
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <div class="col-day">
                <div class="course-index">
                    <div class="course-index sched-item one-half-bottom specialized">
                        <div>
                            {this.props.time}
                        </div>
                        <div class="bold-item">
                            {this.props.name}
                        </div>
                        <div class="bold-item">
                            {this.props.type}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}