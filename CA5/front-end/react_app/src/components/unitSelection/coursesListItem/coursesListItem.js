import React from 'react'
import './coursesListItem-styles.css'
import {enToFaNumber} from "../../../utils/utils"
import { toast } from 'react-toastify';
import API from '../../../apis/api';
import { Tooltip } from '@material-ui/core';



function getIcon(capacity, signedup) {
    if (signedup < capacity) {
        return "flaticon-add"
    }
    else {
        return "flaticon-clock-circular-outline"
    }
}

function getIconClass(capacity, signedup) {
    if (signedup < capacity) {
        return "add-sign"
    }
    else {
        return "full-sign"
    }
}

function getAction(capacity, signedup) {
    if (signedup < capacity) {
        return "add"
    }
    else {
        return "wait"
    }
}

function getCapacityStyle(capacity, signedup) {
    let style = "course-index bold-item ";
    if (signedup >= capacity) {
        style = style + "unavailable";
    }
    return style;
}

function getCourseType(type) {
    if (type == "Umumi")
        return "عمومی"
    else if (type == "Asli")
        return "اصلی"
    else if (type == "Takhasosi")
        return "تخصصی"
    else if (type == "Paaye")
        return "پایه"
}

function getCourseTypeClass(type) {
    if (type == "Umumi")
        return "general"
    else if (type == "Asli")
        return "required"
    else if (type == "Takhasosi")
        return "specialized"
    else if (type == "Paaye")
        return "basic"
}

function translateNumbersInText(text) { 
        const farsiDigits = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
        return text.toString().replace(/[0-9]/g, function (d) {
            return farsiDigits[d];
        });
    }

function selectCourse(props) {
    console.log(props.course.classCode)

    var requestParam = new FormData();
    requestParam.append('courseCode', props.course.courseCode);
    requestParam.append('classCode', props.course.classCode);

    var action = getAction(props.course.capacity, props.course.signedUp)

    API.put('offering/' + action, requestParam).then(resp => {
        if(resp.status == 200) {
            if (resp.data === "OK") {
                props.updateSelections();
                console.log("done");
            }
            else {
                if (action == "wait")
                    toast.error(translateNumbersInText(resp.data))
            }

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

function CoursesListItem(props) {
    return(
        <div className="course-item">
            <div className="row no-gutters">
                <div className="col-add edit-option">
                    <div className={["edit", getIconClass(props.course.capacity, props.course.signedUp), "clickable"].join(' ')}>
                        <span>
                            <i className={getIcon(props.course.capacity, props.course.signedUp)} 
                                onClick={() => selectCourse(props)}></i>
                        </span>
                    </div>
                </div>
                <div className="col-code-2">
                    <div className="course-index code">
                        <span>
                            {enToFaNumber(props.course.courseCode) + '-' + enToFaNumber(props.course.classCode)}
                        </span>
                    </div>
                </div>
                <div className="col-capacity">
                    <div className={getCapacityStyle(props.course.capacity, props.course.signedUp)}>
                        <span>
                            {enToFaNumber(props.course.signedUp) + '/' + enToFaNumber(props.course.capacity)}
                        </span>
                    </div>
                </div>
                <div className="col-type">
                    <div className="course-index course-status">
                        <div className={["type-box", getCourseTypeClass(props.course.type)].join(' ')}>
                            <span>
                                {getCourseType(props.course.type)}
                            </span>
                        </div>
                    </div>
                </div>
                <div className="col-name-2">
                    <div className="course-index">
                        <span>
                            {props.course.name}
                        </span>
                    </div>
                </div>
                <div className="col-instructor-2">
                    <div className="course-index">
                        <span>
                            {props.course.instructor}
                        </span>
                    </div>
                </div>
                <div className="col-units">
                    <div className="course-index bold-item">
                        <span>
                            {enToFaNumber(props.course.units)}
                        </span>
                    </div>
                </div>
                {/* <div className="col-explain">
                    <div className="selection-units">
                        <span>
                            &nbsp;
                        </span>
                    </div>
                </div> */}
            </div>
        </div>
    );
}

export default CoursesListItem;