import React from "react";
import "../../assets/styles/schedule-styles.css";

export default class TableHeader extends React.Component{
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <div class="course-item">
                <div class="row no-gutters">
                    <div class="col-day">
                        <div class="course-index">
                            <span>
                                &nbsp;
                            </span>
                        </div>
                    </div>
                    <div class="col-day">
                        <div class="course-index">
                            <span>
                                شنبه
                            </span>
                        </div>
                    </div>
                    <div class="col-day">
                        <div class="course-index">
                            <span>
                                یک‌شنبه
                            </span>
                        </div>
                    </div>
                    <div class="col-day">
                        <div class="course-index">
                            <span>
                                دوشنبه
                            </span>
                        </div>
                    </div>
                    <div class="col-day">
                        <div class="course-index">
                            <span>
                                سه‌شنبه
                            </span>
                        </div>
                    </div>
                    <div class="col-day">
                        <div class="course-index">
                            <span>
                                چهارشنبه
                            </span>
                        </div>
                    </div>
                    <div class="col-day">
                        <div class="course-index last-index">
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