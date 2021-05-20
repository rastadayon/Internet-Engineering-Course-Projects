import React from "react";
import {enToFaNumber, sizeOf} from "../../../utils/utils"

import "./reportCards-styles.css"
import ReportCard from '../reportCard/ReportCard'

function ReportCards(props) {
    return (
    <div className="section col-sm-9">
        <div className="report-cards-wrapper">
            {
                props.reportCards == undefined ?
                <div className="text-center mt-5">
                    <div className="spinner-border" role="status"></div>
                </div> :
                Object.entries(props.reportCards).map((item, key) => <ReportCard key={key} reportData={item}/>)
            }
        </div>
    </div>);
}

export default ReportCards;