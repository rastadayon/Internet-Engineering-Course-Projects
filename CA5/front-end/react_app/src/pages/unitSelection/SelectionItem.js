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
             <div class="selection-item">
                <div class="row no-gutters">
                    <div class="col-trash">
                        <div class="trash clickable">
                            <span>
                                <i class="icon flaticon-trash-bin"></i>
                            </span>
                        </div>
                    </div>
                    <div class="col-status">
                        <div class="selection-index course-status first-index">
                            <div class="status-box submitted">
                                <span>
                                    ثبت شده
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="col-code">
                        <div class="selection-index">
                            <span>
                                ۸۱۰۱۸۶۴-۰۱
                            </span>
                        </div>
                    </div>
                    <div class="col-name">
                        <div class="selection-index">
                            <span>
                                پایگاه داده‌ها
                            </span>
                        </div>
                    </div>
                    <div class="col-instructor">
                        <div class="selection-index">
                            <span>
                                آزاده شاکری
                            </span>
                        </div>
                    </div>
                    <div class="col-1">
                        <div class="selection-units">
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