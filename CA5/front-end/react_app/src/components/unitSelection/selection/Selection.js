import * as React from "react";
import "./selection-styles.css";
import SubmitBar from "../submitBar/SubmitBar";
import SelectionHeader from "../selectionHeader/SelectionHeader";
import SelectionItem from "../selectionItem/SelectionItem";

export default class Selection extends React.Component{

     constructor(props) {
         super(props);
         this.state = {    
            updateSelections : this.props.updateSelections
        }
     }
     
     getRows(selections) {
        var items = [];
        for (var i = 0; i < selections.submittedOfferings.offerings.length; i++) {
             items.push( <SelectionItem updateSelections={this.props.updateSelections ? 
                this.props.updateSelections : ""} courseStatus={"submitted"}
                offering={selections.submittedOfferings.offerings[i]}/> );
         }

         for (var j = 0; j < selections.selectedOfferings.offerings.length; j++) {
             items.push( <SelectionItem updateSelections={this.props.updateSelections ? 
                this.props.updateSelections : ""} courseStatus={"not-submitted"}
                offering={selections.selectedOfferings.offerings[j]}/> );
         }

         for (var k = 0; k < selections.waitingOfferings.offerings.length; k++) {
             items.push( <SelectionItem updateSelections={this.props.updateSelections ? 
                this.props.updateSelections : ""} courseStatus={"waiting"}
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

                    <SubmitBar updateSelections={this.state.updateSelections ? 
                                this.state.updateSelections : ""} 
                                selections={this.props.selections ? 
                                    this.props.selections : ''}/>

                </div>
            </div>
         );
     }
}