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
             <div className="course-selection-wrapper">
                <div className="course-selection row">
                    <div className="col-label label">
                        <span>
                            دروس انتخاب شده
                        </span>
                    </div>
                    <div className="col-hidden">
                    </div>
                    <div className="selections">

                        <SelectionHeader />
                        
                        <SelectionItem />

    
                        <div className="selection-item">
                            <div className="row no-gutters">
                                <div className="col-trash">
                                    <div className="trash clickable">
                                        <span>
                                            <i className="icon flaticon-trash-bin"></i>
                                        </span>
                                    </div>
                                </div>
                                <div className="col-status">
                                    <div className="selection-index course-status first-index">
                                        <div className="status-box not-submitted">
                                            <span>
                                                ثبت نهایی نشده
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-code">
                                    <div className="selection-index">
                                        <span>
                                            ۸۱۰۱۲۹۹-۰۱
                                        </span>
                                    </div>
                                </div>
                                <div className="col-name">
                                    <div className="selection-index">
                                        <span>
                                            مهندسی نرم‌افزار
                                        </span>
                                    </div>
                                </div>
                                <div className="col-instructor">
                                    <div className="selection-index">
                                        <span>
                                            رامتین خسروی
                                        </span>
                                    </div>
                                </div>
                                <div className="col-1">
                                    <div className="selection-units">
                                        <span>
                                            ۳
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div> 
    
                        <div className="selection-item">
                            <div className="row no-gutters">
                                <div className="col-trash">
                                    <div className="trash clickable">
                                        <span>
                                            <i className="icon flaticon-trash-bin"></i>
                                        </span>
                                    </div>
                                </div>
                                <div className="col-status">
                                    <div className="selection-index course-status first-index">
                                        <div className="status-box waiting">
                                            <span>
                                                در انتظار
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-code">
                                    <div className="selection-index">
                                        <span>
                                            ۸۱۰۱۶۱۲-۰۱
                                        </span>
                                    </div>
                                </div>
                                <div className="col-name">
                                    <div className="selection-index">
                                        <span>
                                            نظریه زبان‌ها و ماشین‌ها
                                        </span>
                                    </div>
                                </div>
                                <div className="col-instructor">
                                    <div className="selection-index">
                                        <span>
                                            حکیمه فدایی
                                        </span>
                                    </div>
                                </div>
                                <div className="col-1">
                                    <div className="selection-units">
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