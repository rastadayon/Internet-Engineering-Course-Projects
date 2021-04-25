import * as React from "react";
import "../../assets/styles/courses-styles.css";

export default class SubmitBar extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }
     
     getRows(courses) {
        /*const slots = ["۸:۰۰ - ۷:۰۰", "۹:۰۰ - ۸:۰۰", "۱۰:۰۰ - ۹:۰۰", "۱۱:۰۰ - ۱۰:۰۰",
                        "۱۲:۰۰ - ۱۱:۰۰", "۱۳:۰۰ - ۱۲:۰۰", "۱۴:۰۰ - ۱۳:۰۰",
                        "۱۵:۰۰ - ۱۴:۰۰", "۱۶:۰۰ - ۱۵:۰۰", "۱۷:۰۰ - ۱۶:۰۰",
                        "۱۸:۰۰ - ۱۷:۰۰"];

        var items = [];
        for (var i = 0; i < slots.length; i++) {
             items.push( <ScheduleItem slot={slots[i]} 
                courses={courses} index={i}/> );
         }
         return items;*/
     }

     toFarsiNumber(n) {
        const farsiDigits = ['۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹', '۱۰'];
        return farsiDigits[n-1];
    }

     render() {
         return (
             <div className="submit-bar col-sm-12">
                <div className="col-unit-count unit-count">
                    <div className="bold-item">
                        <span>
                            تعداد واحد ثبت شده: ۱۵
                        </span>
                    </div>
                </div>
                <div className="col-blank">
                    <span>
                        &nbsp;
                    </span>
                </div>
                <div className="col-refresh submit-box refresh">
                    <div className="clickable">
                        <span>
                            <i className="icon flaticon-refresh-arrow"></i>
                        </span>
                    </div>
                </div>
                <div className="col-submit submit-box">
                    <div className="clickable">
                        <span>
                            ثبت نهایی
                        </span>
                    </div>
                </div>
            </div>
         );
     }
}