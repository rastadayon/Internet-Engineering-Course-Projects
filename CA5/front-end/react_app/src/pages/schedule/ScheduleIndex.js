import React from "react";
import "../../assets/styles/schedule-styles.css";

export default class ScheduleIndex extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    getCourseStyle(addedStyle) {
        let style = "course-index sched-item " + addedStyle + " ";
        switch (this.props.offering ? this.props.offering.course.type : "") {
            case "Umumi":
                return style + "general";
            case "Takhasosi":
                return style + "specialized";
            case "Asli":
                return style + "required";
            case "Paaye":
                return style + "basic";

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
                <div className="course-index">
                    <div className={this.getCourseStyle(this.props.style ? this.props.style : "")}>
                        <div>
                            {this.props.offering ? this.translateTime(this.props.offering.classTime.time) : ''}
                        </div>
                        <div class="bold-item">
                            {this.props.offering ? this.props.offering.course.name : ''}
                        </div>
                        <div className="bold-item">
                            {this.props.offering ? this.translateCourseType(this.props.offering.course.type) : ''}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}