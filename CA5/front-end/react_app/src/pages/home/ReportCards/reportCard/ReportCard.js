import React from "react";
import {enToFaNumber} from "../../../../utils/utils"

import "../reportCardItem/reportcardItem-styles.css"
import ReportCardItem from '../reportCardItem/ReportCardItem'

function ReportCard(props) {
    // console.log(props.reportData)
    return(
    <div className="report-card col-sm-12">
    
        <div className="col-report label-report">
            <div className="semester">
                <span>
                    کارنامه - ترم 
                    {' ' + enToFaNumber(props.reportData[1].semester)}
                </span>
            </div>
        </div>
        <div className="col-h">
        </div>
        
        {
            props.reportData == undefined ?
            <div className="text-center mt-5">
                <div className="spinner-border" role="status"></div>
            </div> :
            Object.entries(props.reportData[1].grades).map((reportDataItem, index) => <ReportCardItem key={index} reportDataItem={reportDataItem[1]}/>)
        }

        <div className="GPA">
            <span>
                معدل: 
                {' ' + enToFaNumber(props.reportData[1].gpa.toFixed(2))}
            </span>
        </div>


</div>);
}

export default ReportCard;