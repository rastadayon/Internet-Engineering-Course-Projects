import React from "react";
import "../../assets/styles/schedule-styles.css";

export default class ScheduleIndex extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    getCourseStyle(addedStyle) {
        let style = "schedule-index sched-item " + addedStyle + " ";
        switch (this.props.offering ? this.props.offering.course.type : "") {
            case "Umumi":
                return style + "general-sched";
            case "Takhasosi":
                return style + "specialized-sched";
            case "Asli":
                return style + "required-sched";
            case "Paaye":
                return style + "basic-sched";

            default:
                return ""
        }
        
    }

    translateCourseType(finglishType) {
        switch(finglishType) {
            case "Umumi":
                return "عمومی";
            case "Takhasosi":
                return "تخصصی";
            case "Asli":
                return "اصلی";
            case "Paaye":
                return "پایه";

            default:
                return ""
        }
    }

    translateTime(englishTime) { 
        const farsiDigits = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹', '۱۰'];
        return englishTime.replace(/[0-9]/g, function (d) {
            return farsiDigits[d];
        });
    }

    render() {
        return (
            <div className="col-day">
                <div className="schedule-index">
                    <div className={this.getCourseStyle(this.props.style ? this.props.style : "")}>
                        <div>
                            {this.props.offering ? this.translateTime(this.props.offering.classTime.time) : ''}
                        </div>
                        <div className="bold">
                            {this.props.offering ? this.props.offering.course.name : ''}
                        </div>
                        <div className="bold">
                            {this.props.offering ? this.translateCourseType(this.props.offering.course.type) : ''}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}