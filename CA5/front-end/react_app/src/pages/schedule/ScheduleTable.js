import * as React from "react";
import "../../assets/styles/schedule-styles.css";
import ScheduleItem from "./ScheduleItem";
import TableHeader from "./TableHeader";

export default class ScheduleTable extends React.Component{

     constructor(props) {
         super(props);
         this.state = {
             items : []
         }

         const slots = ["۹:۰۰ - ۸:۰۰", "۱۰:۰۰ - ۹:۰۰", "۱۱:۰۰ - ۱۰:۰۰",
                        "۱۲:۰۰ - ۱۱:۰۰", "۱۳:۰۰ - ۱۲:۰۰", "۱۴:۰۰ - ۱۳:۰۰",
                        "۱۵:۰۰ - ۱۴:۰۰", "۱۶:۰۰ - ۱۵:۰۰", "۱۷:۰۰ - ۱۶:۰۰",
                        "۱۸:۰۰ - ۱۷:۰۰"];

         for (var i = 0; i < slots.length; i++) {
             this.state.items.push( <ScheduleItem slot={slots[i]} /> );
         }
     }


     render() {
         return (
            <div class="schedule-wrapper">
                <div class="schedule row">
                    <div class="col-calendar schedule-label">
                        <div class="calendar">
                            <span>
                                <i class="icon flaticon-calendar"></i>
                            </span>
                        </div>
                    </div>
                    <div class="col-plan schedule-label">
                        <span>
                            برنامه هفتگی
                        </span>
                    </div>
                    <div class="col-hidden">
                    </div>
                    <div class="col-term schedule-label">
                        <span>
                            ترم ۶
                        </span>
                    </div>
                    <div class="list">
                        <div class="sections">
                            <TableHeader/>
                            {this.state.items}
                        </div>
                    </div>
                </div>
            </div>
         );
     }
}