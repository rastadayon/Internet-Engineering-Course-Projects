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
     
     getRows(selections) {
        var items = [];
        for (var i = 0; i < selections.submittedOfferings.offerings.length; i++) {
             items.push( <SelectionItem courseStatus={"submitted"}
                offering={selections.submittedOfferings.offerings[i]}/> );
         }

         for (var j = 0; j < selections.selectedOfferings.offerings.length; j++) {
             items.push( <SelectionItem courseStatus={"not-submitted"}
                offering={selections.selectedOfferings.offerings[j]}/> );
         }

         for (var k = 0; k < selections.waitingOfferings.offerings.length; k++) {
             items.push( <SelectionItem courseStatus={"waiting"}
                offering={selections.waitingOfferings.offerings[k]}/> );
         }
         return items;
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
                        
                        {this.props.selections ? this.getRows(this.props.selections) : ''}
                    </div>

                    <SubmitBar />

                </div>
            </div>
         );
     }
}