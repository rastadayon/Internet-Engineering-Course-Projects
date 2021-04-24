import React from "react";
import "../../assets/styles/schedule-styles.css";

export default class TableHeader extends React.Component{
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <div className="course-item">
                <div className="row no-gutters">
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                &nbsp;
                            </span>
                        </div>
                    </div>
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                شنبه
                            </span>
                        </div>
                    </div>
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                یک‌شنبه
                            </span>
                        </div>
                    </div>
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                دوشنبه
                            </span>
                        </div>
                    </div>
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                سه‌شنبه
                            </span>
                        </div>
                    </div>
                    <div className="col-day">
                        <div className="course-index">
                            <span>
                                چهارشنبه
                            </span>
                        </div>
                    </div>
                    <div className="col-day">
                        <div className="course-index last-index">
                            <span>
                                پنجشنبه
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}