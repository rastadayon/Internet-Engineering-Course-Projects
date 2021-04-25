import * as React from "react";
import "../../assets/styles/courses-styles.css";
import SubmitBar from "./SubmitBar";
import SelectionHeader from "./SelectionHeader";
import SelectionItem from "./SelectionItem";

export default class Selection extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }
     
     getRows(courses) {
        /*var items = [];
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
             <div class="course-selection-wrapper">
                <div class="course-selection row">
                    <div class="col-label label">
                        <span>
                            دروس انتخاب شده
                        </span>
                    </div>
                    <div class="col-hidden">
                    </div>
                    <div class="selections">

                        <SelectionHeader />
                        
                        <SelectionItem />

    
                        <div class="selection-item">
                            <div class="row no-gutters">
                                <div class="col-trash">
                                    <div class="trash clickable">
                                        <span>
                                            <i class="icon flaticon-trash-bin"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-status">
                                    <div class="selection-index course-status first-index">
                                        <div class="status-box not-submitted">
                                            <span>
                                                ثبت نهایی نشده
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-code">
                                    <div class="selection-index">
                                        <span>
                                            ۸۱۰۱۲۹۹-۰۱
                                        </span>
                                    </div>
                                </div>
                                <div class="col-name">
                                    <div class="selection-index">
                                        <span>
                                            مهندسی نرم‌افزار
                                        </span>
                                    </div>
                                </div>
                                <div class="col-instructor">
                                    <div class="selection-index">
                                        <span>
                                            رامتین خسروی
                                        </span>
                                    </div>
                                </div>
                                <div class="col-1">
                                    <div class="selection-units">
                                        <span>
                                            ۳
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div> 
    
                        <div class="selection-item">
                            <div class="row no-gutters">
                                <div class="col-trash">
                                    <div class="trash clickable">
                                        <span>
                                            <i class="icon flaticon-trash-bin"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-status">
                                    <div class="selection-index course-status first-index">
                                        <div class="status-box waiting">
                                            <span>
                                                در انتظار
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-code">
                                    <div class="selection-index">
                                        <span>
                                            ۸۱۰۱۶۱۲-۰۱
                                        </span>
                                    </div>
                                </div>
                                <div class="col-name">
                                    <div class="selection-index">
                                        <span>
                                            نظریه زبان‌ها و ماشین‌ها
                                        </span>
                                    </div>
                                </div>
                                <div class="col-instructor">
                                    <div class="selection-index">
                                        <span>
                                            حکیمه فدایی
                                        </span>
                                    </div>
                                </div>
                                <div class="col-1">
                                    <div class="selection-units">
                                        <span>
                                            ۳
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </div>

                    <SubmitBar />

                </div>
            </div>
         );
     }
}