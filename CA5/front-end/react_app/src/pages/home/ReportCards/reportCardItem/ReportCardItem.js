import React from "react";
import {enToFaNumber} from "../../../../utils/utils"

import "./reportcardItem-styles.css"

function getStatusText(grade) {
    if(grade >= 10)
        return 'قبول'
    else if (grade < 10)
        return 'مردود'
    else
        return 'نامشخص'
}

function getStatusClass(grade, defaultClass) {
    if(grade >= 10)
        return defaultClass + ' pass'
    else if (grade < 10)
        return defaultClass + ' fail'
    else
        return defaultClass + ' unknown'
}

function ReportCardItem(props) {
    // console.log(key)
    // var key = key + 1;
    // console.log(key)
    if(props.reportDataItem)
        console.log(props.reportDataItem)
    // console.log(props)
    return(
        <div className="report-card-item row">
            <div className="report-index col-sm-1">
                <span>
                    ?
                </span>
            </div>
            <div className="course-id col-sm-2">
                <span>
                    {props.reportDataItem? enToFaNumber(props.reportDataItem.code) : '-'}
                </span>
            </div>
            <div className="course-name col-sm-4">
                <span>
                {props.reportDataItem? enToFaNumber(props.reportDataItem.courseName) : '-'}
                </span>
            </div>
            <div className="course-unit col-sm-2">
                <span>
                {props.reportDataItem? enToFaNumber(props.reportDataItem.units) + ' ' : '-'}
                واحد 
                </span>
            </div>
            <div className="status-box-grade col-sm-2">
                <div className={getStatusClass(props.reportDataItem.grade, "status")}>
                    <span>
                    {props.reportDataItem? getStatusText(props.reportDataItem.grade) : '-'}
                    </span>
                </div>
            </div>
            <div className={getStatusClass(props.reportDataItem.grade, "grade col-sm-1")}>
                <span>
                {props.reportDataItem? enToFaNumber(props.reportDataItem.grade) : '-'}
                </span>
            </div>
        </div>
    );
}

export default ReportCardItem;