import * as React from "react";
import "../../assets/styles/schedule-styles.css";
import ScheduleIndex from "./ScheduleIndex";

export default class ScheduleItem extends React.Component{

     constructor(props) {
         super(props);
         this.state = {
             days : ["Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"], 
             slots : ["7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
                        "14:00", "15:00", "16:00", "17:00"]
         }
      }

      getColumns(courses, index) {
        var columns = [];
        for (var i = 0; i < this.state.days.length; i++) {
              let values = this.getCourseByStyle(this.state.days[i], index, courses);
                columns.push( <ScheduleIndex style={values[0]} offering={values[1]} /> );
          }
          return columns;
      }

      getCourseByStyle(day, index, courses) {
          let slots = this.state.slots;
         for (let i = 0; i < courses.length; i++) {
             let course = courses[i];
             if (course.classTime.days.includes(day)) {
                let start = course.classTime.time.split("-")[0];
                let end = course.classTime.time.split("-")[1];
                let timeDuration = this.duration(start, end);

                if (timeDuration === 90) {
                    if (start.includes("30") && end === slots[index+1]) 
                        return ["one-half-top", course];
    
                    if (end.includes("30") && start === slots[index]) 
                        return ["one-half-bottom", course];
                }
                if (timeDuration === 120) {
                    if (end === slots[index+1])
                        return ["two-hours", course];
                }
                if (timeDuration === 180) {
                    if (end === slots[index+2])
                        return ["three-hours", course];
                }

             }
         }
         return ["", ""];
     }

     duration(start, end) {
        start = start.split(":");
        end = end.split(":");
        var startDate = new Date(0, 0, 0, start[0], start[1], 0);
        var endDate = new Date(0, 0, 0, end[0], end[1], 0);
        var diff = endDate.getTime() - startDate.getTime();
        var hours = Math.floor(diff / 1000 / 60 / 60);
        diff -= hours * 1000 * 60 * 60;
        var minutes = Math.floor(diff / 1000 / 60);
        return minutes + hours*60;
    }

     render() {
         return (
             <div className="course-item">
                <div className="row no-gutters">
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                {this.props.slot}
                            </span>
                        </div>
                    </div>
                    {this.props.courses ? this.getColumns(this.props.courses, this.props.index) : ''}
                </div>
            </div>
         );
     }
}
