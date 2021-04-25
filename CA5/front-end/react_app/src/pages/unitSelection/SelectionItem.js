import * as React from "react";
import "../../assets/styles/courses-styles.css";

export default class SelectionItem extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }

     render() {
         return (
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
                            <div className="status-box submitted">
                                <span>
                                    ثبت شده
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-code">
                        <div className="selection-index">
                            <span>
                                ۸۱۰۱۸۶۴-۰۱
                            </span>
                        </div>
                    </div>
                    <div className="col-name">
                        <div className="selection-index">
                            <span>
                                پایگاه داده‌ها
                            </span>
                        </div>
                    </div>
                    <div className="col-instructor">
                        <div className="selection-index">
                            <span>
                                آزاده شاکری
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
         );
     }
}