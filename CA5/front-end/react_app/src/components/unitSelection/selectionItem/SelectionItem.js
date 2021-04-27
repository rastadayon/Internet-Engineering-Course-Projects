import * as React from "react";
import "./selectionItem-styles.css";
import { toast } from 'react-toastify';
import API from '../../../apis/api';

export default class SelectionItem extends React.Component{

     constructor(props) {
         super(props);
         this.state = {    
            course : this.props.offering
        };

        this.editSelection = this.editSelection.bind(this);
     }

     getStatusStyle(status) {
        let style = "status-box " + status;
        return style;
    }

    translateStatus(englisStatus) {
        switch(englisStatus) {
            case "submitted":
                return "ثبت شده";
            case "not-submitted":
                return "ثبت نهایی نشده";
            case "waiting":
                return "در انتظار";

            default:
                return ""
        }
    }

    getOfferingCode(offering) {
        if (offering) {
            let code = offering.course.courseCode + "-0" + offering.classCode;
            return this.translateNumbersInText(code);
        }
        return "";
    }

    translateNumbersInText(text) { 
        const farsiDigits = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
        return text.toString().replace(/[0-9]/g, function (d) {
            return farsiDigits[d];
        });
    }

    editSelection() {

        console.log(this.state.course.classCode)

        var requestParam = new FormData();
        requestParam.append('courseCode', this.state.course.courseCode);
        requestParam.append('classCode', this.state.course.classCode);

        API.put('offering/remove', requestParam).then(resp => {
            if(resp.status == 200) {
                console.log("done");
                this.props.updateSelections();

            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                console.log(error)
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3000/login"
                }
            })
    }
        
     render() {
         return (
             <div className="selection-item">
                <div className="row no-gutters">
                    <div className="col-trash">
                        <div className="trash clickable">
                            <span>
                                    <i className="icon flaticon-trash-bin" onClick={this.editSelection}></i>
                            </span>
                        </div>
                    </div>
                    <div className="col-status">
                        <div className="selection-index course-status first-index">
                            <div className={this.getStatusStyle(this.props.courseStatus ? this.props.courseStatus : "")}>
                                <span>
                                    {this.props.courseStatus ? this.translateStatus(this.props.courseStatus) : ''}
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-code">
                        <div className="selection-index">
                            <span>
                                {this.props.offering ? this.getOfferingCode(this.props.offering) : ''}
                            </span>
                        </div>
                    </div>
                    <div className="col-name">
                        <div className="selection-index">
                            <span>
                                {this.props.offering ? this.props.offering.course.name : ''}
                            </span>
                        </div>
                    </div>
                    <div className="col-instructor">
                        <div className="selection-index">
                            <span>
                                {this.props.offering ? this.props.offering.instructor : ''}
                            </span>
                        </div>
                    </div>
                    <div className="col-1">
                        <div className="selection-units">
                            <span>
                                {this.props.offering ? this.translateNumbersInText(this.props.offering.course.units) : ''}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
         );
     }
}