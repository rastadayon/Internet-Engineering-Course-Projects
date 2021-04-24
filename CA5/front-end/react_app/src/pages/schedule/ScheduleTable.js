import * as React from "react";
import "../../assets/styles/schedule-styles.css";
import ScheduleItem from "./ScheduleItem";
import TableHeader from "./TableHeader";

export default class ScheduleTable extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }
     
     getRows(courses) {
        const slots = ["۸:۰۰ - ۷:۰۰", "۹:۰۰ - ۸:۰۰", "۱۰:۰۰ - ۹:۰۰", "۱۱:۰۰ - ۱۰:۰۰",
                        "۱۲:۰۰ - ۱۱:۰۰", "۱۳:۰۰ - ۱۲:۰۰", "۱۴:۰۰ - ۱۳:۰۰",
                        "۱۵:۰۰ - ۱۴:۰۰", "۱۶:۰۰ - ۱۵:۰۰", "۱۷:۰۰ - ۱۶:۰۰",
                        "۱۸:۰۰ - ۱۷:۰۰"];

        var items = [];
        for (var i = 0; i < slots.length; i++) {
             items.push( <ScheduleItem slot={slots[i]} 
                courses={courses} index={i}/> );
         }
         return items;
     }

     toFarsiNumber(n) {
        const farsiDigits = ['۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹', '۱۰'];
        return farsiDigits[n-1];
    }

     render() {
         return (
             <main className="main-sched">
            <div className="schedule-wrapper">
                <div className="schedule row">
                    <div className="col-calendar schedule-label">
                        <div className="calendar">
                            <span>
                                <i className="icon-sched flaticon-calendar"></i>
                            </span>
                        </div>
                    </div>
                    <div className="col-plan schedule-label">
                        <span>
                            برنامه هفتگی
                        </span>
                    </div>
                    <div className="col-hidden-sched">
                    </div>
                    <div className="col-term schedule-label">
                        <span>
                            ترم  {this.props.scheduleInfo ? this.toFarsiNumber(this.props.scheduleInfo.term) : ''}
                        </span>
                    </div>
                    <div className="list-sched">
                        <div className="sections">
                            <TableHeader/>
                            {this.props.scheduleInfo ? this.getRows(this.props.scheduleInfo.offerings) : ''}
                        </div>
                    </div>
                </div>
            </div>
            </main>
         );
     }
}